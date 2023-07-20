package com.gloddy.server.auth.jwt.payload.factory;

import com.gloddy.server.auth.jwt.payload.AccessPayload;
import com.gloddy.server.auth.jwt.payload.Payload;
import io.jsonwebtoken.Claims;

import static com.gloddy.server.auth.jwt.constant.Constant.*;

public class AccessPayloadFactory extends PayloadFactory {
    @Override
    public Payload createPayload(Claims claims) {
        String sub = claims.get(SUB, String.class);
        return AccessPayload.of(sub);
    }
}
