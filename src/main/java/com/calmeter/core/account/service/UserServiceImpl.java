package com.calmeter.core.account.service;

import com.calmeter.core.account.utils.UserHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.calmeter.core.account.model.User;
import com.calmeter.core.account.model.UserRole;
import com.calmeter.core.account.repository.IUserRoleRepository;
import com.calmeter.core.account.repository.IUserRepository;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;

@Service
public class UserServiceImpl
        implements IUserService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private UserHelper userHelper;

    public User save(User user) {

        boolean isNewUser = false;
        if (this.findByUsername(user.getUsername()).isPresent()) {
            user.setModificationDateTime(LocalDateTime.now());
        } else {
            isNewUser = true;
            user.setCreationDateTime(LocalDateTime.now());
        }
        User createdUser = userRepository.save(user);

        if (isNewUser)
            this.userHelper.createNewUserTasks(createdUser);

        return createdUser;
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
