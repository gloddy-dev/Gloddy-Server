package com.gloddy.server.auth.jwt;

import com.gloddy.server.auth.jwt.exception.JwtTokenInvalidException;
import com.gloddy.server.auth.jwt.payload.AccessPayload;
import com.gloddy.server.auth.jwt.payload.Payload;
import com.gloddy.server.auth.jwt.payload.RefreshPayload;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtTokenIssuer {

    @Value("${jwt.secret}")
    private String key;
    private final JwtTokenBuilder jwtTokenBuilder;
    private final JwtTokenExtractor jwtTokenExtractor;

    public JwtToken issueToken(String sub) {

        AccessPayload accessPayload = AccessPayload.of(sub);
        String accessToken = jwtTokenBuilder.createToken(accessPayload);

        RefreshPayload refreshPayload = RefreshPayload.of(sub, accessToken);
        String refreshToken = jwtTokenBuilder.createToken(refreshPayload);

        return JwtToken.of(accessToken, refreshToken);
    }

    public JwtToken reIssueToken(String accessToken, String refreshToken) {
        try {

            Payload payload = jwtTokenExtractor.extractPayload(refreshToken, key);
            RefreshPayload refreshPayload = (RefreshPayload) payload;

            refreshPayload.validate(accessToken);
            return issueToken(refreshPayload.getSub());

        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException e) {
            log.error(e.getMessage());
            throw new JwtTokenInvalidException();
        }
    }
}
