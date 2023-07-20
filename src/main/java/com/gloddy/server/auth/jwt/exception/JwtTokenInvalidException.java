package com.gloddy.server.auth.jwt.exception;

import com.gloddy.server.core.error.handler.errorCode.ErrorCode;
import com.gloddy.server.core.error.handler.exception.UserBusinessException;

public class JwtTokenInvalidException extends UserBusinessException {
    public JwtTokenInvalidException() {
        super(ErrorCode.TOKEN_INVALID);
    }
}
