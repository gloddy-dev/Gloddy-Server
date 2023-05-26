package com.gloddy.server.article.domain.service;

import com.gloddy.server.article.exception.NoAuthorizedNoticeArticleException;
import com.gloddy.server.user_group.domain.UserGroup;
import org.springframework.stereotype.Component;

@Component
public class NoticeArticleCreatePolicy {

    public void validate(UserGroup userGroup) {
        if (!userGroup.isCaptain()) {
            throw new NoAuthorizedNoticeArticleException();
        }
    }
}
