package com.gloddy.server.group.service;

import com.gloddy.server.group.entity.UserGroup;
import com.gloddy.server.group.repository.UserGroupJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserGroupUpdateService {
    private final UserGroupJpaRepository userGroupJpaRepository;

    @Transactional
    public void completePraise(Long userId, Long groupId) {
        UserGroup findUserGroup = userGroupJpaRepository.findByUserIdAndGroupId(userId, groupId)
                .orElseThrow(() -> new RuntimeException("not found UserGroup"));
        findUserGroup.completePraise();
    }
}
