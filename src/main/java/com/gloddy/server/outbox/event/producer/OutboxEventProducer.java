package com.gloddy.server.outbox.event.producer;

import com.gloddy.server.outbox.domain.dto.OutboxEventPayload;

public interface OutboxEventProducer {
    void produceEvent(OutboxEventPayload payload);
}
