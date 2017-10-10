package com.calmeter.core.account.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.calmeter.core.account.model.User;
import com.calmeter.core.account.model.UserRole;
import com.calmeter.core.account.repository.IUserRoleRepository;
import com.calmeter.core.account.repository.IUserRepository;

import java.util.HashSet;
import java.util.Optional;

@Service
public class UserServiceImpl
		implements IUserService {

	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private IUserRoleRepository roleRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public void save (User user) {
		user.setPassword (bCryptPasswordEncoder.encode (user.getPassword ()));
		user.setRoles (new HashSet<UserRole> (roleRepository.findAll ()));
		userRepository.save (user);
	}

	public Optional<User> findByUsername (String username) {
		return userRepository.findByUsername (username);
	}
}
