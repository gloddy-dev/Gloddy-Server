package com.gloddy.server.auth.exception;

import com.gloddy.server.core.error.handler.errorCode.ErrorCode;
import com.gloddy.server.core.error.handler.exception.UserBusinessException;

public class AlreadyUserSignUpException extends UserBusinessException {
    public AlreadyUserSignUpException() {
        super(ErrorCode.ALREADY_USER_SIGN_UP);
    }
}
