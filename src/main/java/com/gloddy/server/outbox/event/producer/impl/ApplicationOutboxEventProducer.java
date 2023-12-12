package com.gloddy.server.outbox.event.producer.impl;

import com.gloddy.server.outbox.domain.dto.OutboxEventPayload;
import com.gloddy.server.outbox.event.producer.OutboxEventProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApplicationOutboxEventProducer implements OutboxEventProducer {

    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void produceEvent(OutboxEventPayload payload) {
        eventPublisher.publishEvent(payload);
    }
}
