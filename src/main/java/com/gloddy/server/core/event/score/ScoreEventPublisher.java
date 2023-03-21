package com.gloddy.server.core.event.score;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScoreEventPublisher {
    private final ApplicationEventPublisher publisher;

    public void publish(ScoreUpdateEvent event) {
        publisher.publishEvent(event);
    }
}
