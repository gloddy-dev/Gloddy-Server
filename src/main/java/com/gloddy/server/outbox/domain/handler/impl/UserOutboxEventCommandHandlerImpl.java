package com.gloddy.server.outbox.domain.handler.impl;

import com.gloddy.server.outbox.domain.UserEvent;
import com.gloddy.server.outbox.domain.handler.UserOutboxEventCommandHandler;
import com.gloddy.server.outbox.infra.repository.UserEventJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
@RequiredArgsConstructor
public class UserOutboxEventCommandHandlerImpl implements UserOutboxEventCommandHandler {

    private final UserEventJpaRepository userEventJpaRepository;

    @Override
    public UserEvent save(UserEvent userEvent) {
        return userEventJpaRepository.save(userEvent);
    }

    @Override
    public void updatePublished(Long eventId) {
        userEventJpaRepository.updatePublished(eventId, LocalDateTime.now());
    }
}
