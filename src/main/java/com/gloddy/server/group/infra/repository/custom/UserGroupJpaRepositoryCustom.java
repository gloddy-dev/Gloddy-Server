package com.gloddy.server.group.infra.repository.custom;

import com.gloddy.server.auth.domain.User;
import com.gloddy.server.group.domain.Group;
import com.gloddy.server.group.domain.UserGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserGroupJpaRepositoryCustom {

    List<Group> findExpectedGroupsByUser(User user);

    Page<UserGroup> findParticipatedGroupsByUser(User user, Pageable pageable);

    List<UserGroup> findUserGroupsToPraiseByUserIdInAndGroupId(List<Long> userIds, Long groupId);
}
