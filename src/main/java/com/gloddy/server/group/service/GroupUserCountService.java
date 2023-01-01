package com.gloddy.server.group.service;

import com.gloddy.server.apply.handler.ApplyQueryHandler;
import com.gloddy.server.group.entity.Group;
import com.gloddy.server.group.handler.GroupQueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GroupUserCountService {
    private final ApplyQueryHandler applyQueryHandler;

    @Transactional
    public Long countUserInGroup(Long groupId) {
        return applyQueryHandler.countApprovedAppliesBy(groupId);
    }
}
