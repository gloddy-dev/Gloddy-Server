package com.gloddy.server.comment.domain.service;

import com.gloddy.server.article.exception.NoAuthorizedArticleDeleteException;
import com.gloddy.server.comment.domain.Comment;
import com.gloddy.server.group_member.domain.GroupMember;
import org.springframework.stereotype.Component;

@Component
public class CommentDeletePolicy {

    public void validate(GroupMember groupMember, Comment comment) {
        validatePermission(groupMember, comment);
    }

    private void validatePermission(GroupMember groupMember, Comment comment) {
        if (!comment.isWriter(groupMember.getUser()) && !groupMember.isCaptain()) {
            throw new NoAuthorizedArticleDeleteException();
        }
    }
}
