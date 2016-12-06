package com.calmeter.core.account.service;

import com.calmeter.core.account.model.User;

public interface IUserService {
	
	void save(User user);

	User findByUsername(String username);

}