package com.gloddy.server.user.event.consumer;

import com.gloddy.server.user.application.UserCommandService;
import com.gloddy.server.group.event.GroupCreateEvent;
import com.gloddy.server.group_member.event.GroupMemberEstimateCompleteEvent;
import com.gloddy.server.group_member.event.GroupMemberLeaveEvent;
import com.gloddy.server.group_member.event.GroupMemberReceivePraiseEvent;
import com.gloddy.server.mate.event.MateCreateEvent;
import com.gloddy.server.user.domain.vo.ScoreMinusType;
import com.gloddy.server.user.domain.vo.ScorePlusType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class UserEventConsumer {

    private final UserCommandService userCommandService;

    @EventListener
    public void consume(GroupMemberReceivePraiseEvent event) {
        userCommandService.praise(event.getUserId(), event.getPraiseValue());
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void consume(GroupCreateEvent event) {
        userCommandService.upgradeReliability(event.getUserId(), ScorePlusType.Created_Group);
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void consume(GroupMemberEstimateCompleteEvent event) {
        userCommandService.upgradeReliability(event.getUserId(), ScorePlusType.Estimated);
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void consume(MateCreateEvent event) {
        userCommandService.upgradeReliability(event.getMatedUserId(), ScorePlusType.Mated);
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void consume(GroupMemberLeaveEvent event) {
        userCommandService.upgradeReliability(event.getUserId(), ScoreMinusType.Leaved_Group);
    }
}
