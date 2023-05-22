package com.gloddy.server.comment.exception;

import com.gloddy.server.core.error.handler.errorCode.ErrorCode;
import com.gloddy.server.core.error.handler.exception.CommentBusinessException;

public class NotFoundCommentException extends CommentBusinessException {
    public NotFoundCommentException() {
        super(ErrorCode.COMMENT_NOT_FOUND);
    }
}
