package com.gloddy.server.comment.handler;

import com.gloddy.server.comment.entity.Comment;

public interface CommendHandler {
    Comment findById(Long id);
}
