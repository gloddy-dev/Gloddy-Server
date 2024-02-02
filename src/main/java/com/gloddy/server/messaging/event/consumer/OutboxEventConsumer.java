package com.gloddy.server.messaging.event.consumer;

import static com.gloddy.server.config.AsyncConfig.EVENT_HANDLER_TASK_EXECUTOR;

import com.gloddy.server.messaging.MessagePublisher;
import com.gloddy.server.outbox.domain.GroupEvent;
import com.gloddy.server.outbox.domain.UserEvent;
import com.gloddy.server.outbox.domain.dto.GroupOutboxEventPayload;
import com.gloddy.server.outbox.domain.dto.UserOutboxEventPayload;
import com.gloddy.server.outbox.domain.handler.GroupOutboxEventCommandHandler;
import com.gloddy.server.outbox.domain.handler.GroupOutboxEventQueryHandler;
import com.gloddy.server.outbox.domain.handler.UserOutboxEventCommandHandler;
import com.gloddy.server.outbox.domain.handler.UserOutboxEventQueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class OutboxEventConsumer {

    private final MessagePublisher messagePublisher;
    private final GroupOutboxEventQueryHandler groupOutboxEventQueryHandler;
    private final GroupOutboxEventCommandHandler groupOutboxEventCommandHandler;
    private final UserOutboxEventCommandHandler userOutboxEventCommandHandler;
    private final UserOutboxEventQueryHandler userOutboxEventQueryHandler;

    @Async(EVENT_HANDLER_TASK_EXECUTOR)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional
    public void handle(GroupOutboxEventPayload payload) {
        GroupEvent outboxEvent = groupOutboxEventQueryHandler.findById(payload.getId());
        groupOutboxEventCommandHandler.updatePublished(outboxEvent.getId());
        messagePublisher.publishEvent(outboxEvent.getEvent(), outboxEvent.getEventType());
    }

    @Async(EVENT_HANDLER_TASK_EXECUTOR)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional
    public void handle(UserOutboxEventPayload payload) {
        UserEvent userEvent = userOutboxEventQueryHandler.findById(payload.getId());
        userOutboxEventCommandHandler.updatePublished(userEvent.getId());
        messagePublisher.publishEvent(userEvent.getEvent(), userEvent.getEventType());
    }
}
