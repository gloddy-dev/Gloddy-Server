package com.gloddy.server.comment.repository;

import com.gloddy.server.article.entity.Article;
import com.gloddy.server.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentJpaRepository extends JpaRepository<Comment, Long> {
    int countAllByArticle(Article article);
    List<Comment> findAllByArticle(Article article);
}
