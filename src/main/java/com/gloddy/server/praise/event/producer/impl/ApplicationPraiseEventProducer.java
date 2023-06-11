package com.gloddy.server.praise.event.producer.impl;

import com.gloddy.server.core.event.Event;
import com.gloddy.server.praise.event.producer.PraiseEventProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApplicationPraiseEventProducer implements PraiseEventProducer {

    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void produceEvent(Event event) {
        eventPublisher.publishEvent(event);
    }
}
