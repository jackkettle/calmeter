package com.calmeter.core.security.auth.jwt.verifier;

public interface TokenVerifier {
    public boolean verify(String jti);
}
