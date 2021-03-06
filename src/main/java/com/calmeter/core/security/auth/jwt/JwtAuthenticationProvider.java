package com.calmeter.core.security.auth.jwt;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.calmeter.core.security.auth.JwtAuthenticationToken;
import com.calmeter.core.security.config.JwtSettings;
import com.calmeter.core.security.model.UserContext;
import com.calmeter.core.security.model.token.RawAccessJwtToken;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

@Component
@SuppressWarnings("unchecked")
public class JwtAuthenticationProvider
		implements AuthenticationProvider {

	private final JwtSettings jwtSettings;

	@Autowired
	public JwtAuthenticationProvider (JwtSettings jwtSettings) {
		this.jwtSettings = jwtSettings;
	}

	@Override
	public Authentication authenticate (Authentication authentication)
			throws AuthenticationException {
						
		RawAccessJwtToken rawAccessToken = (RawAccessJwtToken)authentication.getCredentials ();

		Jws<Claims> jwsClaims = rawAccessToken.parseClaims (jwtSettings.getTokenSigningKey ());
		String subject = jwsClaims.getBody ().getSubject ();
		List<String> scopes = jwsClaims.getBody ().get ("scopes", List.class);
		List<GrantedAuthority> authorities =
				scopes.stream ().map (authority -> new SimpleGrantedAuthority (authority)).collect (Collectors.toList ());

		UserContext context = UserContext.create (subject, authorities);

		return new JwtAuthenticationToken (context, context.getAuthorities ());
	}

	@Override
	public boolean supports (Class<?> authentication) {
		return (JwtAuthenticationToken.class.isAssignableFrom (authentication));
	}

	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(JwtAuthenticationProvider.class);
}
