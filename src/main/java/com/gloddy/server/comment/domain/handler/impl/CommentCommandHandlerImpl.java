package com.gloddy.server.comment.domain.handler.impl;

import com.gloddy.server.comment.domain.Comment;
import com.gloddy.server.comment.domain.handler.CommentCommandHandler;
import com.gloddy.server.comment.infra.repository.CommentJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommentCommandHandlerImpl implements CommentCommandHandler {
    private final CommentJpaRepository commentJpaRepository;

    @Override
    public Comment save(Comment comment) {
        return commentJpaRepository.save(comment);
    }
}
