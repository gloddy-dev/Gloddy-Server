package com.gloddy.server.outbox.adapter.user.event;

import com.gloddy.server.outbox.adapter.AdapterEvent;
import com.gloddy.server.outbox.adapter.IUserAdapterEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class UserAdapterEvent implements IUserAdapterEvent {

    private Long userId;
    private UserEventType eventType;
    private LocalDateTime eventDateTime;

    @Override
    public String getEventType() {
        return eventType.name();
    }
}
