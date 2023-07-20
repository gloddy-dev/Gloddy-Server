package com.gloddy.server.auth.exception;

import com.gloddy.server.core.error.handler.errorCode.ErrorCode;
import com.gloddy.server.core.error.handler.exception.UserBusinessException;

public class InValidPhoneNumberException extends UserBusinessException {
    public InValidPhoneNumberException() {
        super(ErrorCode.INVALID_PHONE_NUMBER);
    }
}
