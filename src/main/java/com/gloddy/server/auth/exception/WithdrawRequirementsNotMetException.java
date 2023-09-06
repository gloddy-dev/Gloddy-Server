package com.gloddy.server.auth.exception;

import com.gloddy.server.core.error.handler.errorCode.ErrorCode;
import com.gloddy.server.core.error.handler.exception.BaseBusinessException;

public class WithdrawRequirementsNotMetException extends BaseBusinessException {
    public WithdrawRequirementsNotMetException(ErrorCode errorCode) {
        super(errorCode);
    }
}
