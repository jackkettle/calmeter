package com.calmeter.core.account.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.calmeter.core.account.model.User;
import com.calmeter.core.account.repository.IUserRepository;

@RestController
@RequestMapping("/api/food-item")
public class UserRestController {

	@Autowired
	private IUserRepository userRepository;

	ResponseEntity<User> getUser (Long id) {
		User user = userRepository.findById (id);
		if (user == null) {
			return new ResponseEntity<User> (HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<User> (user, HttpStatus.OK);
	}

}
