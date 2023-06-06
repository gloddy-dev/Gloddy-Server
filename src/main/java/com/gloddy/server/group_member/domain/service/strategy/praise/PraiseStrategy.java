package com.gloddy.server.praise.domain.service;

import com.gloddy.server.group_member.domain.GroupMember;
import org.springframework.context.ApplicationEventPublisher;

public interface PraiseStrategy {

    void praise(GroupMember groupMember, ApplicationEventPublisher eventPublisher);
}
