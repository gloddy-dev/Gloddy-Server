package com.gloddy.server.outbox.infra.repository.custom;

import com.gloddy.server.outbox.domain.dto.GroupOutboxEventPayload;

import java.util.List;

public interface GroupEventJpaRepositoryCustom {
    List<GroupOutboxEventPayload> findAllByNotPublished();
}
