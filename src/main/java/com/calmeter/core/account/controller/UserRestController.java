package com.calmeter.core.account.controller;

import java.util.List;
import java.util.Optional;

import com.calmeter.core.account.model.WeightLogEntry;
import com.calmeter.core.account.service.IUserService;
import com.calmeter.core.account.service.IWeightLogEntryService;
import com.calmeter.core.account.utils.UserHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.context.SecurityContextHolder;

import com.calmeter.core.account.model.User;
import com.calmeter.core.account.repository.IUserRepository;
import com.calmeter.core.security.model.UserContext;
import com.google.common.base.Strings;

@RestController
@RequestMapping("/api/user")
public class UserRestController {

    @Autowired
    private UserHelper userHelper;
    private IUserService userService;
    private BCryptPasswordEncoder encoder;

    @Autowired
    public UserRestController(IUserService userService, UserHelper userHelper, BCryptPasswordEncoder encoder) {
        this.userService = userService;
        this.userHelper = userHelper;
        this.encoder = encoder;
    }

    @RequestMapping(value = "/getThisUser", method = RequestMethod.GET)
    ResponseEntity<User> getThisUser() {

        UserContext loggedInUserContext = (UserContext) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        if (loggedInUserContext == null) {
            return new ResponseEntity<User>(HttpStatus.FORBIDDEN);
        }

        Optional<User> userWrapper = userService.findByUsername(loggedInUserContext.getUsername());
        if (!userWrapper.isPresent()) {
            return new ResponseEntity<User>(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<User>(userWrapper.get(), HttpStatus.OK);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    ResponseEntity<String> update(@RequestBody User user) {

        Optional<User> userWrapper = userHelper.getLoggedInUser();
        if (!userWrapper.isPresent()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        User loggedInUser = userWrapper.get();
        loggedInUser.setEmail(user.getEmail());
        loggedInUser.setFirstname(user.getFirstname());
        loggedInUser.setLastname(user.getLastname());

        if (loggedInUser.getIsUserProfileSet()) {
            user.getUserProfile().setId(loggedInUser.getUserProfile().getId());
            user.getUserProfile().getWeightLog().addAll(loggedInUser.getUserProfile().getWeightLog());
        }

        loggedInUser.setIsUserProfileSet(true);
        loggedInUser.setUserProfile(user.getUserProfile());
        User updatedUser = userService.save(loggedInUser);
        logger.info("Updated user: {}", updatedUser.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public static final Logger logger = LoggerFactory.getLogger(UserRestController.class);

}
