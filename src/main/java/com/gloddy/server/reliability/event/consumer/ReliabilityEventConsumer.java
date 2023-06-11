package com.gloddy.server.reliability.event.consumer;

import com.gloddy.server.core.event.GroupParticipateEvent;
import com.gloddy.server.group.event.GroupCreateEvent;
import com.gloddy.server.group_member.event.GroupMemberEstimateCompleteEvent;
import com.gloddy.server.mate.event.MateCreateEvent;
import com.gloddy.server.praise.event.PraiseCountUpdateEvent;
import com.gloddy.server.reliability.application.ReliabilitySaveService;
import com.gloddy.server.reliability.application.ReliabilityService;
import com.gloddy.server.reliability.domain.vo.ScoreType;
import com.gloddy.server.user.event.UserCreateEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class ReliabilityEventConsumer {

    private final ReliabilityService reliabilityService;
    private final ReliabilitySaveService reliabilitySaveService;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void consume(UserCreateEvent event) {
        reliabilitySaveService.init(event.getUser());
    }

    @EventListener
    public void consume(PraiseCountUpdateEvent event) {
        if (event.isAbsenceCountUpdate()) {
            reliabilityService.update(event.getUserId(), ScoreType.Absence_Group);
        } else {
            reliabilityService.update(event.getUserId(), ScoreType.Praised);
        }
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void consume(GroupCreateEvent event) {
        reliabilityService.update(event.getUserId(), ScoreType.Created_Group);
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void consume(GroupMemberEstimateCompleteEvent event) {
        reliabilityService.update(event.getUserId(), ScoreType.Estimated);
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void consume(MateCreateEvent event) {
        reliabilityService.update(event.getMatedUserId(), ScoreType.Mated);
    }
}
