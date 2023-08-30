package com.gloddy.server.myGroup.read.domainService;

import com.gloddy.server.apply.domain.handler.ApplyQueryHandler;
import com.gloddy.server.apply.domain.vo.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HostingGroupExistNewApplyChecker {

    private final ApplyQueryHandler applyQueryHandler;

    public boolean isExistNewApply(Long groupId) {
        Long countNewApplies = applyQueryHandler.countAppliesByGroupIdAndStatus(groupId, Status.WAIT);
        return countNewApplies > 0;
    }
}
