package com.gloddy.server.group.handler;

import com.gloddy.server.core.error.handler.errorCode.ErrorCode;
import com.gloddy.server.core.error.handler.exception.GroupBusinessException;
import com.gloddy.server.group.entity.Group;
import com.gloddy.server.group.repository.GroupJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
