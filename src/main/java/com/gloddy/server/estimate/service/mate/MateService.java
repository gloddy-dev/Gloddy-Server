package com.gloddy.server.estimate.service.mate;

import com.gloddy.server.estimate.dto.MateResponse;
import com.gloddy.server.estimate.repository.MateJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MateService {
    private final MateGetService mateGetService;
    private final MateDeleteService mateDeleteService;

    public MateResponse.getMatesForUser getMatesForUser(Long userId) {
        return mateGetService.getMateForUser(userId);
    }

    public void deleteMateForUser(Long mateId, Long userId) {
        mateDeleteService.deleteMateForUser(mateId, userId);
    }
}
