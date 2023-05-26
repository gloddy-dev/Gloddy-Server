package com.gloddy.server.core.utils.event;

import com.gloddy.server.user_group.application.UserGroupSaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class ApplyEventHandler {

    private final UserGroupSaveService userGroupSaveService;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void applyApproveEventHandler(ApplyApproveEvent applyApproveEvent) {
        userGroupSaveService.saveUserGroup(applyApproveEvent.getUserId(), applyApproveEvent.getGroupId());
    }
}
