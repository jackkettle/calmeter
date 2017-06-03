package com.calmeter.core.account.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.calmeter.core.account.model.User;
import com.calmeter.core.account.model.UserRole;
import com.calmeter.core.account.repository.IUserRoleRepository;
import com.calmeter.core.account.repository.IUserRepository;
import com.google.common.base.Strings;

import java.util.HashSet;

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

	public User findByUsername (String username) {

		if (Strings.isNullOrEmpty (username))
			return null;

		return userRepository.findByUsername (username).get ();
	}
}
