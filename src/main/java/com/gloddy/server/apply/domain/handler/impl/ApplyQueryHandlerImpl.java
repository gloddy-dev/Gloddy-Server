package com.gloddy.server.apply.domain.handler.impl;

import com.gloddy.server.apply.domain.Apply;
import com.gloddy.server.apply.domain.vo.Status;
import com.gloddy.server.apply.domain.handler.ApplyQueryHandler;
import com.gloddy.server.apply.exception.NotFoundApplyException;
import com.gloddy.server.apply.infra.repository.ApplyJpaRepository;
import com.gloddy.server.group.domain.Group;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ApplyQueryHandlerImpl implements ApplyQueryHandler {

    private final ApplyJpaRepository applyJpaRepository;

    @Override
    public List<Apply> findAllApprovedAppliesFetchUserBy(Group group) {
        return applyJpaRepository.findAppliesByGroupAndStatusFetchUser(group, Status.APPROVE);
    }

    @Override
    public Long countApprovedAppliesBy(Long groupId) {
        return applyJpaRepository.countByGroupIdAndStatus(groupId, Status.APPROVE);
    }

    @Override
    public Apply findApplyToUpdateStatus(Long id) {
        return applyJpaRepository.findByIdFetchGroupAndCaptain(id)
                .orElseThrow(NotFoundApplyException::new);
    }

    @Override
    public List<Apply> findAllByGroupIdAndStatus(Long groupId, Status status) {
        return applyJpaRepository.findAllByGroupIdAndStatus(groupId, status);
    }

    @Override
    public Long countAppliesByGroupIdAndStatus(Long groupId, Status status) {
        return applyJpaRepository.countByGroupIdAndStatus(groupId, status);
    }
}
