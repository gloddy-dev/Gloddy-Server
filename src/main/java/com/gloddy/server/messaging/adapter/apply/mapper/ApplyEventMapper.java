package com.gloddy.server.messaging.adapter.apply.mapper;

import com.gloddy.server.apply.domain.vo.Status;
import com.gloddy.server.apply.event.ApplyCreateEvent;
import com.gloddy.server.apply.event.ApplyStatusUpdateEvent;
import com.gloddy.server.messaging.adapter.apply.event.ApplyAdapterEvent;
import com.gloddy.server.messaging.adapter.apply.event.ApplyEventType;

public class ApplyEventMapper {

    public static ApplyAdapterEvent mapToApplyAdapterEventFrom(ApplyCreateEvent applyCreateEvent) {
        return new ApplyAdapterEvent(
                applyCreateEvent.getUserId(),
                applyCreateEvent.getGroupId(),
                applyCreateEvent.getApplyId(),
                ApplyEventType.APPLY_CREATE
        );
    }

    public static ApplyAdapterEvent mapToApplyAdapterEventFrom(ApplyStatusUpdateEvent applyStatusUpdateEvent) {
        return new ApplyAdapterEvent(
                applyStatusUpdateEvent.getUserId(),
                applyStatusUpdateEvent.getGroupId(),
                applyStatusUpdateEvent.getApplyId(),
                getApplyEventType(applyStatusUpdateEvent.getStatus())
        );
    }

    private static ApplyEventType getApplyEventType(Status status) {
        if (status.isApprove()) {
            return ApplyEventType.APPLY_APPROVE;
        } else if (status.isRefuse()) {
            return ApplyEventType.APPLY_REFUSE;
        } else {
            throw new RuntimeException("invalid apply status");
        }
    }
}
