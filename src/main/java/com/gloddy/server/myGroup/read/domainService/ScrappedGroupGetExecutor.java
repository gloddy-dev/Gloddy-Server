package com.gloddy.server.myGroup.read.domainService;

import com.gloddy.server.group.domain.Group;
import com.gloddy.server.group.domain.handler.GroupQueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ScrappedGroupGetExecutor {
    private final GroupQueryHandler groupQueryHandler;

    public List<Group> getScrappedGroups(Long userId) {
        return groupQueryHandler.findAllScrappedGroups(userId);
    }
}
