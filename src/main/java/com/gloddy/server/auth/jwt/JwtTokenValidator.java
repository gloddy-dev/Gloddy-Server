package com.gloddy.server.auth.jwt;

import org.springframework.stereotype.Component;

@Component
public class JwtTokenValidator {

    public boolean validateToken(String token) {
        return token.equals("null") || token.equals("undefined") ? true : false;
    }
}
