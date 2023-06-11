package com.gloddy.server.group_member.domain.service.strategy.praise;

import com.gloddy.server.core.event.group_member.GroupMemberReceivePraiseEvent;
import com.gloddy.server.group_member.domain.GroupMember;
import com.gloddy.server.praise.domain.vo.PraiseValue;
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
            eventPublisher.publishEvent(new GroupMemberReceivePraiseEvent(groupMember.getUser().getId(), PraiseValue.ABSENCE));
        }
    }
}
