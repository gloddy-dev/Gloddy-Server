package com.gloddy.server.group.domain.handler.impl;

import com.gloddy.server.group.domain.Group;
import com.gloddy.server.group.domain.handler.GroupCommandHandler;
import com.gloddy.server.group.infra.repository.GroupJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class GroupCommandHandlerImpl implements GroupCommandHandler {

    private final GroupJpaRepository groupJpaRepository;

    @Override
    public Group save(Group group) {
        return groupJpaRepository.save(group);
    }
}
