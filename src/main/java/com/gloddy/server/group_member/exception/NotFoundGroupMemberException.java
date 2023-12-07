package com.gloddy.server.group_member.exception;

import com.gloddy.server.core.error.handler.errorCode.ErrorCode;
import com.gloddy.server.core.error.handler.exception.BaseBusinessException;

public class NotFoundGroupMemberException extends BaseBusinessException {
    public NotFoundGroupMemberException() {
        super(ErrorCode.NOT_FOUND_GROUP_MEMBER);
    }
}
