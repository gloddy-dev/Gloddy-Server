package com.gloddy.server.outbox.infra.repository.custom;

import com.gloddy.server.outbox.domain.dto.UserOutboxEventPayload;

import java.util.List;

public interface UserEventJpaRepositoryCustom {
    List<UserOutboxEventPayload> findAllByNotPublished();
}
