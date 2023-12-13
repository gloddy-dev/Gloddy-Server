package com.gloddy.server.article.domain.handler;

import com.gloddy.server.article.domain.Article;
import com.gloddy.server.group.domain.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ArticleQueryHandler {
    Article findById(Long id);

    Article findByIdFetchUserAndGroup(Long id);

    Page<Article> findAllToGetArticlePreview(Group group, Pageable pageable);

    List<Article> findAllByGroupAndNotice(Group group, boolean isNotice);
}
