package com.gloddy.server.group_member.domain.service.strategy.praise;

import com.gloddy.server.core.event.group_member.GroupMemberReceivePraiseEvent;
import com.gloddy.server.group_member.domain.GroupMember;
import com.gloddy.server.praise.domain.vo.PraiseValue;
import org.springframework.context.ApplicationEventPublisher;

public class HumorPraiseStrategy implements PraiseStrategy {
    @Override
    public void praise(GroupMember groupMember, ApplicationEventPublisher eventPublisher) {
        eventPublisher.publishEvent(new GroupMemberReceivePraiseEvent(groupMember.getUser().getId(), PraiseValue.HUMOR));
    }
}
