package com.gloddy.server.Exception.auth;

import com.gloddy.server.core.error.handler.errorCode.ErrorCode;
import com.gloddy.server.core.error.handler.exception.UserBusinessException;

public class InvalidVerificationCodeException extends UserBusinessException {

    public InvalidVerificationCodeException() {
        super(ErrorCode.CODE_INVALID);
    }

}
