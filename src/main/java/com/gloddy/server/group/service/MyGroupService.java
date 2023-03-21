package com.gloddy.server.group.service;

import com.gloddy.server.auth.entity.User;
import com.gloddy.server.group.dto.GroupResponse;
import com.gloddy.server.group.entity.Group;
import com.gloddy.server.group.repository.UserGroupJpaRepository;
import com.gloddy.server.user.service.UserFindService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MyGroupService {

    private final UserFindService userFindService;
    private final UserGroupJpaRepository userGroupJpaRepository;

    public List<GroupResponse.GetGroup> getExpectedMyGroup(Long userId) {
        User findUser = userFindService.findById(userId);
        List<Group> expectedMyGroups = userGroupJpaRepository.findExpectedGroupsByUser(findUser);
        return expectedMyGroups.stream()
           .map(GroupResponse.GetGroup::from)
           .collect(Collectors.toUnmodifiableList());
    }
}
