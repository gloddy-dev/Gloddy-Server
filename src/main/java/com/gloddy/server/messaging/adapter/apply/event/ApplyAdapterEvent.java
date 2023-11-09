package com.gloddy.server.messaging.adapter.apply.event;

import com.gloddy.server.messaging.AdapterEvent;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class ApplyAdapterEvent implements AdapterEvent {
    private Long userId;
    private Long applyGroupId;
    private Long applyUserId;
    private ApplyEventType eventType;
}
