package com.gloddy.server.comment.infra.repository.custom;

import com.gloddy.server.article.domain.Article;
import com.gloddy.server.comment.domain.Comment;

import java.util.List;

public interface CommentJpaRepositoryCustom {

    List<Comment> findAllByArticleFetchUser(Article article);
}
