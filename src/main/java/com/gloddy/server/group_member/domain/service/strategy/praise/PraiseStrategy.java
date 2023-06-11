package com.gloddy.server.group_member.domain.service.strategy.praise;

import com.gloddy.server.group_member.domain.GroupMember;
import org.springframework.context.ApplicationEventPublisher;

public interface PraiseStrategy {

    void praise(GroupMember groupMember, ApplicationEventPublisher eventPublisher);
}
