package com.gloddy.server.outbox.adapter.group.event;

import java.time.LocalDateTime;

import com.gloddy.server.outbox.adapter.GroupAdapterEvent;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class GroupMemberAdapterEvent implements GroupAdapterEvent {
    private Long groupId;
    private Long userId;
    private GroupMemberEventType eventType;
    private LocalDateTime eventDateTime;

    @Override
    public String getEventType() {
        return eventType.name();
    }
}
