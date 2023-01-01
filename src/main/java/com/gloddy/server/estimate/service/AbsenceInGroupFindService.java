package com.gloddy.server.estimate.service;

import com.gloddy.server.estimate.entity.AbsenceInGroup;
import com.gloddy.server.estimate.repository.AbsenceInGroupJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AbsenceInGroupFindService {
    private final AbsenceInGroupJpaRepository absenceInGroupJpaRepository;

    public AbsenceInGroup findByGroupIdAndUserId(Long groupId, Long userId) {
        return absenceInGroupJpaRepository.findByGroupIdAndUserId(groupId, userId)
                .orElseThrow(() -> new RuntimeException("AbsenceInGroup not found"));
    }
}
