package com.gloddy.server.core.event.reliability;

import com.gloddy.server.reliability.service.ReliabilityLevelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class ReliabilityLevelEventHandler {
    private final ReliabilityLevelService reliabilityLevelService;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void updateReliabilityLevelListener(ReliabilityLevelUpdateEvent event) {
        reliabilityLevelService.update(event.getUser(), event.getScore());
    }
}
