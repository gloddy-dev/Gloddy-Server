package com.gloddy.server.estimate.service;

import com.gloddy.server.auth.entity.User;
import com.gloddy.server.estimate.entity.UserGroupAbsence;
import com.gloddy.server.estimate.repository.UserGroupAbsenceJpaRepository;
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
    private final UserGroupAbsenceJpaRepository userGroupAbsenceJpaRepository;

    public UserGroupAbsence saveAbsenceInGroup(Long userId, Long groupId) {

        User user = userFindService.findById(userId);
        Group group = groupQueryHandler.findById(groupId);
        UserGroupAbsence userGroupAbsence = UserGroupAbsence.builder()
                .user(user)
                .group(group)
                .build();
        return userGroupAbsenceJpaRepository.save(userGroupAbsence);
    }
}
