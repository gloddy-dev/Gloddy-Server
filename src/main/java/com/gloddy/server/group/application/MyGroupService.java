package com.gloddy.server.group.application;

import com.gloddy.server.auth.domain.User;
import com.gloddy.server.core.response.PageResponse;
import com.gloddy.server.group.domain.dto.GroupResponse;
import com.gloddy.server.group.domain.Group;
import com.gloddy.server.group.infra.repository.UserGroupJpaRepository;
import com.gloddy.server.user.application.UserFindService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MyGroupService {

    private final UserFindService userFindService;
    private final UserGroupJpaRepository userGroupJpaRepository;

    public GroupResponse.GetGroups getExpectedMyGroup(Long userId) {
        User findUser = userFindService.findById(userId);
        List<Group> expectedMyGroups = userGroupJpaRepository.findExpectedGroupsByUser(findUser);
        return expectedMyGroups.stream()
           .map(GroupResponse.GetGroup::from)
           .collect(Collectors.collectingAndThen(Collectors.toList(), GroupResponse.GetGroups::new));
    }

    public PageResponse<GroupResponse.GetParticipatedGroup> getParticipatedMyGroup(Long userId, int page, int size) {
        User findUser = userFindService.findById(userId);
        return PageResponse.from(
           userGroupJpaRepository.findParticipatedGroupsByUser(findUser, PageRequest.of(page, size))
           .map(GroupResponse.GetParticipatedGroup::from)
        );
    }
}
