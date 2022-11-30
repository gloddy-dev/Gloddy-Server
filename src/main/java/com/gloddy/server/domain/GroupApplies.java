package com.gloddy.server.domain;

import com.gloddy.server.apply.entity.Apply;
import com.gloddy.server.auth.entity.User;
import com.gloddy.server.group.entity.Group;
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
                .collect(Collectors.toUnmodifiableList());
    }
}
