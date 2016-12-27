package com.calmeter.core.account;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.calmeter.core.EventBusFactory;
import com.calmeter.core.account.model.User;
import com.calmeter.core.account.model.UserEvent;
import com.calmeter.core.account.model.UserEventType;
import com.calmeter.core.account.repository.UserRepository;

@Component("authenticationProvider")
public class LoginAuthenticationProvider extends DaoAuthenticationProvider {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	EventBusFactory eventBusFactory;

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	@Qualifier("userDetailsService")
	@Override
	public void setUserDetailsService(UserDetailsService userDetailsService) {
		super.setUserDetailsService(userDetailsService);
		super.setPasswordEncoder(bCryptPasswordEncoder());

	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		try {
			Authentication auth = super.authenticate(authentication);
			logger.info("User logged in: {}", authentication.getName());

			User user = userRepository.findByUsername(authentication.getName());
			UserEvent userEvent = new UserEvent();
			userEvent.setUser(user);
			userEvent.setUserEventType(UserEventType.LOGIN);
			eventBusFactory.getInstance().post(userEvent);

			return auth;

		} catch (BadCredentialsException e) {
			logger.info("Bad user credential: {}", authentication.getName());
			throw e;
		}

	}

	private static Logger logger = LoggerFactory.getLogger(LoginAuthenticationProvider.class);

}
