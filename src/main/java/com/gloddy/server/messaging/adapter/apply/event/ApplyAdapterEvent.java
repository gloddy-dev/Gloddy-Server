package com.gloddy.server.messaging.adapter.apply.event;

import com.gloddy.server.messaging.AdapterEvent;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class ApplyAdapterEvent implements AdapterEvent {
    private Long applyId;
    private ApplyEventType eventType;
    private LocalDateTime eventDateTime;
}
