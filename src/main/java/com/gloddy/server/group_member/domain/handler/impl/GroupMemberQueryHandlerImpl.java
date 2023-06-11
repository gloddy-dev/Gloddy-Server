package com.gloddy.server.group_member.domain.handler.impl;

import com.gloddy.server.group_member.domain.GroupMember;
import com.gloddy.server.group_member.domain.handler.GroupMemberQueryHandler;
import com.gloddy.server.group_member.infra.repository.GroupMemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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
        return groupMemberJpaRepository.findAllByGroupId(groupId);
    }

    @Override
    public List<GroupMember> findAllByUserIdInAndGroupId(List<Long> userIds, Long groupId) {
        return groupMemberJpaRepository.findByUserIdInAndGroupId(userIds, groupId);
    }
}
