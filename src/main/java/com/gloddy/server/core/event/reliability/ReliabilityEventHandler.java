package com.gloddy.server.core.event.reliability;

import com.gloddy.server.reliability.service.ReliabilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;


@Component
@RequiredArgsConstructor
public class ReliabilityEventHandler {
    private final ReliabilityService scoreService;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void updateReliabilityScoreListener(ReliabilityScoreUpdateEvent event) {
        scoreService.update(event.getUseId(), event.getType());
    }
}
