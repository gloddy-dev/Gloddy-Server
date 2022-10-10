package com.gloddy.server.comment.handler;

import com.gloddy.server.comment.entity.Comment;
import com.gloddy.server.comment.repository.CommentJpaRepository;
import com.gloddy.server.core.error.handler.errorCode.ErrorCode;
import com.gloddy.server.core.error.handler.exception.UserBusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommentHandlerImpl implements CommendHandler {

    private final CommentJpaRepository commentJpaRepository;

    @Override
    public Comment findById(Long id) {
        return commentJpaRepository.findById(id)
                .orElseThrow(() -> new UserBusinessException(ErrorCode.COMMENT_NOT_FOUND));
    }
}
