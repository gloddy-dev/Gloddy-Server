package com.gloddy.server.estimate.service.mate;

import com.gloddy.server.estimate.repository.MateJpaRepository;
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
