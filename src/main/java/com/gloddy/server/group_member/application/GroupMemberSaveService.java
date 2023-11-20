package com.gloddy.server.group_member.application;

import com.gloddy.server.user.domain.User;
import com.gloddy.server.group.domain.Group;
import com.gloddy.server.group.domain.vo.GroupMemberVO;
import com.gloddy.server.group_member.domain.handler.GroupMemberCommandHandler;
import com.gloddy.server.user.domain.handler.UserQueryHandler;
import com.gloddy.server.group_member.domain.GroupMember;
import com.gloddy.server.group.domain.handler.GroupQueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupMemberSaveService {

    private final GroupQueryHandler groupQueryHandler;
    private final UserQueryHandler userQueryHandler;
    private final GroupMemberCommandHandler groupMemberCommandHandler;

    public GroupMember saveUserGroup(Long userId, Long groupId) {

        Group findGroup = groupQueryHandler.findById(groupId);
        User findUser = userQueryHandler.findById(userId);

        GroupMember groupMember = GroupMember.empty();
        groupMember.init(findUser, findGroup);

        GroupMemberVO groupMemberVO = groupMember.createUserGroupVO();
        findGroup.addUserGroupVOs(groupMemberVO);

        return groupMemberCommandHandler.save(groupMember);
    }
}
