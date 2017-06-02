package com.calmeter.core.account.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.calmeter.core.account.model.User;
import com.calmeter.core.account.repository.IUserRepository;

@RestController
@RequestMapping("/api/user")
public class UserRestController {

	@Autowired
	private IUserRepository userRepository;
	
	ResponseEntity<String> update (User user) {
		User updatedUser = userRepository.save (user);
		logger.info ("Updated user: {}", updatedUser.getId ());
		return new ResponseEntity<String> (HttpStatus.OK);
	}

	public static final Logger logger = LoggerFactory.getLogger (UserRestController.class);

}
