package com.gloddy.server.group_member.exception;

import com.gloddy.server.core.error.handler.errorCode.ErrorCode;
import com.gloddy.server.core.error.handler.exception.GroupMemberBusinessException;

public class AlreadyExistGroupMemberException extends GroupMemberBusinessException {

    public AlreadyExistGroupMemberException() {
        super(ErrorCode.ALREADY_EXIST_GROUP_MEMBER);
    }
}
