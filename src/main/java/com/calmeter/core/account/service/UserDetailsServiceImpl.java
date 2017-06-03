package com.calmeter.core.account.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.calmeter.core.account.model.User;
import com.calmeter.core.account.model.UserRole;
import com.calmeter.core.account.repository.IUserRepository;
import com.google.common.base.Strings;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service("userDetailsService")
public class UserDetailsServiceImpl
		implements UserDetailsService {

	@Autowired
	private IUserRepository userRepository;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername (String username)
			throws UsernameNotFoundException {
		if (Strings.isNullOrEmpty (username))
			return null;

		Optional<User> userWrapper = userRepository.findByUsername (username);

		if (!userWrapper.isPresent ())
			throw new UsernameNotFoundException (username);

		User user = userWrapper.get ();

		Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority> ();
		for (UserRole role : user.getRoles ()) {
			grantedAuthorities.add (new SimpleGrantedAuthority (role.getRole ().authority ()));
		}

		return new org.springframework.security.core.userdetails.User (user.getUsername (), user.getPassword (), grantedAuthorities);
	}
}
