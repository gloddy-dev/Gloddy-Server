package com.gloddy.server.group.domain.handler.impl;

import com.gloddy.server.core.error.handler.exception.GroupBusinessException;
import com.gloddy.server.group.domain.Group;
import com.gloddy.server.group.domain.handler.GroupQueryHandler;
import com.gloddy.server.group.infra.repository.GroupJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.gloddy.server.core.error.handler.errorCode.ErrorCode.*;

@Repository
@RequiredArgsConstructor
public class GroupQueryHandlerImpl implements GroupQueryHandler {

    private final GroupJpaRepository groupJpaRepository;


    @Override
    public Group findById(Long id) {
        return groupJpaRepository.findById(id)
                .orElseThrow(() -> new GroupBusinessException(GROUP_NOT_FOUND));
    }

    @Override
    public Page<Group> findGroupPage(Pageable pageable) {
        return groupJpaRepository.findAllByOrderByIdDesc(pageable);
    }

    @Override
    public List<Group> findAllByCaptainId(Long captainId) {
        return groupJpaRepository.findAllByCaptainId(captainId);
    }
}
