package com.gloddy.server.apply.handler.impl;

import com.gloddy.server.apply.entity.Apply;
import com.gloddy.server.apply.entity.vo.Status;
import com.gloddy.server.apply.handler.ApplyQueryHandler;
import com.gloddy.server.apply.repository.ApplyJpaRepository;
import com.gloddy.server.group.entity.Group;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ApplyQueryHandlerImpl implements ApplyQueryHandler {

    private ApplyJpaRepository applyJpaRepository;

    @Override
    public List<Apply> findAllApprovedAppliesFetchUserBy(Group group) {
        return applyJpaRepository.findAppliesByGroupAndStatusFetchUser(group, Status.APPROVE);
    }
}
