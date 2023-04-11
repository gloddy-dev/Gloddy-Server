package com.gloddy.server.estimate.service;

import com.gloddy.server.auth.entity.User;
import com.gloddy.server.estimate.entity.AbsenceInGroup;
import com.gloddy.server.estimate.repository.AbsenceInGroupJpaRepository;
import com.gloddy.server.group.entity.Group;
import com.gloddy.server.group.handler.GroupQueryHandler;
import com.gloddy.server.user.service.UserFindService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AbsenceInGroupSaveService {

    private final GroupQueryHandler groupQueryHandler;
    private final UserFindService userFindService;
    private final AbsenceInGroupJpaRepository absenceInGroupJpaRepository;

    public AbsenceInGroup saveAbsenceInGroup(Long userId, Long groupId) {

        User user = userFindService.findById(userId);
        Group group = groupQueryHandler.findById(groupId);
        AbsenceInGroup absenceInGroup = AbsenceInGroup.builder()
                .user(user)
                .group(group)
                .build();
        return absenceInGroupJpaRepository.save(absenceInGroup);
    }
}