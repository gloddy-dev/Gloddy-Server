package com.gloddy.server.group_member.application;

import com.gloddy.server.auth.domain.User;
import com.gloddy.server.auth.domain.vo.kind.Status;
import com.gloddy.server.core.response.PageResponse;
import com.gloddy.server.group.domain.dto.GroupResponse;
import com.gloddy.server.group.domain.Group;
import com.gloddy.server.group_member.api.dto.GroupMemberResponse;
import com.gloddy.server.group_member.domain.GroupMember;
import com.gloddy.server.group_member.domain.dto.GroupMemberRequest;
import com.gloddy.server.group_member.domain.handler.GroupMemberQueryHandler;
import com.gloddy.server.group_member.domain.service.GroupMemberDtoMapper;
import com.gloddy.server.group_member.domain.service.GroupMemberPraisePolicy;
import com.gloddy.server.group_member.domain.service.GroupMemberPraiser;
import com.gloddy.server.group_member.event.producer.GroupMemberEventProducer;
import com.gloddy.server.group_member.infra.repository.GroupMemberJpaRepository;
import com.gloddy.server.user.domain.handler.UserQueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupMemberService {

    private final UserQueryHandler userQueryHandler;
    private final GroupMemberQueryHandler groupMemberQueryHandler;
    private final GroupMemberEventProducer groupMemberEventProducer;
    private final GroupMemberPraisePolicy groupMemberPraisePolicy;
    private final GroupMemberPraiser groupMemberPraiser;
    private final GroupMemberJpaRepository userGroupJpaRepository;

    public GroupResponse.GetGroups getExpectedMyGroup(Long userId) {
        User findUser = userQueryHandler.findByIdAndStatus(userId, Status.ACTIVE);
        List<Group> expectedMyGroups = userGroupJpaRepository.findExpectedGroupsByUser(findUser);
        return expectedMyGroups.stream()
           .map(GroupResponse.GetGroup::from)
           .collect(Collectors.collectingAndThen(Collectors.toList(), GroupResponse.GetGroups::new));
    }

    public PageResponse<GroupResponse.GetParticipatedGroup> getParticipatedMyGroup(Long userId, int page, int size) {
        User findUser = userQueryHandler.findByIdAndStatus(userId, Status.ACTIVE);
        return PageResponse.from(
           userGroupJpaRepository.findParticipatedGroupsByUser(findUser, PageRequest.of(page, size))
           .map(GroupResponse.GetParticipatedGroup::from)
        );
    }

    @Transactional
    public void estimateGroupMembers(GroupMemberRequest.Estimate request, Long userId, Long groupId) {
        GroupMember estimator = groupMemberQueryHandler.findByUserIdAndGroupId(userId, groupId);
        estimator.estimateGroupMembers(request, groupMemberPraisePolicy, groupMemberPraiser, groupMemberEventProducer);
    }

    @Transactional(readOnly = true)
    public GroupMemberResponse.GetAll getGroupMembers(Long groupId) {
        List<GroupMember> groupMembers = groupMemberQueryHandler.findAllByGroupId(groupId);
        return GroupMemberDtoMapper.mapToGetAllFrom(groupMembers);
    }
}
