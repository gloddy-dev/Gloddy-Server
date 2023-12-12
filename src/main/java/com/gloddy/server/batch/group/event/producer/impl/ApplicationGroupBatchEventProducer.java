package com.gloddy.server.batch.group.event.producer.impl;

import com.gloddy.server.batch.group.event.producer.GroupBatchEventProducer;
import com.gloddy.server.core.event.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApplicationGroupBatchEventProducer implements GroupBatchEventProducer {

    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void produceEvent(Event event) {
        eventPublisher.publishEvent(event);
    }
}
