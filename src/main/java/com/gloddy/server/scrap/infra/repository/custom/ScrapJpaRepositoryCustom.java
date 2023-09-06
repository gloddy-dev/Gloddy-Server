package com.gloddy.server.scrap.infra.repository.custom;

import com.gloddy.server.group.domain.Group;

import java.time.LocalDateTime;
import java.util.List;

public interface ScrapJpaRepositoryCustom {
    List<Group> findGroupsByUserIdAndStartTimeAfterOrderByGroupCreatedAt(Long userId, LocalDateTime time);
}
