package com.gloddy.server.apply.event.producer.impl;

import com.gloddy.server.apply.event.producer.ApplyEventProducer;
import com.gloddy.server.core.event.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApplicationApplyEventProducer implements ApplyEventProducer {

    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void produceEvent(Event event) {
        eventPublisher.publishEvent(event);
    }
}
