package com.calmeter.core.account.repository;

import com.calmeter.core.account.model.TaskStatus;
import com.calmeter.core.account.model.User;
import com.calmeter.core.account.model.UserTask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IUserTaskRepository extends JpaRepository<UserTask, Long> {

    List<UserTask> getAllByOwner(User user);

    List<UserTask> getAllByOwnerAndTaskStatus(User user, TaskStatus taskStatus);

    Optional<UserTask> getByOwnerAndTaskStatusAndLocation(User user, TaskStatus taskStatus, String location);

}
