package com.gloddy.server.praise.domain.service;

import com.gloddy.server.group_member.domain.GroupMember;
import org.springframework.context.ApplicationEventPublisher;

public class ActivePraiseStrategy implements PraiseStrategy {
    @Override
    public void praise(GroupMember groupMember, ApplicationEventPublisher eventPublisher) {
        groupMember.getUser().getPraise().plusActiveCount();
    }
}
