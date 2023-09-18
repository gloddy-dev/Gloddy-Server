package com.gloddy.server.apply.exception;

import com.gloddy.server.core.error.handler.errorCode.ErrorCode;
import com.gloddy.server.core.error.handler.exception.ApplyBusinessException;

public class CantAcceptMoreGroupMemberException extends ApplyBusinessException {
    public CantAcceptMoreGroupMemberException() {
        super(ErrorCode.CANT_ACCEPT_MORE_GROUP_MEMBER);
    }
}
