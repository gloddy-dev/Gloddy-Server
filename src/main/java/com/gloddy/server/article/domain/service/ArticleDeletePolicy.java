package com.gloddy.server.article.domain.service;

import com.gloddy.server.article.domain.Article;
import com.gloddy.server.article.exception.NoAuthorizedArticleDeleteException;
import com.gloddy.server.group_member.domain.GroupMember;
import org.springframework.stereotype.Component;

@Component
public class ArticleDeletePolicy {

    public void validate(Article article, GroupMember groupMember) {
        validatePermission(article, groupMember);
    }

    private void validatePermission(Article article, GroupMember groupMember) {
        if (!article.isWriter(groupMember.getUser()) && !groupMember.isCaptain()) {
            throw new NoAuthorizedArticleDeleteException();
        }
    }
}
