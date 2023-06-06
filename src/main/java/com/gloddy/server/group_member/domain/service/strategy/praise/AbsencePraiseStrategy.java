package com.gloddy.server.praise.domain.service;

import com.gloddy.server.group_member.domain.GroupMember;
import org.springframework.context.ApplicationEventPublisher;

public class AbsencePraiseStrategy implements PraiseStrategy {
    @Override
    public void praise(GroupMember groupMember, ApplicationEventPublisher eventPublisher) {
        groupMember.plusAbsenceVoteCount();
        if (groupMember.isAbsence()) {
            return;
        }

        if (groupMember.isAbsenceVoteCountOver()) {
            groupMember.absence();
            groupMember.getUser().getPraise().plusAbsenceCount();
        }
    }
}
