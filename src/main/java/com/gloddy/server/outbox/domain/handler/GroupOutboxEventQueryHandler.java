package com.gloddy.server.outbox.domain.handler;

import com.gloddy.server.outbox.domain.GroupEvent;
import com.gloddy.server.outbox.domain.dto.GroupOutboxEventPayload;
import java.util.List;

public interface GroupOutboxEventQueryHandler {
    GroupEvent findById(Long id);
    List<GroupOutboxEventPayload> findAllByNotPublished();
}
