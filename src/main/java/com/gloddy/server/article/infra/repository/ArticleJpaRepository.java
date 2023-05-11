package com.gloddy.server.article.infra.repository;

import com.gloddy.server.article.domain.Article;
import com.gloddy.server.group.domain.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleJpaRepository extends JpaRepository<Article, Long> {
    Page<Article> findAllByGroup(Group group, Pageable pageable);
}
