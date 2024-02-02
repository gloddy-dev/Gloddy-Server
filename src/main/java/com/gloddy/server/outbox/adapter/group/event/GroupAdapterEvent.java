package com.gloddy.server.outbox.adapter.group.event;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class GroupAdapterEvent implements com.gloddy.server.outbox.adapter.GroupAdapterEvent {
    private Long groupId;
    private GroupEventType eventType;
    private LocalDateTime eventDateTime;

    @Override
    public String getEventType() {
        return eventType.name();
    }
}
