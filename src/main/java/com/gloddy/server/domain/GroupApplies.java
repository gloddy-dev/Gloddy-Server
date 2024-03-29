package com.gloddy.server.domain;

import com.gloddy.server.apply.domain.Apply;
import com.gloddy.server.user.domain.User;
import com.gloddy.server.group.domain.Group;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GroupApplies {

    private Group group;
    private List<Apply> applies;

    public List<User> getAllUsers() {
        return applies.stream()
                .map(Apply::getUser)
                .collect(Collectors.toList());
    }

    public User getCaptain() {
        return group.getCaptain();
    }
}
