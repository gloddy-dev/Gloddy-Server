package com.gloddy.server.outbox.domain.handler.impl;

import com.gloddy.server.core.error.handler.errorCode.ErrorCode;
import com.gloddy.server.core.error.handler.exception.EventBusinessException;
import com.gloddy.server.outbox.domain.UserEvent;
import com.gloddy.server.outbox.domain.dto.UserOutboxEventPayload;
import com.gloddy.server.outbox.domain.handler.UserOutboxEventQueryHandler;
import com.gloddy.server.outbox.infra.repository.UserEventJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserOutboxEventQueryHandlerImpl implements UserOutboxEventQueryHandler {

    private final UserEventJpaRepository userEventJpaRepository;

    @Override
    public UserEvent findById(Long id) {
        return userEventJpaRepository.findById(id)
                .orElseThrow(() -> new EventBusinessException(ErrorCode.OUTBOX_EVENT_NOT_FOUND));
    }

    @Override
    public List<UserOutboxEventPayload> findAllByNotPublished() {
        return userEventJpaRepository.findAllByNotPublished();
    }
}
