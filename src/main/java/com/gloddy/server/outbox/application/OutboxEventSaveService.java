package com.gloddy.server.outbox.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gloddy.server.outbox.adapter.AdapterEvent;
import com.gloddy.server.outbox.adapter.IGroupAdapterEvent;
import com.gloddy.server.outbox.adapter.IUserAdapterEvent;
import com.gloddy.server.outbox.domain.GroupEvent;
import com.gloddy.server.outbox.domain.UserEvent;
import com.gloddy.server.outbox.domain.dto.GroupOutboxEventPayload;
import com.gloddy.server.outbox.domain.dto.UserOutboxEventPayload;
import com.gloddy.server.outbox.domain.handler.GroupOutboxEventCommandHandler;
import com.gloddy.server.outbox.domain.handler.UserOutboxEventCommandHandler;
import com.gloddy.server.outbox.event.producer.OutboxEventProducer;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OutboxEventSaveService {
    private final GroupOutboxEventCommandHandler groupOutboxEventCommandHandler;
    private final UserOutboxEventCommandHandler userOutboxEventCommandHandler;
    private final OutboxEventProducer outboxEventProducer;
    private final ObjectMapper objectMapper;

    public void save(IGroupAdapterEvent groupAdapterEvent) {
        GroupEvent groupEvent = new GroupEvent(toMap(groupAdapterEvent), groupAdapterEvent.getEventType());
        GroupEvent outboxEvent = groupOutboxEventCommandHandler.save(groupEvent);
        outboxEventProducer.produceEvent(new GroupOutboxEventPayload(outboxEvent.getId()));
    }

    public void save(IUserAdapterEvent userAdapterEvent) {
        UserEvent userEvent = new UserEvent(toMap(userAdapterEvent), userAdapterEvent.getEventType());
        UserEvent saveUserEvent = userOutboxEventCommandHandler.save(userEvent);
        outboxEventProducer.produceEvent(new UserOutboxEventPayload(saveUserEvent.getId()));
    }

    private Map<String, Object> toMap(AdapterEvent event) {
        return objectMapper.convertValue(event, Map.class);
    }
}
