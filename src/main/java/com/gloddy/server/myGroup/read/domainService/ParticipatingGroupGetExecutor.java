package com.gloddy.server.myGroup.read.domainService;

import com.gloddy.server.group.domain.Group;
import com.gloddy.server.group_member.domain.GroupMember;
import com.gloddy.server.group_member.domain.handler.GroupMemberQueryHandler;
import com.gloddy.server.myGroup.read.dto.MyGroupResponse;
import com.gloddy.server.myGroup.read.util.MyGroupDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.Comparator.*;
import static java.util.stream.Collectors.*;

@Component
@RequiredArgsConstructor
public class ParticipatingGroupGetExecutor {

    private final GroupMemberQueryHandler groupMemberQueryHandler;

    public MyGroupResponse.Participating getParticipatingGroups(Long userId) {
        List<GroupMember> groupMembers = groupMemberQueryHandler.findAllByUserId(userId);

        return groupMembers.stream()
                .filter(groupMember -> isNotCaptain(groupMember.getGroup(), userId))
                .filter(groupMember -> isNotEndGroup(groupMember.getGroup()))
                .sorted(comparing(this::getGroupMemberCreatedAt).reversed())
                .map(groupMember -> MyGroupDtoMapper.mapToParticipatingOne(isNewGroupMember(groupMember), groupMember.getGroup()))
                .collect(collectingAndThen(toList(), MyGroupResponse.Participating::new));
    }

    private boolean isNotCaptain(Group group, Long userId) {
        return !group.getCaptain().getId().equals(userId);
    }

    private boolean isNotEndGroup(Group group) {
        return group.getDateTime().getStartDateTime().isAfter(LocalDateTime.now());
    }

    private LocalDateTime getGroupMemberCreatedAt(GroupMember groupMember) {
        return groupMember.getCreatedAt();
    }

    private boolean isNewGroupMember(GroupMember groupMember) {
        return groupMember.getCreatedAt().isAfter(LocalDateTime.now().minusHours(1));
    }
}
