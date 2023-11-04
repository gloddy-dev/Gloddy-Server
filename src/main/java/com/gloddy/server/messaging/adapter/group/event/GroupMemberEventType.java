package com.gloddy.server.messaging.adapter.group.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum GroupMemberEventType {
    GROUP_MEMBER_LEAVE("그룹 멤버가 모임을 나갔을 때");

    private final String description;
}
