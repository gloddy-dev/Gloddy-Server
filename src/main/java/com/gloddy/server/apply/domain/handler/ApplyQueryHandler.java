package com.gloddy.server.apply.domain.handler;

import com.gloddy.server.apply.domain.Apply;
import com.gloddy.server.apply.domain.vo.Status;
import com.gloddy.server.user.domain.User;
import com.gloddy.server.group.domain.Group;

import java.util.List;


public interface ApplyQueryHandler {

    List<Apply> findAllApprovedAppliesFetchUserBy(Group group);

    Long countApprovedAppliesBy(Long groupId);

    Apply findApplyToUpdateStatus(Long id);

    List<Apply> findAllByGroupIdAndStatus(Long groupId, Status status);

    Long countAppliesByGroupIdAndStatus(Long groupId, Status status);

    List<Apply> findAllByUserIdAndStatus(Long userId, Status status);

    Apply findById(Long applyId);

    Boolean existsByUserAndGroupAndStatus(User user, Group group, Status status);

    Boolean existsByUserAndStatus(User user, Status status);
    Boolean existsWaitApplyByGroupId(Long groupId);
}
