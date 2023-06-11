package com.gloddy.server.mate.application;

import com.gloddy.server.mate.infra.repository.MateJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MateDeleteService {
    private final MateJpaRepository mateJpaRepository;

    public void deleteMateForUser(Long mateId, Long userId) {
        mateJpaRepository.deleteByMateIdAndUserId(mateId, userId);
    }
}
