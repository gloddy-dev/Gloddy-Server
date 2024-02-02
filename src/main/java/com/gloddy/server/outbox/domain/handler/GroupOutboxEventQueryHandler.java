package com.gloddy.server.outbox.domain.handler;

import com.gloddy.server.outbox.domain.GroupEvent;
import com.gloddy.server.outbox.domain.dto.OutboxEventPayload;
import java.util.List;

public interface OutboxEventQueryHandler {
    GroupEvent findById(Long id);
    List<OutboxEventPayload> findAllByNotPublished();
}
