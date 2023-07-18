package com.gloddy.server.authEmail.exception;

import com.gloddy.server.core.error.handler.errorCode.ErrorCode;
import com.gloddy.server.core.error.handler.exception.UserBusinessException;

public class InvalidVerificationCodeException extends UserBusinessException {

    public InvalidVerificationCodeException() {
        super(ErrorCode.VERIFICATION_CODE_INVALID);
    }

}
