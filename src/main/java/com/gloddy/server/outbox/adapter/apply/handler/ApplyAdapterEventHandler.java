package com.gloddy.server.outbox.adapter.apply.handler;

import com.gloddy.server.apply.event.ApplyCreateEvent;
import com.gloddy.server.apply.event.ApplyStatusUpdateEvent;
import com.gloddy.server.outbox.adapter.apply.event.ApplyAdapterEvent;
import com.gloddy.server.outbox.adapter.apply.mapper.ApplyEventMapper;
import com.gloddy.server.outbox.application.OutboxEventSaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class ApplyAdapterEventHandler {

    private final OutboxEventSaveService outboxEventSaveService;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void handle(ApplyCreateEvent event) {
        ApplyAdapterEvent adapterEvent = ApplyEventMapper.mapToApplyAdapterEventFrom(event);
        outboxEventSaveService.save(adapterEvent);
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void handle(ApplyStatusUpdateEvent event) {
        ApplyAdapterEvent adapterEvent = ApplyEventMapper.mapToApplyAdapterEventFrom(event);
        outboxEventSaveService.save(adapterEvent);
    }
}
