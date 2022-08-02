package com.gloddy.server.auth.jwt;

import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class JwtTokenExtractor {

    public String extractToken(HttpServletRequest request, String headerKey) {
        return request.getHeader(headerKey);
    }

    public String extractEmailFromToken(String token, String key) {
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
