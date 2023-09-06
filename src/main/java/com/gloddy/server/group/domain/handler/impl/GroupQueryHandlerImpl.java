package com.gloddy.server.group.domain.handler.impl;

import com.gloddy.server.core.error.handler.exception.GroupBusinessException;
import com.gloddy.server.group.domain.Group;
import com.gloddy.server.group.domain.handler.GroupQueryHandler;
import com.gloddy.server.group.infra.repository.GroupJpaRepository;
import com.gloddy.server.scrap.infra.repository.ScrapJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.gloddy.server.core.error.handler.errorCode.ErrorCode.*;

@Repository
@RequiredArgsConstructor
public class GroupQueryHandlerImpl implements GroupQueryHandler {

    private final GroupJpaRepository groupJpaRepository;
    private final ScrapJpaRepository scrapJpaRepository;


    @Override
    public Group findById(Long id) {
        return groupJpaRepository.findById(id)
                .orElseThrow(() -> new GroupBusinessException(GROUP_NOT_FOUND));
    }

    @Override
    public Page<Group> findGroupPreviewPage(Pageable pageable) {
        return groupJpaRepository.findAllByOrderByIdDesc(pageable);
    }

    @Override
    public List<Group> findAllByCaptainId(Long captainId) {
        return groupJpaRepository.findAllByCaptainId(captainId);
    }

    @Override
    public List<Group> findAllScrappedGroups(Long userId) {
        return scrapJpaRepository.findGroupsByUserIdAndStartTimeAfterOrderByGroupCreatedAt(userId, LocalDateTime.now());
    }
}
