package com.gloddy.server.myGroup.read.domainService;

import com.gloddy.server.group.domain.Group;
import com.gloddy.server.group.domain.handler.GroupQueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

import static java.util.Comparator.*;

@Component
@RequiredArgsConstructor
public class HostingGroupGetExecutor {

    private final GroupQueryHandler groupQueryHandler;

    public List<Group> getHostingGroups(Long userId) {
        List<Group> groups = groupQueryHandler.findAllByCaptainId(userId);

        return groups.stream()
                .filter(this::isNotEndGroup)
                .sorted(groupCreatedAtDesc())
                .toList();
    }

    private boolean isNotEndGroup(Group group) {
        return !group.isEndGroup();
    }

    private Comparator<? super Group> groupCreatedAtDesc() {
        return comparing(Group::getCreatedAt).reversed();
    }
}
