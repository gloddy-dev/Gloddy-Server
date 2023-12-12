package com.gloddy.server.outbox.adapter.group.mapper;

import com.gloddy.server.article.event.GroupArticleCreateEvent;
import com.gloddy.server.batch.group.event.GroupApproachingEvent;
import com.gloddy.server.batch.group.event.GroupEndEvent;
import com.gloddy.server.group_member.event.GroupMemberLeaveEvent;
import com.gloddy.server.outbox.adapter.group.event.GroupAdapterEvent;
import com.gloddy.server.outbox.adapter.group.event.GroupArticleAdapterEvent;
import com.gloddy.server.outbox.adapter.group.event.GroupArticleEventType;
import com.gloddy.server.outbox.adapter.group.event.GroupEventType;
import com.gloddy.server.outbox.adapter.group.event.GroupMemberAdapterEvent;
import com.gloddy.server.outbox.adapter.group.event.GroupMemberEventType;
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
                event.getGroupId(),
                event.getUserId(),
                GroupMemberEventType.GROUP_MEMBER_LEAVE,
                LocalDateTime.now()
        );
    }

    public static GroupAdapterEvent mapToGroupAdapterEvent(GroupApproachingEvent event) {
        return new GroupAdapterEvent(
                event.getGroupId(),
                GroupEventType.APPROACHING_GROUP,
                LocalDateTime.now()
        );
    }

    public static GroupAdapterEvent mapToGroupAdapterEvent(GroupEndEvent event) {
        return new GroupAdapterEvent(
                event.getGroupId(),
                GroupEventType.END_GROUP,
                LocalDateTime.now()
        );
    }
}
