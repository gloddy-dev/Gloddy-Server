package com.gloddy.server.outbox.infra.repository.custom;

import com.gloddy.server.outbox.domain.dto.OutboxEventPayload;
import java.util.List;

public interface EventJpaRepositoryCustom {
    List<OutboxEventPayload> findAllByNotPublished();
}
