package com.gloddy.server.core.event.score;

import com.gloddy.server.core.event.reliability.ReliabilityLevelEventPublisher;
import com.gloddy.server.core.event.reliability.ReliabilityLevelUpdateEvent;
import com.gloddy.server.reliability.service.ScoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;


@Component
@RequiredArgsConstructor
public class ScoreEventHandler {
    private final ScoreService scoreService;
    private final ReliabilityLevelEventPublisher reliabilityEventPublisher;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void updateScoreListener(ScoreUpdateEvent event) {
        Long score = scoreService.update(event.getUser(), event.getType());
        reliabilityEventPublisher.publish(new ReliabilityLevelUpdateEvent(event.getUser(), score));
    }
}
