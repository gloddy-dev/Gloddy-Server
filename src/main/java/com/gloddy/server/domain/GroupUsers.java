package com.gloddy.server.domain;

import com.gloddy.server.auth.domain.User;
import com.gloddy.server.core.dto.UserInfoDto;
import com.gloddy.server.group.domain.Group;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class GroupUsers {

    private Group group;
    private List<User> users;

    private GroupUsers(Group group, List<User> users) {
        this.group = group;
        this.users = users;
    }

    public static GroupUsers from(GroupApplies groupApplies) {
        List<User> allUsers = groupApplies.getAllUsers();
        allUsers.add(groupApplies.getCaptain());

        return new GroupUsers(
                groupApplies.getGroup(),
                allUsers
        );
    }

    public List<User> getUsersWithOut(User user) {
        List<User> userList;
        userList = this.users;
        userList.remove(user);
        return userList;
    }

    public int getUserCount() {
        return users.size();
    }

    public List<UserInfoDto> getUserInfoDtos() {
        return users.stream()
            .map(UserInfoDto::from)
            .collect(Collectors.toUnmodifiableList());
    }
}
