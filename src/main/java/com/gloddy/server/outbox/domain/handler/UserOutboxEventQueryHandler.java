package com.gloddy.server.outbox.domain.handler;

import com.gloddy.server.outbox.domain.UserEvent;
import com.gloddy.server.outbox.domain.dto.UserOutboxEventPayload;

import java.util.List;

public interface UserOutboxEventQueryHandler {
    UserEvent findById(Long id);

    List<UserOutboxEventPayload> findAllByNotPublished();
}
