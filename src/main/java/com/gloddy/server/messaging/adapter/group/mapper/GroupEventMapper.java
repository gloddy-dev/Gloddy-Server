package com.gloddy.server.messaging.adapter.group.mapper;

import com.gloddy.server.article.event.GroupArticleCreateEvent;
import com.gloddy.server.group_member.event.GroupMemberLeaveEvent;
import com.gloddy.server.messaging.adapter.group.event.GroupArticleAdapterEvent;
import com.gloddy.server.messaging.adapter.group.event.GroupArticleEventType;
import com.gloddy.server.messaging.adapter.group.event.GroupMemberAdapterEvent;
import com.gloddy.server.messaging.adapter.group.event.GroupMemberEventType;
import java.time.LocalDateTime;

public class GroupEventMapper {

    public static GroupArticleAdapterEvent mapToGroupArticleAdapterEvent(GroupArticleCreateEvent event) {
        return new GroupArticleAdapterEvent(
                event.getArticleId(),
                GroupArticleEventType.from(event.isNotice()),
                LocalDateTime.now()
        );
    }

    public static GroupMemberAdapterEvent mapToGroupMemberAdapterEvent(GroupMemberLeaveEvent event) {
        return new GroupMemberAdapterEvent(
                event.getGroupMemberId(),
                GroupMemberEventType.GROUP_MEMBER_LEAVE,
                LocalDateTime.now()
        );
    }
}
