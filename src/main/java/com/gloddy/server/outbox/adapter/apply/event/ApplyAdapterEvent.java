package com.gloddy.server.outbox.adapter.apply.event;

import java.time.LocalDateTime;

import com.gloddy.server.outbox.adapter.IGroupAdapterEvent;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class ApplyAdapterEvent implements IGroupAdapterEvent {
    private Long applyId;
    private ApplyEventType eventType;
    private LocalDateTime eventDateTime;

    @Override
    public String getEventType() {
        return eventType.name();
    }
}
