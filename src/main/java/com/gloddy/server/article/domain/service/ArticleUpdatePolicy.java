package com.gloddy.server.article.domain.service;

import com.gloddy.server.article.domain.Article;
import com.gloddy.server.article.exception.NoAuthorizedArticleUpdateException;
import com.gloddy.server.auth.domain.User;
import org.springframework.stereotype.Component;

@Component
public class ArticleUpdatePolicy {

    public void validate(Article article, User user) {
        validateWriter(article, user);
    }

    private void validateWriter(Article article, User user) {
        if (!article.getUser().equals(user)) {
            throw new NoAuthorizedArticleUpdateException();
        }
    }
}
