package com.gloddy.server.group.service;

import com.gloddy.server.group.entity.UserGroup;
import com.gloddy.server.group.repository.UserGroupJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserGroupFindService {
    private final UserGroupJpaRepository userGroupJpaRepository;

    public UserGroup findByUserIdAndGroupId(Long userId, Long groupId) {
        return userGroupJpaRepository.findByUserIdAndGroupId(userId, groupId)
                .orElseThrow(() -> new RuntimeException("not found usergroup"));
    }

    public List<UserGroup> getUserGroupsToPraise(List<Long> userIds, Long groupId) {
        return userGroupJpaRepository.findUserGroupsToPraiseByUserIdInAndGroupId(userIds, groupId);
    }
}
