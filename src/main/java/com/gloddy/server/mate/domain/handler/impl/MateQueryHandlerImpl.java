package com.gloddy.server.mate.domain.handler.impl;

import com.gloddy.server.mate.domain.handler.MateQueryHandler;
import com.gloddy.server.mate.infra.repository.MateJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MateQueryHandlerImpl implements MateQueryHandler {

    private final MateJpaRepository mateJpaRepository;

    @Override
    public Long countByMateId(Long mateId) {
        return mateJpaRepository.countByMateId(mateId);
    }
}
