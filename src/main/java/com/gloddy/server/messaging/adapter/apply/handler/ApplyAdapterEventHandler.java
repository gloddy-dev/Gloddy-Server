package com.gloddy.server.messaging.adapter.apply.handler;

import com.gloddy.server.apply.event.ApplyCreateEvent;
import com.gloddy.server.apply.event.ApplyStatusUpdateEvent;
import com.gloddy.server.messaging.MessagePublisher;
import com.gloddy.server.messaging.adapter.apply.event.ApplyAdapterEvent;
import com.gloddy.server.messaging.adapter.apply.mapper.ApplyEventMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class ApplyAdapterEventHandler {

    private final MessagePublisher messagePublisher;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(ApplyCreateEvent event) {
        ApplyAdapterEvent adapterEvent = ApplyEventMapper.mapToApplyAdapterEventFrom(event);
        messagePublisher.publishApplyEvent(adapterEvent);
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(ApplyStatusUpdateEvent event) {
        ApplyAdapterEvent adapterEvent = ApplyEventMapper.mapToApplyAdapterEventFrom(event);
        messagePublisher.publishApplyEvent(adapterEvent);
    }
}
