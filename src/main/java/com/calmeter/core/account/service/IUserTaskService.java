package com.calmeter.core.account.service;

import com.calmeter.core.account.model.TaskStatus;
import com.calmeter.core.account.model.User;
import com.calmeter.core.account.model.UserTask;

import java.util.List;
import java.util.Optional;

public interface IUserTaskService {

    UserTask save(UserTask userTask);

    List<UserTask> getAllByUser(User user);

    List<UserTask> getAllByUserAndTaskStatus(User user, TaskStatus taskStatus);

    Optional<UserTask> getAllByUserAndTaskStatusAndLocation(User user, TaskStatus taskStatus, String location);

}
