package com.gloddy.server.praise.event.consumer;

import com.gloddy.server.group_member.event.GroupMemberReceivePraiseEvent;
import com.gloddy.server.praise.application.PraiseSaveService;
import com.gloddy.server.praise.application.PraiseService;
import com.gloddy.server.user.event.UserCreateEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class PraiseEventConsumer {

    private final PraiseSaveService praiseSaveService;
    private final PraiseService praiseService;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void consume(UserCreateEvent event) {
        praiseSaveService.initPraiseSaveBy(event.getUser());
    }

    @EventListener
    public void consume(GroupMemberReceivePraiseEvent event) {
        praiseService.updatePraisePoint(event.getUserId(), event.getPraiseValue());
    }
}
