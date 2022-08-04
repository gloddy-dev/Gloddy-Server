package com.gloddy.server.Exception.auth;

import com.gloddy.server.core.error.handler.errorCode.ErrorCode;
import com.gloddy.server.core.error.handler.exception.UserBusinessException;

public class InvalidEmailException extends UserBusinessException {

    public InvalidEmailException() {
        super(ErrorCode.EMAIL_INVALID);
    }
}
