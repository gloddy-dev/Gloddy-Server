package com.gloddy.server.messaging.adapter.group.handler;

import com.gloddy.server.article.event.GroupArticleCreateEvent;
import com.gloddy.server.messaging.MessagePublisher;
import com.gloddy.server.messaging.adapter.group.event.GroupArticleAdapterEvent;
import com.gloddy.server.messaging.adapter.group.mapper.GroupEventMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class GroupArticleAdapterEventHandler {

    private final MessagePublisher messagePublisher;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(GroupArticleCreateEvent event) {
        GroupArticleAdapterEvent adapterEvent = GroupEventMapper.mapToGroupArticleAdapterEvent(event);
        messagePublisher.publishGroupArticleEvent(adapterEvent);
    }
}
