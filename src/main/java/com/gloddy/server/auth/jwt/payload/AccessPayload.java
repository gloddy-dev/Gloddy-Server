package com.gloddy.server.auth.jwt.payload;

import com.gloddy.server.auth.jwt.type.TokenType;
import lombok.Getter;

import java.util.Map;

@Getter
public class AccessPayload extends Payload {

    protected AccessPayload(String sub) {
        super(sub, TokenType.ACCESS);
    }

    public static AccessPayload of(String sub) {
        return new AccessPayload(sub);
    }

    @Override
    public Map<String, Object> createClaims() {
        return super.createClaims();
    }
}
