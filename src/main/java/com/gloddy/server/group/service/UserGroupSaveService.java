package com.gloddy.server.group.service;

import com.gloddy.server.auth.entity.User;
import com.gloddy.server.group.entity.Group;
import com.gloddy.server.group.entity.UserGroup;
import com.gloddy.server.group.handler.GroupQueryHandler;
import com.gloddy.server.group.repository.UserGroupJpaRepository;
import com.gloddy.server.user.service.UserFindService;
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
        return userGroupJpaRepository.save(userGroup);
    }
}
