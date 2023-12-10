package com.gloddy.server.group.application;

import com.gloddy.server.group.domain.Group;
import com.gloddy.server.group.domain.dto.GroupMemberPayload;
import com.gloddy.server.group.domain.dto.GroupPayload;
import com.gloddy.server.group.domain.handler.GroupQueryHandler;
import com.gloddy.server.group.domain.vo.in.GroupMemberPayloadEventType;
import com.gloddy.server.group.domain.vo.in.GroupPayloadEventType;
import com.gloddy.server.user.domain.User;
import com.gloddy.server.user.domain.handler.UserQueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupPayloadService {
    private final GroupQueryHandler groupQueryHandler;
    private final UserQueryHandler userQueryHandler;

    public GroupPayload getGroupPayload(Long groupId, GroupPayloadEventType eventType) {
        Group group = groupQueryHandler.findById(groupId);

        return GroupPayload.toDto(group);
    }

    public GroupMemberPayload getGroupMemberPayload(Long groupId, Long userId, GroupMemberPayloadEventType eventType) {
        Group group = groupQueryHandler.findById(groupId);
        User groupMemberUser = userQueryHandler.findById(userId);

        return GroupMemberPayload.toDto(group, groupMemberUser);
    }
}
