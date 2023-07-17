package com.gloddy.server.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenBuilder {

    private final String key;
    private final Long accessTokenValidTime;
    private final Long refreshTokenValidTime;

    public JwtTokenBuilder(@Value("${jwt.secret}") String key,
                           @Value("${jwt.access-token-validTime}") Long accessTokenValidTime,
                           @Value("${jwt.access-token-validTime}") Long refreshTokenValidTime) {
        this.key = key;
        this.accessTokenValidTime = accessTokenValidTime;
        this.refreshTokenValidTime = refreshTokenValidTime;
    }

    public String createAccessToken(String phoneNumber) {

        long vaildTimeMilli = accessTokenValidTime * 1000L;
        Date now = new Date();

        Claims claims = Jwts.claims().setSubject(phoneNumber);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime()+ vaildTimeMilli))
                .signWith(getSigningKey(key), SignatureAlgorithm.HS256)
                .compact();
    }

    public String createRefreshToken(String phoneNumber) {
        long vaildTimeMilli = refreshTokenValidTime * 1000L;
        Date now = new Date();

        Claims claims = Jwts.claims().setSubject(phoneNumber);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime()+ vaildTimeMilli))
                .signWith(getSigningKey(key), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSigningKey(String secretKey) {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
