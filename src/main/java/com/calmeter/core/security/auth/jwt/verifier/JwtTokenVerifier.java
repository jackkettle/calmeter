package com.calmeter.core.security.auth.jwt.verifier;

import org.springframework.stereotype.Component;

@Component
public class JwtTokenVerifier implements TokenVerifier {
    @Override
    public boolean verify(String jti) {
        
        // TODO: Implement JWT verify method
        
        // well formed jwt
        
        // check the signature
        
        // validate the standard claims
        
        // check the client permissions
        
        return true;
    }
}
