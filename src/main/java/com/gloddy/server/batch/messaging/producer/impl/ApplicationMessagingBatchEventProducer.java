package com.gloddy.server.batch.messaging.producer.impl;

import com.gloddy.server.batch.messaging.producer.MessagingBatchEventProducer;
import com.gloddy.server.core.event.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApplicationMessagingBatchEventProducer implements MessagingBatchEventProducer {

    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void produceEvent(Event event) {
        eventPublisher.publishEvent(event);
    }
}
