package com.gloddy.server.domain;

import com.gloddy.server.auth.entity.User;
import com.gloddy.server.group.entity.Group;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GroupUsers {

    private Group group;
    private List<User> users;

    private GroupUsers(Group group, List<User> users) {
        this.group = group;
        this.users = users;
    }

    public static GroupUsers from(GroupApplies groupApplies) {
        return new GroupUsers(
                groupApplies.getGroup(),
                groupApplies.getAllUsers()
        );
    }

    public List<User> getUsersWithOut(User user) {
        List<User> userList;
        userList = this.users;
        userList.remove(user);
        return userList;
    }
}
