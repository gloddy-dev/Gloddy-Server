package com.gloddy.server.auth.jwt.payload.factory;

import com.gloddy.server.auth.jwt.constant.Constant;
import com.gloddy.server.auth.jwt.payload.Payload;
import com.gloddy.server.auth.jwt.payload.RefreshPayload;
import io.jsonwebtoken.Claims;

import static com.gloddy.server.auth.jwt.constant.Constant.*;

public class RefreshPayloadFactory extends PayloadFactory {
    @Override
    public Payload createPayload(Claims claims) {
        String sub = claims.get(SUB, String.class);
        String accessToken = claims.get(ACCESS_TOKEN, String.class);
        return RefreshPayload.of(sub, accessToken);
    }
}
