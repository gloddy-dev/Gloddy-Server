package com.gloddy.server.group_member.application;

import com.gloddy.server.group_member.domain.GroupMember;
import com.gloddy.server.group_member.infra.repository.GroupMemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupMemberFindService {
    private final GroupMemberJpaRepository userGroupJpaRepository;

    public GroupMember findByUserIdAndGroupId(Long userId, Long groupId) {
        return userGroupJpaRepository.findByUserIdAndGroupId(userId, groupId)
                .orElseThrow(() -> new RuntimeException("not found usergroup"));
    }

    public List<GroupMember> getUserGroupsToPraise(List<Long> userIds, Long groupId) {
        return userGroupJpaRepository.findUserGroupsToPraiseByUserIdInAndGroupId(userIds, groupId);
    }
}
