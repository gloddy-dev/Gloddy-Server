package com.gloddy.server.group.repository.custom;

import com.gloddy.server.auth.entity.User;
import com.gloddy.server.group.entity.Group;

import java.util.List;

public interface UserGroupJpaRepositoryCustom {

    List<Group> findExpectedGroupsByUser(User user);
}
