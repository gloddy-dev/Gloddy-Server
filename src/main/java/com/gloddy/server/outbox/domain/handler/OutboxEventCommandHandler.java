package com.gloddy.server.outbox.domain.handler;

import com.gloddy.server.outbox.domain.Event;

public interface OutboxEventCommandHandler {
    Event save(Event event);
    void updatePublished(Long eventId);
}
