package com.gloddy.server.outbox.domain.dto;

import com.gloddy.server.core.event.Event;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserOutboxEventPayload implements Event {
    private Long id;

    @QueryProjection
    public UserOutboxEventPayload(Long id) {
        this.id = id;
    }
}
