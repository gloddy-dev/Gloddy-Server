package com.gloddy.server.article.infra.repository;

import com.gloddy.server.article.domain.Article;
import com.gloddy.server.article.domain.ArticleImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleImageJpaRepository extends JpaRepository<ArticleImage, Long> {
    List<ArticleImage> findAllByArticle(Article article);
    void deleteAllByArticle(Article article);
}
