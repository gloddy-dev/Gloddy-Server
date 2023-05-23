package com.gloddy.server.group.application;

import com.gloddy.server.auth.domain.User;
import com.gloddy.server.group.domain.Group;
import com.gloddy.server.user_group.domain.UserGroup;
import com.gloddy.server.group.domain.handler.GroupQueryHandler;
import com.gloddy.server.user_group.infra.repository.UserGroupJpaRepository;
import com.gloddy.server.user.application.UserFindService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserGroupSaveService {

    private final GroupQueryHandler groupQueryHandler;
    private final UserFindService userFindService;
    private final UserGroupJpaRepository userGroupJpaRepository;

    public UserGroup saveUserGroup(Long userId, Long groupId) {
        Group findGroup = groupQueryHandler.findById(groupId);
        User findUser = userFindService.findById(userId);
        UserGroup userGroup = UserGroup.empty();
        userGroup.init(findUser, findGroup);
        findGroup.addUserGroup(userGroup);
        return userGroupJpaRepository.save(userGroup);
    }
}
