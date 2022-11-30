package com.gloddy.server.estimate.service;

import com.gloddy.server.apply.entity.Apply;
import com.gloddy.server.apply.entity.vo.Status;
import com.gloddy.server.apply.handler.ApplyQueryHandler;
import com.gloddy.server.apply.repository.ApplyJpaRepository;
import com.gloddy.server.auth.entity.User;
import com.gloddy.server.auth.handler.UserHandler;
import com.gloddy.server.domain.GroupApplies;
import com.gloddy.server.domain.GroupUsers;
import com.gloddy.server.estimate.dto.EstimateResponse;
import com.gloddy.server.group.entity.Group;
import com.gloddy.server.group.handler.GroupQueryHandler;
import com.gloddy.server.group.repository.GroupJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetGroupMemberForEstimateService {

    private final ApplyQueryHandler applyQueryHandler;
    private final UserHandler userQueryHandler;
    private final GroupQueryHandler groupQueryHandler;

    @Transactional(readOnly = true)
    public List<User> getGroupMembers(Long userId, Long groupId) {

        Group findGroup = groupQueryHandler.findById(groupId);

        User me = userQueryHandler.findById(userId);

        List<Apply> approvedAllAppliesByGroup = applyQueryHandler.findAllApprovedAppliesFetchUserBy(findGroup);

        GroupApplies groupApplies = new GroupApplies(findGroup, approvedAllAppliesByGroup);
        GroupUsers groupUsers = GroupUsers.from(groupApplies);

        return groupUsers.getUsersWithOut(me);
    }
}
