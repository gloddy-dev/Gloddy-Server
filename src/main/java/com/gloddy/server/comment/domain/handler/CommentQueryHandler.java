package com.gloddy.server.comment.domain.handler;

import com.gloddy.server.comment.domain.Comment;

public interface CommentQueryHandler {
    Comment findById(Long id);
}
