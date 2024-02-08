package com.gloddy.server.outbox.domain.handler.impl;

import static com.gloddy.server.core.error.handler.errorCode.ErrorCode.OUTBOX_EVENT_NOT_FOUND;

import com.gloddy.server.core.error.handler.exception.EventBusinessException;
import com.gloddy.server.outbox.domain.GroupEvent;
import com.gloddy.server.outbox.domain.dto.GroupOutboxEventPayload;
import com.gloddy.server.outbox.domain.handler.GroupOutboxEventQueryHandler;
import com.gloddy.server.outbox.infra.repository.GroupEventJpaRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class GroupOutboxEventQueryHandlerImpl implements GroupOutboxEventQueryHandler {

    private final GroupEventJpaRepository groupEventJpaRepository;

    @Override
    public GroupEvent findById(Long id) {
        return groupEventJpaRepository.findById(id)
                .orElseThrow(() -> new EventBusinessException(OUTBOX_EVENT_NOT_FOUND));
    }

    @Override
    public List<GroupOutboxEventPayload> findAllByNotPublished() {
        return groupEventJpaRepository.findAllByNotPublished();
    }
}
