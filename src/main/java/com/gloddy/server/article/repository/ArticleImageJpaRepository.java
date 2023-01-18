package com.gloddy.server.article.repository;

import com.gloddy.server.article.entity.Article;
import com.gloddy.server.article.entity.ArticleImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleImageJpaRepository extends JpaRepository<ArticleImage, Long> {
    List<ArticleImage> findAllByArticle(Article article);
    void deleteAllByArticle(Article article);
}
