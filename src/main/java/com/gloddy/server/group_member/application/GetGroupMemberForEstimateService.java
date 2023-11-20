package com.gloddy.server.group_member.application;

import com.gloddy.server.apply.domain.Apply;
import com.gloddy.server.apply.domain.handler.ApplyQueryHandler;
import com.gloddy.server.user.domain.User;
import com.gloddy.server.group_member.domain.dto.EstimateResponse;
import com.gloddy.server.user.domain.handler.UserQueryHandler;
import com.gloddy.server.domain.GroupApplies;
import com.gloddy.server.domain.GroupUsers;
import com.gloddy.server.group.domain.Group;
import com.gloddy.server.group.domain.handler.GroupQueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class GetGroupMemberForEstimateService {

    private final ApplyQueryHandler applyQueryHandler;
    private final UserQueryHandler userQueryHandler;
    private final GroupQueryHandler groupQueryHandler;

    @Transactional(readOnly = true)
    public EstimateResponse.GetGroupMembers getGroupMembers(Long userId, Long groupId) {

        Group findGroup = groupQueryHandler.findById(groupId);

        User me = userQueryHandler.findById(userId);

        List<Apply> approvedAllAppliesByGroup = applyQueryHandler.findAllApprovedAppliesFetchUserBy(findGroup);

        GroupApplies groupApplies = new GroupApplies(findGroup, approvedAllAppliesByGroup);
        GroupUsers groupUsers = GroupUsers.from(groupApplies);

        return groupUsers.getUsersWithOut(me)
                .stream()
                .map(user -> EstimateResponse.GetGroupMembers.GetGroupMember.from(user, findGroup))
                .collect(collectingAndThen(toList(), EstimateResponse.GetGroupMembers::new));
    }
}
