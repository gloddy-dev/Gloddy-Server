package com.gloddy.server.core.event.user;

import com.gloddy.server.estimate.service.praise.PraiseSaveService;
import com.gloddy.server.reliability.service.ReliabilitySaveService;
import com.gloddy.server.reliability.service.ReliabilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class UserEventHandler {

    private final PraiseSaveService praiseSaveService;
    private final ReliabilitySaveService reliabilitySaveService;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    @Transactional
    public void userCreateListener(UserCreateEvent userCreateEvent) {
        praiseSaveService.initPraiseSaveBy(userCreateEvent.getUser());
        reliabilitySaveService.init(userCreateEvent.getUser());
    }
}
