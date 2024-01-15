package com.gloddy.server.apply.application;

import com.gloddy.server.apply.domain.Apply;
import com.gloddy.server.apply.domain.dto.ApplyPayload;
import com.gloddy.server.apply.domain.handler.ApplyQueryHandler;
import com.gloddy.server.outbox.adapter.apply.event.ApplyEventType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplyPayloadService {

    private final ApplyQueryHandler applyQueryHandler;

    public ApplyPayload getPayload(Long applyId, ApplyEventType eventType) {
        Apply apply = applyQueryHandler.findById(applyId);

        return ApplyPayload.toDto(apply);
    }
}
