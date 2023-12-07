package com.gloddy.server.group_member.domain.handler.impl;

import com.gloddy.server.group_member.exception.NotFoundGroupMemberException;
import com.gloddy.server.user.domain.User;
import com.gloddy.server.group_member.domain.GroupMember;
import com.gloddy.server.group_member.domain.handler.GroupMemberQueryHandler;
import com.gloddy.server.group_member.infra.repository.GroupMemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GroupMemberQueryHandlerImpl implements GroupMemberQueryHandler {

    private final GroupMemberJpaRepository groupMemberJpaRepository;

    @Override
    public GroupMember findByUserIdAndGroupId(Long userId, Long groupId) {
        return groupMemberJpaRepository.findByUserIdAndGroupId(userId, groupId)
                .orElseThrow(() -> new RuntimeException("Not Found UserGroup"));
    }

    @Override
    public List<GroupMember> findAllByGroupId(Long groupId) {
        return groupMemberJpaRepository.findAllByGroupIdFetchUserAndGroup(groupId);
    }

    @Override
    public List<GroupMember> findAllByUserIdInAndGroupId(List<Long> userIds, Long groupId) {
        return groupMemberJpaRepository.findByUserIdInAndGroupId(userIds, groupId);
    }

    @Override
    public List<GroupMember> findAllByUserId(Long userId) {
        return groupMemberJpaRepository.findByUserIdFetchGroupAndUser(userId);
    }

    @Override
    public Long countParticipatedGroup(Long userId) {
        return groupMemberJpaRepository.countByUserIdAndIsAbsenceAndEndDateTimeBeforeJoinGroup(
                userId,
                false,
                LocalDateTime.now()
        );
    }

    @Override
    public boolean existsByUserAndGroupEndTimeBefore(User user) {
        return groupMemberJpaRepository.existsByUserAndGroupEndTimeBefore(user);
    }

    @Override
    public GroupMember findById(Long groupMemberId) {
        return groupMemberJpaRepository.findByIdFetchGroupAndUser(groupMemberId)
                .orElseThrow(NotFoundGroupMemberException::new);
    }

}
