package com.gloddy.server.group.event.producer.impl;

import com.gloddy.server.core.event.Event;
import com.gloddy.server.group.event.producer.GroupEventProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApplicationGroupEventProducer implements GroupEventProducer {

    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void produceEvent(Event event) {
        eventPublisher.publishEvent(event);
    }
}
