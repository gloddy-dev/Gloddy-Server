package com.gloddy.server.outbox.domain.handler;

import com.gloddy.server.outbox.domain.Event;
import com.gloddy.server.outbox.domain.dto.OutboxEventPayload;
import java.util.List;

public interface OutboxEventQueryHandler {
    Event findById(Long id);
    List<OutboxEventPayload> findAllByNotPublished();
}
