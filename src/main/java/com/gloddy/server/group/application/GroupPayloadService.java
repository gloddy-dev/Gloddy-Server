package com.gloddy.server.group.application;

import com.gloddy.server.group.domain.Group;
import com.gloddy.server.group.domain.dto.GroupMemberPayload;
import com.gloddy.server.group.domain.dto.GroupPayload;
import com.gloddy.server.group.domain.handler.GroupQueryHandler;
import com.gloddy.server.group.domain.vo.in.GroupMemberPayloadEventType;
import com.gloddy.server.group.domain.vo.in.GroupPayloadEventType;
import com.gloddy.server.group_member.domain.GroupMember;
import com.gloddy.server.group_member.domain.handler.GroupMemberQueryHandler;
import com.gloddy.server.messaging.adapter.group.event.GroupEventType;
import com.gloddy.server.messaging.adapter.group.event.GroupMemberEventType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupPayloadService {
    private final GroupQueryHandler groupQueryHandler;
    private final GroupMemberQueryHandler groupMemberQueryHandler;

    public GroupPayload getGroupPayload(Long groupId, GroupPayloadEventType eventType) {
        Group group = groupQueryHandler.findById(groupId);

        return GroupPayload.toDto(group);
    }

    public GroupMemberPayload getGroupMemberPayload(Long groupMemberId, GroupMemberPayloadEventType eventType) {
        GroupMember groupMember = groupMemberQueryHandler.findById(groupMemberId);

        return GroupMemberPayload.toDto(groupMember);
    }
}
