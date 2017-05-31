package com.calmeter.core.account.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.calmeter.core.account.model.User;

public interface IUserRepository
		extends JpaRepository<User, Long> {

	Optional<User> findById (Long id);

	Optional<User> findByUsername (String username);
}
