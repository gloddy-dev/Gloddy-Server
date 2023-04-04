package com.gloddy.server.common.group;

import com.gloddy.server.auth.entity.User;
import com.gloddy.server.common.BaseApiTest;
import com.gloddy.server.group.entity.Group;
import com.gloddy.server.group.entity.UserGroup;
import com.gloddy.server.group.repository.GroupJpaRepository;
import com.gloddy.server.group.repository.UserGroupJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

abstract public class GroupApiTest extends BaseApiTest {

    @Autowired
    protected GroupJpaRepository groupJpaRepository;

    @Autowired
    protected UserGroupJpaRepository userGroupJpaRepository;

    protected Group createGroup() {
        Group group = Group.builder().build();
        return groupJpaRepository.save(group);
    }

    protected UserGroup createUserGroup(User user, Group group) {
        UserGroup userGroup = UserGroup.empty();
        userGroup.init(user, group);
        group.addUserGroup(userGroup);
        return userGroupJpaRepository.save(userGroup);
    }

    protected Group createExpectedGroup() {
        Group expectedGroup = Group.builder().startTime(LocalDateTime.now().plusDays(1L)).build();
        return groupJpaRepository.save(expectedGroup);
    }

    protected Group createParticipatedGroup() {
        Group participatedGroup = Group.builder().startTime(LocalDateTime.now().minusDays(1L)).build();
        return groupJpaRepository.save(participatedGroup);
    }
}
