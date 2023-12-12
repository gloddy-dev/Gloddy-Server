package com.gloddy.server.outbox.domain.handler;

import com.gloddy.server.outbox.domain.Event;

public interface OutboxEventQueryHandler {
    Event findById(Long id);
}
