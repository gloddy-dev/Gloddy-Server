package com.gloddy.server.group.repository.custom;

import com.gloddy.server.auth.entity.User;
import com.gloddy.server.group.entity.Group;
import com.gloddy.server.group.entity.UserGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserGroupJpaRepositoryCustom {

    List<Group> findExpectedGroupsByUser(User user);

    Page<UserGroup> findParticipatedGroupsByUser(User user, Pageable pageable);
}
