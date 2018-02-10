package com.calmeter.core.account.controller;

import java.util.List;
import java.util.Optional;

import com.calmeter.core.Constants;
import com.calmeter.core.account.model.TaskStatus;
import com.calmeter.core.account.model.UserTask;
import com.calmeter.core.account.service.IUserService;
import com.calmeter.core.account.service.IUserTaskService;
import com.calmeter.core.account.utils.UserHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.calmeter.core.account.model.User;

@RestController
@RequestMapping("/api/user")
public class UserRestController {

    private UserHelper userHelper;
    private IUserService userService;
    private IUserTaskService userTaskService;

    @Autowired
    public UserRestController(IUserService userService, UserHelper userHelper, IUserTaskService userTaskService) {
        this.userService = userService;
        this.userTaskService = userTaskService;
        this.userHelper = userHelper;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    ResponseEntity<User> getThisUser() {

        Optional<User> userOptional = this.userHelper.getLoggedInUser();
        return userOptional
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.FORBIDDEN));

    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    ResponseEntity<String> update(@RequestBody User user) {

        this.getThisUser();

        Optional<User> userWrapper = userHelper.getLoggedInUser();
        if (!userWrapper.isPresent()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        User loggedInUser = userWrapper.get();
        loggedInUser.setEmail(user.getEmail());
        loggedInUser.setFirstname(user.getFirstname());
        loggedInUser.setLastname(user.getLastname());

        if (loggedInUser.getUserProfileSet()) {
            user.getUserProfile().setId(loggedInUser.getUserProfile().getId());
            user.getUserProfile().getWeightLog().addAll(loggedInUser.getUserProfile().getWeightLog());
        }

        loggedInUser.setUserProfileSet(true);
        loggedInUser.setUserProfile(user.getUserProfile());
        User updatedUser = userService.save(loggedInUser);
        logger.info("Updated user: {}", updatedUser.getId());

        Optional<UserTask> goalTaskWrapper = userTaskService.getAllByUserAndTaskStatusAndLocation(userWrapper.get(),
                TaskStatus.OPEN, Constants.USER_TASK_SET_PROFILE_LOCATION);

        goalTaskWrapper.ifPresent(goalTask -> {
            goalTask.setTaskStatus(TaskStatus.COMPLETE);
            userTaskService.save(goalTask);
        });

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/taskList", method = RequestMethod.GET)
    ResponseEntity<List<UserTask>> getTaskList() {

        Optional<User> userWrapper = userHelper.getLoggedInUser();
        return userWrapper
                .map(user -> new ResponseEntity<>(this.userTaskService.getAllByUser(user), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.FORBIDDEN));

    }


    public static final Logger logger = LoggerFactory.getLogger(UserRestController.class);

}
