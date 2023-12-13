package com.gloddy.server.article.infra.repository.custom;

import com.gloddy.server.article.domain.Article;
import com.gloddy.server.group.domain.Group;
import com.gloddy.server.user.domain.User;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArticleJpaRepositoryCustom {

    Page<Article> findAllByGroupFetchUserAndGroup(Group group, Pageable pageable);

    Optional<Article> findByIdFetchUserAndGroup(Long articleId);
}
