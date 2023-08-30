package com.gloddy.server.myGroup.read.domainService;

import com.gloddy.server.group.domain.Group;
import com.gloddy.server.group_member.domain.GroupMember;
import com.gloddy.server.group_member.domain.handler.GroupMemberQueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class NotEstimatedGroupGetExecutor {

    private final GroupMemberQueryHandler groupMemberQueryHandler;

    public List<Group> getNotEstimatedGroups(Long userId) {
        List<GroupMember> groupMembers = groupMemberQueryHandler.findAllByUserId(userId);

        return groupMembers.stream()
                .filter(this::isNotEndEstimate)
                .map(GroupMember::getGroup)
                .filter(this::isEndGroup)
                .toList();
    }

    private boolean isNotEndEstimate(GroupMember groupMember) {
        return !groupMember.isEndEstimate();
    }

    private boolean isEndGroup(Group group) {
        return group.isEndGroup();
    }
}
