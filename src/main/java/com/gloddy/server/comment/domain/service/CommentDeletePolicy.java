package com.gloddy.server.comment.domain.service;

import com.gloddy.server.article.exception.NoAuthorizedArticleDeleteException;
import com.gloddy.server.comment.domain.Comment;
import com.gloddy.server.user_group.domain.UserGroup;
import org.springframework.stereotype.Component;

@Component
public class CommentDeletePolicy {

    public void validate(UserGroup userGroup, Comment comment) {
        validatePermission(userGroup, comment);
    }

    private void validatePermission(UserGroup userGroup, Comment comment) {
        if (!comment.isWriter(userGroup.getUser()) && !userGroup.isCaptain()) {
            throw new NoAuthorizedArticleDeleteException();
        }
    }
}
