package com.gloddy.server.core.utils.event;

import com.gloddy.server.group_member.application.GroupMemberSaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class ApplyEventHandler {

    private final GroupMemberSaveService groupMemberSaveService;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void applyApproveEventHandler(ApplyApproveEvent applyApproveEvent) {
        groupMemberSaveService.saveUserGroup(applyApproveEvent.getUserId(), applyApproveEvent.getGroupId());
    }
}
