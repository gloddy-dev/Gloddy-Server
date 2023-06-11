package com.gloddy.server.group_member.exception;

import com.gloddy.server.core.error.handler.errorCode.ErrorCode;
import com.gloddy.server.core.error.handler.exception.GroupMemberBusinessException;

public class InvalidRequestGroupMemberPraiseException extends GroupMemberBusinessException {
    public InvalidRequestGroupMemberPraiseException() {
        super(ErrorCode.NO_TOTAL_GROUP_MEMBER_PRAISE);
    }
}
