package com.calmeter.core.custom;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.calmeter.core.account.model.Role;
import com.calmeter.core.account.model.User;
import com.calmeter.core.account.model.UserRole;
import com.calmeter.core.account.repository.IUserRoleRepository;
import com.calmeter.core.account.repository.IUserRepository;

@Component
public class InitialDataLoader implements ApplicationListener<ContextRefreshedEvent> {

	boolean alreadySetup = false;

	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private IUserRoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	@Transactional
	public void onApplicationEvent(ContextRefreshedEvent event) {

		if (alreadySetup)
			return;

		createRoleIfNotFound(Role.ADMIN);
		createRoleIfNotFound(Role.MEMBER);
		createRoleIfNotFound(Role.PREMIUM_MEMBER);

		UserRole adminRole = roleRepository.findByRole(Role.ADMIN).get();
		UserRole memberRole = roleRepository.findByRole(Role.MEMBER).get();
		UserRole premiumMemberRole = roleRepository.findByRole(Role.PREMIUM_MEMBER).get();

		User user = new User();
		user.setUsername("john.doe");
		user.setEmail("john.doe@example.com");
		user.setPassword(passwordEncoder.encode("password"));
		user.getRoles().add(adminRole);
		user.getRoles().add(memberRole);
		user.getRoles().add(premiumMemberRole);
		user.setEnabled(true);
		userRepository.save(user);

		alreadySetup = true;
	}

	@Transactional
	private UserRole createRoleIfNotFound(Role role) {

		Optional<UserRole> userRoleWrapper = roleRepository.findByRole(role);
		UserRole userRole = null;
		if (!userRoleWrapper.isPresent()) {
			userRole = new UserRole(role);
			roleRepository.save(userRole);
		} else {
			userRole = userRoleWrapper.get();
		}
		return userRole;
	}
}