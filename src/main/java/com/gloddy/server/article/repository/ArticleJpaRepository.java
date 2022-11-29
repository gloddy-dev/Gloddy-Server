package com.gloddy.server.article.repository;

import com.gloddy.server.article.entity.Article;
import com.gloddy.server.group.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleJpaRepository extends JpaRepository<Article, Long> {
    List<Article> findAllByGroup(Group group);
}
