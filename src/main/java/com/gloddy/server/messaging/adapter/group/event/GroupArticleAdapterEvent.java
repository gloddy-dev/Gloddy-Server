package com.gloddy.server.messaging.adapter.group.event;

import com.gloddy.server.article.event.GroupArticleCreateEvent;
import com.gloddy.server.messaging.AdapterEvent;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class GroupArticleAdapterEvent implements AdapterEvent {
    private Long userId;
    private Long groupId;
    private List<Long> groupMemberUserIds;
    private Long articleId;
    private GroupArticleEventType eventType;

    public static GroupArticleAdapterEvent from(GroupArticleCreateEvent event) {
        return new GroupArticleAdapterEvent(
                event.getUserId(),
                event.getGroupId(),
                event.getGroupMemberUserIds(),
                event.getArticleId(),
                GroupArticleEventType.from(event.isNotice())
        );
    }
}
