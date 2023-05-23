package com.gloddy.server.article.domain.service;

import com.gloddy.server.article.domain.Article;
import com.gloddy.server.article.exception.NoAuthorizedArticleDeleteException;
import com.gloddy.server.user_group.domain.UserGroup;
import org.springframework.stereotype.Component;

@Component
public class ArticleDeletePolicy {

    public void validate(Article article, UserGroup userGroup) {
        validatePermission(article, userGroup);
    }

    private void validatePermission(Article article, UserGroup userGroup) {
        if (!article.isWriter(userGroup.getUser()) && !userGroup.isCaptain()) {
            throw new NoAuthorizedArticleDeleteException();
        }
    }
}
