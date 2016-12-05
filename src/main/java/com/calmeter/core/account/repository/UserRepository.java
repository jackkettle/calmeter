package com.calmeter.core.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.calmeter.core.account.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
