package com.gloddy.server.messaging.adapter.group.event;

import com.gloddy.server.messaging.AdapterEvent;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class GroupMemberAdapterEvent implements AdapterEvent {
    private Long userId;
    private Long groupId;
    private GroupMemberEventType eventType;
}
