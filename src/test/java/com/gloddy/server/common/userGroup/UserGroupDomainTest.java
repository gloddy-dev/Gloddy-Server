package com.gloddy.server.common.userGroup;

import com.gloddy.server.UnitTest;
import com.gloddy.server.auth.entity.User;
import com.gloddy.server.estimate.entity.Praise;
import com.gloddy.server.group.entity.Group;
import com.gloddy.server.group.entity.UserGroup;

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
