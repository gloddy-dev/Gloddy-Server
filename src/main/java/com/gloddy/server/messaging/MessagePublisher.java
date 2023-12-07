package com.gloddy.server.messaging;

import com.gloddy.server.messaging.adapter.group.event.GroupEvent;
import com.gloddy.server.messaging.adapter.apply.event.ApplyAdapterEvent;
import com.gloddy.server.messaging.adapter.group.event.GroupArticleAdapterEvent;
import com.gloddy.server.messaging.adapter.group.event.GroupMemberAdapterEvent;

public interface MessagePublisher {
    void publishApplyEvent(ApplyAdapterEvent event);
    void publishGroupMemberEvent(GroupMemberAdapterEvent event);
    void publishGroupArticleEvent(GroupArticleAdapterEvent event);

    void publishGroupStatusEvent(GroupEvent event);
}
