package com.gloddy.server.messaging.adapter.group.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum GroupStatusEventType {
    APPROACHING_GROUP("모임 시작이 임박할 때"),
    END_GROUP("모임이 완료됏을 때");

    private final String description;
}
