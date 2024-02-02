package com.gloddy.server.outbox.domain.handler;

import com.gloddy.server.outbox.domain.GroupEvent;

public interface OutboxEventCommandHandler {
    GroupEvent save(GroupEvent groupEvent);
    void updatePublished(Long eventId);
}
