package com.gloddy.server.core.utils.event;

import com.gloddy.server.estimate.service.PraiseSaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class UserEventHandler {

    private final PraiseSaveService praiseSaveService;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    @Transactional
    public void userCreateListener(UserCreateEvent userCreateEvent) {
        praiseSaveService.initPraiseSaveBy(userCreateEvent.getUser());
    }
}
