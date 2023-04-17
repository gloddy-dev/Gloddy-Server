package com.gloddy.server.common.group;

import com.gloddy.server.auth.entity.User;
import com.gloddy.server.common.BaseApiTest;
import com.gloddy.server.estimate.repository.AbsenceInGroupJpaRepository;
import com.gloddy.server.group.dto.GroupRequest;
import com.gloddy.server.group.entity.Group;
import com.gloddy.server.group.entity.UserGroup;
import com.gloddy.server.group.repository.GroupJpaRepository;
import com.gloddy.server.group.repository.UserGroupJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;

abstract public class GroupApiTest extends BaseApiTest {

    @Autowired
    protected GroupJpaRepository groupJpaRepository;

    @Autowired
    protected UserGroupJpaRepository userGroupJpaRepository;

    @Autowired
    protected AbsenceInGroupJpaRepository absenceInGroupJpaRepository;

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

    protected UserGroup createCompletePraiseUserGroup(User user, Group group) {
        UserGroup userGroup = UserGroup.empty();
        userGroup.init(user, group);
        group.addUserGroup(userGroup);
        userGroup.completePraise();
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

    protected GroupRequest.Create createGroupCreateRequest() {
        LocalDate now = LocalDate.now();
        return new GroupRequest.Create(
                "test_FileUrl",
                "test_GroupTitle",
                "test_Content",
                LocalDate.of(now.getYear(), 4, 26),
                "12:00",
                "15:00",
                "숭실대학교",
                "150",
                "150",
                10
        );
    }
}
