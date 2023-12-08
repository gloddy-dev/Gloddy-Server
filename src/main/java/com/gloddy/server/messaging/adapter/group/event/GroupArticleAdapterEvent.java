package com.gloddy.server.messaging.adapter.group.event;

import com.gloddy.server.article.event.GroupArticleCreateEvent;
import com.gloddy.server.messaging.AdapterEvent;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class GroupArticleAdapterEvent implements AdapterEvent {
    private Long articleId;
    private GroupArticleEventType eventType;
    private LocalDateTime eventDateTime;
}
