package com.gloddy.server.auth.jwt;

import com.gloddy.server.auth.jwt.payload.Payload;
import com.gloddy.server.auth.jwt.payload.factory.AccessPayloadFactory;
import com.gloddy.server.auth.jwt.payload.factory.PayloadFactory;
import com.gloddy.server.auth.jwt.payload.factory.RefreshPayloadFactory;
import com.gloddy.server.auth.jwt.type.TokenType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;


@Component
public class JwtTokenExtractor extends JwtTokenKeyUsable {

    private static final String TYPE = "type";

    public String extractToken(HttpServletRequest request, String headerKey) {
        return request.getHeader(headerKey);
    }

    public String extractSubjectFromToken(String token, String key) {
        Claims claims = getClaims(token, key);
        return claims.getSubject();
    }

    public Payload extractPayload(String token, String key) {
        Claims claims = getClaims(token, key);

        TokenType type = TokenType.valueOf(claims.get(TYPE, String.class));
        PayloadFactory payloadFactory = getPayloadFactory(type);
        return payloadFactory.createPayload(claims);
    }

    private Claims getClaims(String token, String key) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey(key))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private PayloadFactory getPayloadFactory(TokenType type) {
        if (type.isAccess()) {
            return new AccessPayloadFactory();
        } else if (type.isRefresh()) {
            return new RefreshPayloadFactory();
        } else {
            throw new RuntimeException("TOKEN TYPE INVALID");
        }
    }
}
