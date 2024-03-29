package com.gloddy.server.group_member.domain.handler;

import com.gloddy.server.user.domain.User;
import com.gloddy.server.group_member.domain.GroupMember;

import java.util.List;

public interface GroupMemberQueryHandler {
    GroupMember findByUserIdAndGroupId(Long userId, Long groupId);

    List<GroupMember> findAllByGroupId(Long groupId);

    List<GroupMember> findAllByUserIdInAndGroupId(List<Long> userIds, Long groupId);

    List<GroupMember> findAllByUserId(Long userId);

    Long countParticipatedGroup(Long userId);

    boolean existsByUserAndGroupEndTimeBefore(User user);

    GroupMember findById(Long groupMemberId);
}
