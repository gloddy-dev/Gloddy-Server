package com.gloddy.server.messaging.event.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gloddy.server.messaging.MessagePublisher;
import com.gloddy.server.outbox.domain.Event;
import com.gloddy.server.outbox.domain.dto.OutboxEventPayload;
import com.gloddy.server.outbox.domain.handler.OutboxEventCommandHandler;
import com.gloddy.server.outbox.domain.handler.OutboxEventQueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class OutboxEventConsumer {

    private final MessagePublisher messagePublisher;
    private final OutboxEventQueryHandler outboxEventQueryHandler;
    private final OutboxEventCommandHandler outboxEventCommandHandler;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(OutboxEventPayload payload) {
        Event outboxEvent = outboxEventQueryHandler.findById(payload.getId());
        messagePublisher.publishEvent(outboxEvent.getEvent(), outboxEvent.getEventType());
        outboxEventCommandHandler.updatePublished(outboxEvent.getId());
    }
}
