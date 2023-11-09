package com.gloddy.server.messaging.adapter.group.handler;

import com.gloddy.server.group_member.event.GroupMemberLeaveEvent;
import com.gloddy.server.messaging.MessagePublisher;
import com.gloddy.server.messaging.adapter.group.event.GroupMemberAdapterEvent;
import com.gloddy.server.messaging.adapter.group.event.GroupMemberEventType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class GroupMemberAdapterEventHandler {
    private final MessagePublisher messagePublisher;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(GroupMemberLeaveEvent event) {
        GroupMemberAdapterEvent adapterEvent = new GroupMemberAdapterEvent(
                event.getUserId(),
                event.getGroupId(),
                GroupMemberEventType.GROUP_MEMBER_LEAVE);

        messagePublisher.publishGroupMemberEvent(adapterEvent);
    }
}