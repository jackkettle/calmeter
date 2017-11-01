package com.calmeter.core.security.auth.jwt.verifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.calmeter.core.security.config.JwtSettings;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;

@Component
public class JwtTokenVerifier implements TokenVerifier {

	@Autowired
	JwtSettings jwtSettings;

	@Override
	public boolean verify(String jti) {

		// TODO: Implement JWT verify method
		logger.info("Verifying JWT");

		// well formed jwt
		try {
			
			Jws<Claims> claims = Jwts.parser().setSigningKey(jwtSettings.getTokenSigningKey()).parseClaimsJws(jti);

			logger.info("Token: {}", claims.getHeader());
			logger.info("Token: {}", claims.getBody());
			logger.info("Token: {}", claims.getSignature());


		} catch (SignatureException e) {

			logger.error("Invalid jwt. Reason: {}", e.getMessage());
			return false;

		}

		// check the signature

		// validate the standard claims

		// check the client permissions

		return true;
	}

	private static Logger logger = LoggerFactory.getLogger(JwtTokenVerifier.class);

}
