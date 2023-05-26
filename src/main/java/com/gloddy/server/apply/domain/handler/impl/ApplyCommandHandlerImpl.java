package com.gloddy.server.apply.domain.handler.impl;

import com.gloddy.server.apply.domain.Apply;
import com.gloddy.server.apply.domain.handler.ApplyCommandHandler;
import com.gloddy.server.apply.infra.repository.ApplyJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ApplyCommandHandlerImpl implements ApplyCommandHandler {
    private final ApplyJpaRepository applyJpaRepository;

    @Override
    public Apply save(Apply apply) {
        return applyJpaRepository.save(apply);
    }
}
