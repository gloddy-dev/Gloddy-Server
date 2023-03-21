package com.gloddy.server.core.event.reliability;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReliabilityLevelEventPublisher {
    private final ApplicationEventPublisher publisher;

    public void publish(ReliabilityLevelUpdateEvent event) {
        publisher.publishEvent(event);
    }
}
