package com.calmeter.core.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.calmeter.core.account.model.User;

public interface IUserRepository
		extends JpaRepository<User, Long> {

	User findById (Long id);

	User findByUsername (String username);
}
