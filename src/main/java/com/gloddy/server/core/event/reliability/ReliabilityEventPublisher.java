package com.gloddy.server.core.event.reliability;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReliabilityEventPublisher {
    private final ApplicationEventPublisher publisher;

    public void publish(ReliabilityScoreUpdateEvent event) {
        publisher.publishEvent(event);
    }
}
