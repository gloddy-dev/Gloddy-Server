package com.gloddy.server.outbox.adapter.user.handler;

import com.gloddy.server.outbox.adapter.user.event.UserAdapterEvent;
import com.gloddy.server.outbox.adapter.user.mapper.UserEventMapper;
import com.gloddy.server.outbox.application.OutboxEventSaveService;
import com.gloddy.server.user.event.UserCreateEvent;
import com.gloddy.server.user.event.UserProfileUpdateEvent;
import com.gloddy.server.user.event.UserReliabilityUpgradeEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class UserAdapterHandler {

    private final OutboxEventSaveService outboxEventSaveService;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void handle(UserCreateEvent userCreateEvent) {
        UserAdapterEvent adapterEvent = UserEventMapper.toAdapterEvent(userCreateEvent);
        outboxEventSaveService.save(adapterEvent);
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void handle(UserProfileUpdateEvent userProfileUpdateEvent) {
        UserAdapterEvent adapterEvent = UserEventMapper.toAdapterEvent(userProfileUpdateEvent);
        outboxEventSaveService.save(adapterEvent);
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void handle(UserReliabilityUpgradeEvent userReliabilityUpgradeEvent) {
        UserAdapterEvent adapterEvent = UserEventMapper.toAdapterEvent(userReliabilityUpgradeEvent);
        outboxEventSaveService.save(adapterEvent);
    }
}
