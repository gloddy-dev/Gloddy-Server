package com.gloddy.server.outbox.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OutboxEventPayload {
    private Long id;
}
