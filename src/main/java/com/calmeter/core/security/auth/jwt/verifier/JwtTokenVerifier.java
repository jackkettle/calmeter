package com.calmeter.core.security.auth.jwt.verifier;

import org.springframework.stereotype.Component;

@Component
public class JwtTokenVerifier implements TokenVerifier {
    @Override
    public boolean verify(String jti) {
        return true;
    }
}
