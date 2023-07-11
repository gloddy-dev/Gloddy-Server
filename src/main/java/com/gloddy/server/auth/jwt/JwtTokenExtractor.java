package com.gloddy.server.auth.jwt;

import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;


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
