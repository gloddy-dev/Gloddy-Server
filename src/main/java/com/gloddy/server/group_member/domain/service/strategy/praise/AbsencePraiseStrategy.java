package com.gloddy.server.group_member.domain.service.strategy.praise;

import com.gloddy.server.group_member.event.GroupMemberReceivePraiseEvent;
import com.gloddy.server.group_member.domain.GroupMember;
import com.gloddy.server.group_member.event.producer.GroupMemberEventProducer;
import com.gloddy.server.user.domain.vo.PraiseValue;

public class AbsencePraiseStrategy implements PraiseStrategy {
    @Override
    public void praise(GroupMember groupMember, GroupMemberEventProducer groupMemberEventProducer) {
        groupMember.plusAbsenceVoteCount();
        if (groupMember.isAbsence()) {
            return;
        }

        if (groupMember.isAbsenceVoteCountOver()) {
            groupMember.absence();
            groupMemberEventProducer.produceEvent(new GroupMemberReceivePraiseEvent(groupMember.getUser().getId(), PraiseValue.ABSENCE));
        }
    }
}
