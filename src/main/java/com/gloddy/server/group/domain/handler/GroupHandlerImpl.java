package com.gloddy.server.group.domain.handler;

import com.gloddy.server.core.error.handler.exception.GroupBusinessException;
import com.gloddy.server.group.domain.Group;
import com.gloddy.server.group.infra.repository.GroupJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.gloddy.server.core.error.handler.errorCode.ErrorCode.*;

@RequiredArgsConstructor
@Repository
public class GroupHandlerImpl implements GroupHandler{
    private final GroupJpaRepository groupJpaRepository;

    @Override
    public Group findById(Long id) {
        return groupJpaRepository.findById(id)
            .orElseThrow(() -> new GroupBusinessException(GROUP_NOT_FOUND));
    }
}
