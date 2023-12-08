package com.gloddy.server.messaging.adapter.apply.mapper;

import com.gloddy.server.apply.domain.vo.Status;
import com.gloddy.server.apply.event.ApplyCreateEvent;
import com.gloddy.server.apply.event.ApplyStatusUpdateEvent;
import com.gloddy.server.messaging.adapter.apply.event.ApplyAdapterEvent;
import com.gloddy.server.messaging.adapter.apply.event.ApplyEventType;
import java.time.LocalDateTime;

public class ApplyEventMapper {

    public static ApplyAdapterEvent mapToApplyAdapterEventFrom(ApplyCreateEvent applyCreateEvent) {
        return new ApplyAdapterEvent(
                applyCreateEvent.getApplyId(),
                ApplyEventType.APPLY_CREATE,
                LocalDateTime.now()
        );
    }

    public static ApplyAdapterEvent mapToApplyAdapterEventFrom(ApplyStatusUpdateEvent applyStatusUpdateEvent) {
        return new ApplyAdapterEvent(
                applyStatusUpdateEvent.getApplyId(),
                getApplyEventType(applyStatusUpdateEvent.getStatus()),
                LocalDateTime.now()
        );
    }

    private static ApplyEventType getApplyEventType(Status status) {
        return switch (status) {
            case APPROVE -> ApplyEventType.APPLY_APPROVE;
            case REFUSE -> ApplyEventType.APPLY_REFUSE;
            default -> throw new RuntimeException("invalid apply status");
        };
    }
}
