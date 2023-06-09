package com.gloddy.server.praise.domain.handler.impl;

import com.gloddy.server.praise.domain.Praise;
import com.gloddy.server.praise.domain.handler.PraiseQueryHandler;
import com.gloddy.server.praise.exception.NotFoundPraiseException;
import com.gloddy.server.praise.infra.repository.PraiseJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PraiseQueryHandlerImpl implements PraiseQueryHandler {
    private final PraiseJpaRepository praiseJpaRepository;

    @Override
    public Praise findByUserId(Long userId) {
        return praiseJpaRepository.findByUserId(userId)
                .orElseThrow(NotFoundPraiseException::new);
    }
}
