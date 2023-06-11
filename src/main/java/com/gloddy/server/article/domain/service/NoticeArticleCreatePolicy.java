package com.gloddy.server.article.domain.service;

import com.gloddy.server.article.exception.NoAuthorizedNoticeArticleException;
import com.gloddy.server.group_member.domain.GroupMember;
import org.springframework.stereotype.Component;

@Component
public class NoticeArticleCreatePolicy {

    public void validate(GroupMember groupMember) {
        if (!groupMember.isCaptain()) {
            throw new NoAuthorizedNoticeArticleException();
        }
    }
}
