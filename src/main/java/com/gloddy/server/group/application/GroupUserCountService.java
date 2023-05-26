package com.gloddy.server.group.application;

import com.gloddy.server.apply.domain.handler.ApplyQueryHandler;
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
