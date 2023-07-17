package com.gloddy.server.auth.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JwtToken {
    private String accessToken;
    private String refreshToken;

    public static JwtToken of(String accessToken, String refreshToken) {
        return new JwtToken(accessToken, refreshToken);
    }
}
