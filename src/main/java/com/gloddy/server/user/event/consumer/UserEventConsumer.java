package com.gloddy.server.user.event.consumer;

import com.gloddy.server.auth.domain.User;
import com.gloddy.server.group.event.GroupCreateEvent;
import com.gloddy.server.group_member.event.GroupMemberEstimateCompleteEvent;
import com.gloddy.server.group_member.event.GroupMemberLeaveEvent;
import com.gloddy.server.group_member.event.GroupMemberReceivePraiseEvent;
import com.gloddy.server.mate.event.MateCreateEvent;
import com.gloddy.server.reliability.domain.vo.ScoreMinusType;
import com.gloddy.server.reliability.domain.vo.ScorePlusType;
import com.gloddy.server.reliability.domain.vo.ScoreType;
import com.gloddy.server.user.domain.handler.UserQueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class UserEventConsumer {

    private final UserQueryHandler userQueryHandler;

    @EventListener
    public void consume(GroupMemberReceivePraiseEvent event) {
        User user = userQueryHandler.findById(event.getUserId());
        user.receivePraise(event.getPraiseValue());
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void consume(GroupCreateEvent event) {
        User user = userQueryHandler.findById(event.getUserId());
        user.reflectReliability(ScorePlusType.Created_Group);
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void consume(GroupMemberEstimateCompleteEvent event) {
        User user = userQueryHandler.findById(event.getUserId());
        user.reflectReliability(ScorePlusType.Estimated);
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void consume(MateCreateEvent event) {
        User user = userQueryHandler.findById(event.getMatedUserId());
        user.reflectReliability(ScorePlusType.Mated);
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void consume(GroupMemberLeaveEvent event) {
        User user = userQueryHandler.findById(event.getUserId());
        user.reflectReliability(ScoreMinusType.Leaved_Group);
    }
}
