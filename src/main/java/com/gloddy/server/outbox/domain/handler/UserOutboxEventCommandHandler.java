package com.gloddy.server.outbox.domain.handler;

import com.gloddy.server.outbox.domain.UserEvent;

public interface UserOutboxEventCommandHandler {
    UserEvent save(UserEvent userEvent);

    void updatePublished(Long eventId);
}
