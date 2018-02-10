package com.calmeter.core.account.utils;

import java.util.List;
import java.util.Optional;

import com.calmeter.core.Constants;
import com.calmeter.core.account.model.*;
import com.calmeter.core.account.service.IUserTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.calmeter.core.account.service.IUserService;
import com.calmeter.core.security.model.UserContext;

@Component
public class UserHelper {

    private IUserService userService;
    private IUserTaskService userTaskService;

    @Autowired
    public UserHelper(IUserService userService, IUserTaskService userTaskService) {
        this.userService = userService;
        this.userTaskService = userTaskService;
    }

    public Optional<User> getLoggedInUser() {
        UserContext loggedInUserContext = (UserContext) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        if (loggedInUserContext == null) {
            return Optional.empty();
        }

        return userService.findByUsername(loggedInUserContext.getUsername());
    }

    public boolean isAdmin(User user) {
        for (UserRole role : user.getRoles()) {
            if (role.getRole().equals(Role.ADMIN))
                return true;
        }
        return false;
    }

    public Optional<WeightLogEntry> getCurrentWeight(User user) {
        if (!user.getUserProfileSet())
            return Optional.empty();

        List<WeightLogEntry> weightLogEntryList = user.getUserProfile().getWeightLog();

        if (weightLogEntryList == null || weightLogEntryList.size() < 1) {
            return Optional.empty();
        }

        WeightLogEntry weightLogEntryToReturn = weightLogEntryList.get(0);
        for (int i = 1; i < weightLogEntryList.size(); i++) {
            WeightLogEntry weightLogEntry = weightLogEntryList.get(i);
            if (weightLogEntry.getDateTime().isAfter(weightLogEntryToReturn.getDateTime())) {
                weightLogEntryToReturn = weightLogEntry;
            }
        }
        return Optional.of(weightLogEntryToReturn);
    }

    public void createNewUserTasks(User user) {
        if (user.getId() == 0)
            return;

        UserTask userTask1 = new UserTask();
        UserTask userTask2 = new UserTask();

        userTask1.setOwner(user);
        userTask1.setDescription(Constants.USER_TASK_SET_PROFILE_DESCRIPTION);
        userTask1.setLocation(Constants.USER_TASK_SET_PROFILE_LOCATION);
        userTask1.setIcon(Constants.USER_TASK_SET_PROFILE_ICON);
        userTask1.setTaskStatus(TaskStatus.OPEN);
        this.userTaskService.save(userTask1);

        userTask2.setOwner(user);
        userTask2.setDescription(Constants.USER_TASK_SET_GOALS_DESCRIPTION);
        userTask2.setLocation(Constants.USER_TASK_SET_GOALS_LOCATION);
        userTask2.setIcon(Constants.USER_TASK_SET_GOALS_ICON);
        userTask2.setTaskStatus(TaskStatus.OPEN);
        this.userTaskService.save(userTask2);
    }

    public static final Logger logger = LoggerFactory.getLogger(UserHelper.class);

}
