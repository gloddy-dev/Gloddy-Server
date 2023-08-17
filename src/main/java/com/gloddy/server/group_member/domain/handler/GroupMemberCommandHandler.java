package com.gloddy.server.group_member.domain.handler;

import com.gloddy.server.group_member.domain.GroupMember;

public interface GroupMemberCommandHandler {
    GroupMember save(GroupMember groupMember);
    void delete(GroupMember groupMember);
}
