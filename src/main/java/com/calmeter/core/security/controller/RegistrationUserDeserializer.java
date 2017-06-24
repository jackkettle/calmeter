package com.calmeter.core.security.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.calmeter.core.account.model.Role;
import com.calmeter.core.account.model.User;
import com.calmeter.core.account.model.UserRole;
import com.calmeter.core.account.repository.IUserRoleRepository;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class RegistrationUserDeserializer
		extends JsonDeserializer<User> {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private IUserRoleRepository roleRepository;

	@Override
	public User deserialize (JsonParser jsonParser, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {

		JsonNode rootNode = jsonParser.getCodec ().readTree (jsonParser);
		User user = new User ();
		
		user.setUsername (rootNode.get ("username").asText ());
		user.setFirstName (rootNode.get ("firstName").asText ());
		user.setLastName (rootNode.get ("lastName").asText ());
		user.setEmail (rootNode.get ("email").asText ());
		
		String rawPassword = rootNode.get ("passwords").get ("password").asText ();
		user.setPassword (passwordEncoder.encode (rawPassword));
		
		UserRole memberRole = roleRepository.findByRole (Role.MEMBER).get ();
		user.getRoles ().add (memberRole);
		
		user.setEnabled (true);

		return user;
	}

}
