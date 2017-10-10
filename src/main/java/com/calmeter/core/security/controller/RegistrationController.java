package com.calmeter.core.security.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.calmeter.core.account.model.User;
import com.calmeter.core.account.repository.IUserRepository;

@RestController
@RequestMapping(value = "/api/auth/registration")
public class RegistrationController {

	public static final String USERNAME_EXIST_MESSAGE = "A user with that username already exists";
	public static final String EMAIL_EXIST_MESSAGE = "A user with that email already exists";

	@Autowired
	private IUserRepository userRepository;

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<String> registration (@RequestBody User user) {

		logger.info ("registration service called");
		logger.info ("Registering username: {}", user.getUsername ());

		Optional<User> userWrapper = userRepository.findByUsername (user.getUsername ());
		if (userWrapper.isPresent ()) {
			logger.error ("{}: {}", EMAIL_EXIST_MESSAGE, user.getEmail ());
			return new ResponseEntity<String> (EMAIL_EXIST_MESSAGE, HttpStatus.CONFLICT);
		}

		userWrapper = userRepository.findByEmail (user.getEmail ());
		if (userWrapper.isPresent ()) {
			logger.error ("{}: {}", USERNAME_EXIST_MESSAGE, user.getEmail ());
			return new ResponseEntity<String> (USERNAME_EXIST_MESSAGE, HttpStatus.CONFLICT);
		}

		userRepository.save (user);
		return new ResponseEntity<String> (HttpStatus.CREATED);
	}

	public static final Logger logger = LoggerFactory.getLogger (RegistrationController.class);

}
