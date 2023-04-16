package com.gloddy.server.estimate.service;

import com.gloddy.server.estimate.entity.UserGroupAbsence;
import com.gloddy.server.estimate.repository.UserGroupAbsenceJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AbsenceInGroupFindService {
    private final UserGroupAbsenceJpaRepository userGroupAbsenceJpaRepository;

    public UserGroupAbsence findByGroupIdAndUserId(Long groupId, Long userId) {
        return userGroupAbsenceJpaRepository.findByGroupIdAndUserId(groupId, userId)
                .orElseThrow(() -> new RuntimeException("AbsenceInGroup not found"));
    }
}
