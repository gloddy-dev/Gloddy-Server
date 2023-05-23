package com.gloddy.server.comment.exception;

import com.gloddy.server.core.error.handler.errorCode.ErrorCode;
import com.gloddy.server.core.error.handler.exception.CommentBusinessException;

public class NoAuthorizedCommentDeleteException extends CommentBusinessException {
    public NoAuthorizedCommentDeleteException() {
        super(ErrorCode.NO_COMMENT_DELETE_PERMISSION);
    }
}
