package com.gloddy.server.myGroup.read.domainService;

import com.gloddy.server.group.domain.Group;
import com.gloddy.server.group.domain.handler.GroupQueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Component
@RequiredArgsConstructor
public class HostingGroupGetExecutor {

    private final GroupQueryHandler groupQueryHandler;

    public List<Group> getHostingGroups(Long userId) {
        List<Group> groups = groupQueryHandler.findAllByCaptainId(userId);

        return groups.stream()
                .filter(this::isNotEndGroup)
                .sorted(Comparator.comparing(this::getGroupCreatedAt).reversed())
                .toList();
    }

    private boolean isNotEndGroup(Group group) {
        return group.getDateTime().getStartDateTime().isAfter(LocalDateTime.now());
    }

    private LocalDateTime getGroupCreatedAt(Group group) {
        return group.getCreatedAt();
    }
}
