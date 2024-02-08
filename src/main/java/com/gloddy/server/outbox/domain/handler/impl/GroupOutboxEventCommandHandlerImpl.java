package com.gloddy.server.outbox.domain.handler.impl;

import com.gloddy.server.outbox.domain.GroupEvent;
import com.gloddy.server.outbox.domain.handler.GroupOutboxEventCommandHandler;
import com.gloddy.server.outbox.infra.repository.GroupEventJpaRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class GroupOutboxEventCommandHandlerImpl implements GroupOutboxEventCommandHandler {

    private final GroupEventJpaRepository groupEventJpaRepository;

    @Override
    public GroupEvent save(GroupEvent groupEvent) {
        return groupEventJpaRepository.save(groupEvent);
    }

    @Override
    @Transactional
    public void updatePublished(Long eventId) {
        groupEventJpaRepository.updatePublished(eventId, LocalDateTime.now());
    }
}
