package com.calmeter.core.security.exceptions;

import org.springframework.security.authentication.AuthenticationServiceException;

public class AuthMethodNotSupportedException extends AuthenticationServiceException {

	private static final long serialVersionUID = -3523327988019338164L;

	public AuthMethodNotSupportedException(String msg) {
        super(msg);
    }
}
