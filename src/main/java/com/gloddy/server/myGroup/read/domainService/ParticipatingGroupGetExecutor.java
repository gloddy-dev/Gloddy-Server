package com.gloddy.server.myGroup.read.domainService;

import com.gloddy.server.group.domain.Group;
import com.gloddy.server.group_member.domain.GroupMember;
import com.gloddy.server.group_member.domain.handler.GroupMemberQueryHandler;
import com.gloddy.server.myGroup.read.dto.MyGroupResponse;
import com.gloddy.server.myGroup.read.util.MyGroupDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Comparator;
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
                .sorted(groupMemberCreatedAtDesc())
                .map(groupMember -> MyGroupDtoMapper.mapToParticipatingOne(isNewGroupMember(groupMember), groupMember.getGroup()))
                .collect(collectingAndThen(toList(), MyGroupResponse.Participating::new));
    }

    private boolean isNotCaptain(Group group, Long userId) {
        return !group.getCaptain().getId().equals(userId);
    }

    private boolean isNotEndGroup(Group group) {
        return !group.isEndGroup();
    }

    private Comparator<? super GroupMember> groupMemberCreatedAtDesc() {
        return comparing(GroupMember::getCreatedAt).reversed();
    }

    private boolean isNewGroupMember(GroupMember groupMember) {
        return groupMember.isNewGroupMember();
    }
}
