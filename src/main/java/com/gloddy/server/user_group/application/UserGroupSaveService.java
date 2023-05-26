package com.gloddy.server.user_group.application;

import com.gloddy.server.auth.domain.User;
import com.gloddy.server.group.domain.Group;
import com.gloddy.server.group.domain.vo.UserGroupVO;
import com.gloddy.server.user.domain.handler.UserQueryHandler;
import com.gloddy.server.user_group.domain.UserGroup;
import com.gloddy.server.group.domain.handler.GroupQueryHandler;
import com.gloddy.server.user_group.domain.handler.UserGroupCommandHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserGroupSaveService {

    private final GroupQueryHandler groupQueryHandler;
    private final UserQueryHandler userQueryHandler;
    private final UserGroupCommandHandler userGroupCommandHandler;

    public UserGroup saveUserGroup(Long userId, Long groupId) {

        Group findGroup = groupQueryHandler.findById(groupId);
        User findUser = userQueryHandler.findById(userId);

        UserGroup userGroup = UserGroup.empty();
        userGroup.init(findUser, findGroup);

        UserGroupVO userGroupVO = userGroup.createUserGroupVO();
        findGroup.addUserGroupVOs(userGroupVO);

        return userGroupCommandHandler.save(userGroup);
    }
}
