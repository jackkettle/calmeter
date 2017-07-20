package com.calmeter.core.account.service;

import java.util.Optional;

import com.calmeter.core.account.model.User;

public interface IUserService {
	
	void save(User user);

	Optional<User> findByUsername(String username);

}