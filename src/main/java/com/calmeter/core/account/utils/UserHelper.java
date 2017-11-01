package com.calmeter.core.account.utils;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.calmeter.core.account.model.Role;
import com.calmeter.core.account.model.User;
import com.calmeter.core.account.model.UserRole;
import com.calmeter.core.account.service.IUserService;
import com.calmeter.core.security.model.UserContext;

@Component
public class UserHelper {

	@Autowired
	IUserService userService;

	public Optional<User> getLoggedInUser() {
		UserContext loggedInUserContext = (UserContext) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		if (loggedInUserContext == null) {
			return Optional.empty();
		}

		Optional<User> userWrapper = userService.findByUsername(loggedInUserContext.getUsername());
		if (!userWrapper.isPresent())
			return Optional.empty();

		return Optional.of(userWrapper.get());
	}

	public boolean isAdmin(User user) {
		for (UserRole role : user.getRoles()) {
			if (role.getRole().equals(Role.ADMIN))
				return true;
		}
		return false;
	}

	public static final Logger logger = LoggerFactory.getLogger(UserHelper.class);

}
