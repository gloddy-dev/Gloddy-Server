package com.gloddy.server.mate.event.producer.impl;

import com.gloddy.server.core.event.Event;
import com.gloddy.server.mate.event.producer.MateEventProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApplicationMateEventProducer implements MateEventProducer {

    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void produceEvent(Event event) {
        eventPublisher.publishEvent(event);
    }
}
