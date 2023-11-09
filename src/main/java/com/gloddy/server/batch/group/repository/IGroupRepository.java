package com.gloddy.server.batch.group.repository;

import com.gloddy.server.group.domain.Group;

import java.time.LocalDateTime;
import java.util.List;

public interface IGroupRepository {
    List<Group> findApproachingGroups(LocalDateTime currentDateTime);

    List<Group> findEndGroups(LocalDateTime currentDateTime);
}
