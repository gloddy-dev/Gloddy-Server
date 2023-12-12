package com.gloddy.server.messaging.sns;

import lombok.Getter;

@Getter
public enum Topic {
    APPLY_CREATE("지원서가 생성 되었을 때", "apply-topic"),
    APPLY_APPROVE("지원서가 승인 처리 되었을 때", "apply-topic"),
    APPLY_REFUSE("지원서가 거절 처리 되었을 때", "apply-topic"),

    CREATE_GENERAL_ARTICLE("그룹 일반 게시글이 생성될 떄", "group-article-topic"),
    CREATE_NOTICE_ARTICLE("그룹 공지글이 생성될 때", "group-article-topic"),

    APPROACHING_GROUP("모임 시작이 임박할 때", "group-article-topic"),
    END_GROUP("모임이 완료됏을 때", "group-article-topic"),

    GROUP_MEMBER_LEAVE("그룹 멤버가 모임을 나갔을 때", "group-member-topic");

    private final String description;
    private final String topicProperty;

    Topic(String description, String topicProperty) {
        this.description = description;
        this.topicProperty = topicProperty;
    }

    public static String getTopicProperty(String eventType) {
        return Topic.valueOf(eventType).topicProperty;
    }
}
