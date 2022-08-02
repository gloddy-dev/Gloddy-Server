package com.gloddy.server.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenBuilder {

    private final String key;
    private final Long validTime;

    public JwtTokenBuilder(@Value("${jwt.secret}") String key,
                           @Value("${jwt.validTime}") Long validTime) {
        this.key = key;
        this.validTime = validTime;
    }

    public String createToken(String email) {

        long vaildTimeMilli = validTime * 1000L;
        Date now = new Date();

        Claims claims = Jwts.claims().setSubject(email);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime()+ vaildTimeMilli))
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }
}
