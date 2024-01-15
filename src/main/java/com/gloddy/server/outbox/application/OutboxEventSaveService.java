package com.gloddy.server.outbox.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gloddy.server.outbox.adapter.AdapterEvent;
import com.gloddy.server.outbox.domain.Event;
import com.gloddy.server.outbox.domain.dto.OutboxEventPayload;
import com.gloddy.server.outbox.domain.handler.OutboxEventCommandHandler;
import com.gloddy.server.outbox.event.producer.OutboxEventProducer;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OutboxEventSaveService {
    private final OutboxEventCommandHandler outboxEventCommandHandler;
    private final OutboxEventProducer outboxEventProducer;
    private final ObjectMapper objectMapper;

    public void save(AdapterEvent adapterEvent) {
        Event event = new Event(toMap(adapterEvent), adapterEvent.getEventType());
        Event outboxEvent = outboxEventCommandHandler.save(event);
        outboxEventProducer.produceEvent(new OutboxEventPayload(outboxEvent.getId()));
    }

    private Map<String, Object> toMap(AdapterEvent event) {
        return objectMapper.convertValue(event, Map.class);
    }
}
