package com.gloddy.server.comment.infra.repository;

import com.gloddy.server.article.domain.Article;
import com.gloddy.server.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentJpaRepository extends JpaRepository<Comment, Long> {
    int countAllByArticle(Article article);
    List<Comment> findAllByArticle(Article article);
}
