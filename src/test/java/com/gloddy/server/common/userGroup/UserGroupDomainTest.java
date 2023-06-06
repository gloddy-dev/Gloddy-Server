package com.gloddy.server.common.userGroup;

import com.gloddy.server.UnitTest;
import com.gloddy.server.auth.domain.User;
import com.gloddy.server.praise.domain.Praise;
import com.gloddy.server.group.domain.Group;
import com.gloddy.server.user_group.domain.UserGroup;

public abstract class UserGroupDomainTest extends UnitTest {

    protected Praise getInitPraise(User user) {
        Praise praise = Praise.empty();
        praise.init(user);
        return praise;
    }

    protected UserGroup getInitUserGroup(User user, Group group) {
        UserGroup userGroup = UserGroup.empty();
        userGroup.init(user, group);
        return userGroup;
    }
}
