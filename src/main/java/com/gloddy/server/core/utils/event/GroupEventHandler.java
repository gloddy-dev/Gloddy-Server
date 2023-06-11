package com.gloddy.server.core.utils.event;

import com.gloddy.server.group_member.application.GroupMemberSaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class GroupEventHandler {
    private final GroupMemberSaveService groupMemberSaveService;

    @Transactional
    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void groupParticipateListener(GroupParticipateEvent event) {
        groupMemberSaveService.saveUserGroup(event.getUserId(), event.getGroupId());
    }
}
