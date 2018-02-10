package com.calmeter.core.account.service;

import com.calmeter.core.account.model.TaskStatus;
import com.calmeter.core.account.model.User;
import com.calmeter.core.account.model.UserTask;
import com.calmeter.core.account.repository.IUserRepository;
import com.calmeter.core.account.repository.IUserTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Service("userTaskService")
public class UserTaskServiceImpl implements IUserTaskService {

    @Autowired
    private IUserTaskRepository userTaskRepository;

    @Override
    public UserTask save(UserTask userTask) {
        return userTaskRepository.save(userTask);
    }

    @Override
    public List<UserTask> getAllByUser(User user) {
        return userTaskRepository.getAllByOwner(user);
    }

    @Override
    public List<UserTask> getAllByUserAndTaskStatus(User user, TaskStatus taskStatus) {
        return userTaskRepository.getAllByOwnerAndTaskStatus(user, taskStatus);
    }

    @Override
    public Optional<UserTask> getAllByUserAndTaskStatusAndLocation(User user, TaskStatus taskStatus, String location) {
        return userTaskRepository.getByOwnerAndTaskStatusAndLocation(user, taskStatus, location);
    }
}
