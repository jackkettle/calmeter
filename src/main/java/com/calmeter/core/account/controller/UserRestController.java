package com.calmeter.core.account.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	private IUserRepository userRepository;

	@RequestMapping(value = "/getThisUser", method = RequestMethod.GET)
	ResponseEntity<User> getThisUser() {

		UserContext loggedInUserContext = (UserContext) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		if (loggedInUserContext == null) {
			return new ResponseEntity<User>(HttpStatus.FORBIDDEN);
		}

		Optional<User> userWrapper = userRepository.findByUsername(loggedInUserContext.getUsername());
		if (!userWrapper.isPresent()) {
			return new ResponseEntity<User>(HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<User>(userWrapper.get(), HttpStatus.OK);
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	ResponseEntity<String> update(@RequestBody User user) {

		UserContext loggedInUserContext = (UserContext) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		if (loggedInUserContext == null) {
			return new ResponseEntity<String>(HttpStatus.FORBIDDEN);
		}

		Optional<User> userWrapper = userRepository.findByUsername(loggedInUserContext.getUsername());
		if (!userWrapper.isPresent()) {
			return new ResponseEntity<String>(HttpStatus.FORBIDDEN);
		}

		User loggedInUser = userWrapper.get();

		user.setUsername(loggedInUser.getUsername());
		user.setId(loggedInUser.getId());
		user.setRoles(loggedInUser.getRoles());
		user.setIsUserProfileSet(true);

		if (Strings.isNullOrEmpty(user.getPassword())) {
			user.setPassword(loggedInUser.getPassword());
		}

		logger.info("user profile: {}", user.getUserProfile().toString());

		User updatedUser = userRepository.save(user);
		logger.info("Updated user: {}", updatedUser.getId());
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	public static final Logger logger = LoggerFactory.getLogger(UserRestController.class);

}
