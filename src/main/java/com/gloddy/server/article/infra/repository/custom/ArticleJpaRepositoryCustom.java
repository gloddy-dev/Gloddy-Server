package com.gloddy.server.article.infra.repository.custom;

import com.gloddy.server.article.domain.Article;
import com.gloddy.server.group.domain.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArticleJpaRepositoryCustom {

    Page<Article> findAllByGroupFetchUserAndGroup(Group group, Pageable pageable);
}
