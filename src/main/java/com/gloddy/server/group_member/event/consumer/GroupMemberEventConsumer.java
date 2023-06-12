package com.gloddy.server.group_member.event.consumer;

import com.gloddy.server.core.event.GroupParticipateEvent;
import com.gloddy.server.group_member.application.GroupMemberSaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class GroupMemberEventConsumer {

    private final GroupMemberSaveService groupMemberSaveService;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void consume(GroupParticipateEvent event) {
        groupMemberSaveService.saveUserGroup(event.getUserId(), event.getGroupId());
    }
}
