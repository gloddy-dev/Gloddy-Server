package com.gloddy.server.outbox.adapter.apply.event;

import java.time.LocalDateTime;

import com.gloddy.server.outbox.adapter.GroupAdapterEvent;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class ApplyAdapterEvent implements GroupAdapterEvent {
    private Long applyId;
    private ApplyEventType eventType;
    private LocalDateTime eventDateTime;

    @Override
    public String getEventType() {
        return eventType.name();
    }
}
