package com.gloddy.server.user.event.producer.impl;

import com.gloddy.server.core.event.Event;
import com.gloddy.server.user.event.producer.UserEventProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApplicationUserEventProducer implements UserEventProducer {

    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void produceEvent(Event event) {
        eventPublisher.publishEvent(event);
    }
}
