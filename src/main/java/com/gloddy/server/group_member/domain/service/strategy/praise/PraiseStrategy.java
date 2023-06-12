package com.gloddy.server.group_member.domain.service.strategy.praise;

import com.gloddy.server.group_member.domain.GroupMember;
import com.gloddy.server.group_member.event.producer.GroupMemberEventProducer;

public interface PraiseStrategy {

    void praise(GroupMember groupMember, GroupMemberEventProducer groupMemberEventProducer);
}
