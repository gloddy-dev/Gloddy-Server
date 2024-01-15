package com.gloddy.server.outbox.adapter.group.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GroupArticleEventType {
    CREATE_GENERAL_ARTICLE("그룹 일반 게시글이 생성될 떄"),
    CREATE_NOTICE_ARTICLE("그룹 공지글이 생성될 때");

    private final String description;

    public static GroupArticleEventType from(boolean isNoticeCreate) {
        if (isNoticeCreate) {
            return CREATE_NOTICE_ARTICLE;
        }
        return CREATE_GENERAL_ARTICLE;
    }
}
