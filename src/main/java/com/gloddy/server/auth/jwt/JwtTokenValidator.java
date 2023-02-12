package com.gloddy.server.auth.jwt;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class JwtTokenValidator {

    public boolean validateToken(String token) {
        return Optional.ofNullable(token).isEmpty() || token.equals("null") || token.equals("undefined");
    }
}
