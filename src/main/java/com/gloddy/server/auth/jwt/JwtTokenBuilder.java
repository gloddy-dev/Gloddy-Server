package com.gloddy.server.auth.jwt;

import com.gloddy.server.auth.jwt.payload.Payload;
import com.gloddy.server.auth.jwt.type.TokenType;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Component
public class JwtTokenBuilder extends JwtTokenKeyUsable {

    private final String key;

    public JwtTokenBuilder(@Value("${jwt.secret}") String key) {
        this.key = key;
    }

    public String createToken(Payload payload) {
        Date expiration = getExpiration(payload.getType());

        Map<String, Object> claims = payload.createClaims();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(expiration)
                .signWith(getSigningKey(key), SignatureAlgorithm.HS256)
                .compact();
    }

    private Date getExpiration(TokenType type) {
        Date now = new Date();
        long vaildTimeMilli = type.getValidTimeSeconds() * 1000L;
        return new Date(now.getTime() + vaildTimeMilli);
    }
}
