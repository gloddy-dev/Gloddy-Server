package com.gloddy.server.outbox.domain.handler.impl;

import com.gloddy.server.outbox.domain.GroupEvent;
import com.gloddy.server.outbox.domain.handler.OutboxEventCommandHandler;
import com.gloddy.server.outbox.infra.repository.EventJpaRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class OutboxEventCommandHandlerImpl implements OutboxEventCommandHandler {

    private final EventJpaRepository eventJpaRepository;

    @Override
    public GroupEvent save(GroupEvent groupEvent) {
        return eventJpaRepository.save(groupEvent);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updatePublished(Long eventId) {
        eventJpaRepository.updatePublished(eventId, LocalDateTime.now());
    }
}
