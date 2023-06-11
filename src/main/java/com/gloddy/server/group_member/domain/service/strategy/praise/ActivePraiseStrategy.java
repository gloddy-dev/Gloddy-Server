package com.gloddy.server.group_member.domain.service.strategy.praise;

import com.gloddy.server.group_member.event.GroupMemberReceivePraiseEvent;
import com.gloddy.server.group_member.domain.GroupMember;
import com.gloddy.server.praise.domain.vo.PraiseValue;
import org.springframework.context.ApplicationEventPublisher;

public class ActivePraiseStrategy implements PraiseStrategy {
    @Override
    public void praise(GroupMember groupMember, ApplicationEventPublisher eventPublisher) {
        eventPublisher.publishEvent(new GroupMemberReceivePraiseEvent(groupMember.getUser().getId(), PraiseValue.ACTIVE));
    }
}
