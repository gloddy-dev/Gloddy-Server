package com.gloddy.server.outbox.domain.handler.impl;

import static com.gloddy.server.core.error.handler.errorCode.ErrorCode.OUTBOX_EVENT_NOT_FOUND;

import com.gloddy.server.core.error.handler.exception.EventBusinessException;
import com.gloddy.server.outbox.domain.Event;
import com.gloddy.server.outbox.domain.handler.OutboxEventQueryHandler;
import com.gloddy.server.outbox.infra.repository.EventJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OutboxEventQueryHandlerImpl implements OutboxEventQueryHandler {

    private final EventJpaRepository eventJpaRepository;

    @Override
    public Event findById(Long id) {
        return eventJpaRepository.findById(id)
                .orElseThrow(() -> new EventBusinessException(OUTBOX_EVENT_NOT_FOUND));
    }
}
