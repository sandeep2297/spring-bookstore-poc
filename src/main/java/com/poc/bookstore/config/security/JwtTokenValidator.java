package com.poc.bookstore.config.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.poc.bookstore.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import static com.poc.bookstore.util.JwtUtil.decodeToken;
import static com.poc.bookstore.util.JwtUtil.verifyPayload;
import static com.poc.bookstore.util.JwtUtil.verifySignature;

@RequiredArgsConstructor
public class JwtTokenValidator {

    private final String signingKey;

    private final UserRepository userRepository;

    public AccessToken validateAuthorizationHeader(String authorizationHeader) {
        validateToken(authorizationHeader);
        return new AccessToken(authorizationHeader, userRepository);
    }

    private void validateToken(String value) {
        DecodedJWT decodedJWT = decodeToken(value);
        verifyPayload(decodedJWT);
        verifySignature(value, signingKey);
    }

}
