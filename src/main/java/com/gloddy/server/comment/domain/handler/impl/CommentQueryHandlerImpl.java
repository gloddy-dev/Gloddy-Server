package com.gloddy.server.comment.domain.handler.impl;

import com.gloddy.server.article.domain.Article;
import com.gloddy.server.comment.domain.Comment;
import com.gloddy.server.comment.domain.handler.CommentQueryHandler;
import com.gloddy.server.comment.exception.NotFoundCommentException;
import com.gloddy.server.comment.infra.repository.CommentJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentQueryHandlerImpl implements CommentQueryHandler {

    private final CommentJpaRepository commentJpaRepository;

    @Override
    public Comment findById(Long id) {
        return commentJpaRepository.findById(id)
                .orElseThrow(NotFoundCommentException::new);
    }

    @Override
    public List<Comment> findAllByArticleFetchUser(Article article) {
        return commentJpaRepository.findAllByArticleFetchUser(article);
    }
}
