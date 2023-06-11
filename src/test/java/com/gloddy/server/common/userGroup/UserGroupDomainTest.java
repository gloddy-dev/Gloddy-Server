package com.gloddy.server.common.userGroup;

import com.gloddy.server.UnitTest;
import com.gloddy.server.auth.domain.User;
import com.gloddy.server.praise.domain.Praise;
import com.gloddy.server.group.domain.Group;
import com.gloddy.server.group_member.domain.GroupMember;

public abstract class UserGroupDomainTest extends UnitTest {

    protected Praise getInitPraise(User user) {
        Praise praise = Praise.empty();
        praise.init(user);
        return praise;
    }

    protected GroupMember getInitUserGroup(User user, Group group) {
        GroupMember groupMember = GroupMember.empty();
        groupMember.init(user, group);
        return groupMember;
    }
}
