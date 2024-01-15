package com.gloddy.server.outbox.adapter.group.handler;

import com.gloddy.server.article.event.GroupArticleCreateEvent;
import com.gloddy.server.batch.group.event.GroupApproachingEvent;
import com.gloddy.server.batch.group.event.GroupEndEvent;
import com.gloddy.server.group_member.event.GroupMemberLeaveEvent;
import com.gloddy.server.outbox.adapter.group.event.GroupAdapterEvent;
import com.gloddy.server.outbox.adapter.group.event.GroupArticleAdapterEvent;
import com.gloddy.server.outbox.adapter.group.event.GroupMemberAdapterEvent;
import com.gloddy.server.outbox.adapter.group.mapper.GroupEventMapper;
import com.gloddy.server.outbox.application.OutboxEventSaveService;
import com.gloddy.server.outbox.event.producer.OutboxEventProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class GroupAdapterEventHandler {

    private final OutboxEventSaveService outboxEventSaveService;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void handle(GroupArticleCreateEvent event) {
        GroupArticleAdapterEvent adapterEvent = GroupEventMapper.mapToGroupArticleAdapterEvent(event);
        outboxEventSaveService.save(adapterEvent);
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void handle(GroupMemberLeaveEvent event) {
        GroupMemberAdapterEvent adapterEvent = GroupEventMapper.mapToGroupMemberAdapterEvent(event);
        outboxEventSaveService.save(adapterEvent);
    }

    @EventListener
    public void handle(GroupApproachingEvent event) {
        GroupAdapterEvent adapterEvent = GroupEventMapper.mapToGroupAdapterEvent(event);
        outboxEventSaveService.save(adapterEvent);
    }

    @EventListener
    public void handle(GroupEndEvent event) {
        GroupAdapterEvent adapterEvent = GroupEventMapper.mapToGroupAdapterEvent(event);
        outboxEventSaveService.save(adapterEvent);
    }
}
