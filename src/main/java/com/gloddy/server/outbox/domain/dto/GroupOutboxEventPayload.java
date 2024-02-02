package com.gloddy.server.outbox.domain.dto;

import com.gloddy.server.core.event.Event;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GroupOutboxEventPayload implements Event {
    private Long id;

    @QueryProjection
    public GroupOutboxEventPayload(Long id) {
        this.id = id;
    }
}
