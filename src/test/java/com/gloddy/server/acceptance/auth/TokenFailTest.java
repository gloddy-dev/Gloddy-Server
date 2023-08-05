package com.gloddy.server.acceptance.auth;

import com.gloddy.server.auth.jwt.payload.AccessPayload;
import com.gloddy.server.common.auth.AuthApiTest;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Map;

import static com.gloddy.server.core.error.handler.errorCode.ErrorCode.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TokenFailTest extends AuthApiTest {

    @Value("${jwt.secret}")
    private String key;

    private String createExpiredAccessToken() {
        AccessPayload accessPayload = AccessPayload.of(user.getPhone().toString());

        Date now = new Date();

        Map<String, Object> claims = accessPayload.createClaims();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(now)
                .signWith(getSigningKey(key), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSigningKey(String secretKey) {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private ResultActions apiCall(String accessToken) throws Exception {
        return mockMvc.perform(
                get("/me/page")
                .header("X-AUTH-TOKEN", accessToken)
                .contentType(MediaType.APPLICATION_JSON)
        );
    }

    private ResultActions apiCallWithNoToken() throws Exception {
        return mockMvc.perform(
                get("/me/page")
                .contentType(MediaType.APPLICATION_JSON)
        );
    }

    @Test
    @DisplayName("Fail - Token Expire")
    void failByTokenExpire() throws Exception {
        String expiredAccessToken = createExpiredAccessToken();

        ResultActions test = apiCall(expiredAccessToken);

        test.andExpect(status().isForbidden());
        test.andExpect(jsonPath("status").value(TOKEN_EXPIRED.getStatus()));
        test.andExpect(jsonPath("message").value(TOKEN_EXPIRED.name()));
        test.andExpect(jsonPath("reason").value(TOKEN_EXPIRED.getErrorMessage()));
    }

    @Test
    @DisplayName("Fail - Token Blank")
    void failByTokenBlank() throws Exception {
        ResultActions test = apiCallWithNoToken();

        test.andExpect(status().isForbidden());
        test.andExpect(jsonPath("status").value(TOKEN_BLANK.getStatus()));
        test.andExpect(jsonPath("message").value(TOKEN_BLANK.name()));
        test.andExpect(jsonPath("reason").value(TOKEN_BLANK.getErrorMessage()));
    }

    @Test
    @DisplayName("FAIL - Token Invalid")
    void failByTokenInvalid() throws Exception {
        String invalidToken = "token";

        ResultActions test = apiCall(invalidToken);

        test.andExpect(status().isForbidden());
        test.andExpect(jsonPath("status").value(TOKEN_INVALID.getStatus()));
        test.andExpect(jsonPath("message").value(TOKEN_INVALID.name()));
        test.andExpect(jsonPath("reason").value(TOKEN_INVALID.getErrorMessage()));
    }
}
