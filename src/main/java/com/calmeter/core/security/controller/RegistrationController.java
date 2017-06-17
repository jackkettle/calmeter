package com.calmeter.core.security.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.calmeter.core.account.model.User;
import com.calmeter.core.account.repository.IUserRepository;

@RestController
@RequestMapping(value = "/api/auth")
public class RegistrationController {

	@Autowired
	private IUserRepository userRepository;
	
	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public ResponseEntity<String> registration(User user) {

		logger.info("registration service called");
		logger.info("Registering username: {}", user.getUsername());

		userRepository.save(user);
		return new ResponseEntity<String> (HttpStatus.OK);
	}
	
	public static final Logger logger = LoggerFactory.getLogger (RegistrationController.class);
	
}
