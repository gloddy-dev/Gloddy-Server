package com.gloddy.server.auth.jwt.payload.factory;

import com.gloddy.server.auth.jwt.payload.Payload;
import io.jsonwebtoken.Claims;

public abstract class PayloadFactory {

    abstract public Payload createPayload(Claims claims);
}
