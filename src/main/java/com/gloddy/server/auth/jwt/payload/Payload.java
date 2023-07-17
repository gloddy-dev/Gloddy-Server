package com.gloddy.server.auth.jwt.payload;

import com.gloddy.server.auth.jwt.type.TokenType;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

import static com.gloddy.server.auth.jwt.constant.Constant.SUB;
import static com.gloddy.server.auth.jwt.constant.Constant.TYPE;

@Getter
public abstract class Payload {

    protected String sub;
    protected TokenType type;

    protected Payload() {

    }

    protected Payload(String sub, TokenType type) {
        this.sub = sub;
        this.type = type;
    }

    public Map<String, Object> createClaims() {
        HashMap<String, Object> claims = new HashMap<>();

        claims.put(SUB, this.sub);
        claims.put(TYPE, this.type);
        return claims;
    }
}
