package com.gloddy.server.group_member.application;

import com.gloddy.server.group_member.domain.GroupMember;
import com.gloddy.server.group_member.infra.repository.GroupMemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GroupMemberUpdateService {
    private final GroupMemberJpaRepository userGroupJpaRepository;

    @Transactional
    public void completePraise(Long userId, Long groupId) {
        GroupMember findGroupMember = userGroupJpaRepository.findByUserIdAndGroupId(userId, groupId)
                .orElseThrow(() -> new RuntimeException("not found UserGroup"));
        findGroupMember.completePraise();
    }
}
