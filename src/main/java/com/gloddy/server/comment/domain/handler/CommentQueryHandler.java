package com.gloddy.server.comment.domain.handler;

import com.gloddy.server.article.domain.Article;
import com.gloddy.server.comment.domain.Comment;

import java.util.List;

public interface CommentQueryHandler {
    Comment findById(Long id);
    List<Comment> findAllByArticleFetchUser(Article article);
}
