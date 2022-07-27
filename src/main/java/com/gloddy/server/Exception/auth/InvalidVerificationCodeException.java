package com.gloddy.server.Exception.auth;

import com.gloddy.server.Exception.BusinessException;
import com.gloddy.server.Exception.ExceptionCode;

public class InvalidVerificationCodeException extends BusinessException {

    public InvalidVerificationCodeException() {
        super(ExceptionCode.FAIL_AUTHENTICATION);
    }

}
