package com.gloddy.server.group.application;

import com.gloddy.server.apply.domain.Apply;
import com.gloddy.server.apply.domain.vo.Status;
import com.gloddy.server.apply.infra.repository.ApplyJpaRepository;
import com.gloddy.server.auth.domain.User;
import com.gloddy.server.core.event.reliability.ReliabilityEventPublisher;
import com.gloddy.server.core.event.reliability.ReliabilityScoreUpdateEvent;
import com.gloddy.server.group.domain.handler.GroupCommandHandler;
import com.gloddy.server.group.domain.service.GroupFactory;
import com.gloddy.server.reliability.domain.vo.ScoreType;
import com.gloddy.server.core.utils.event.GroupParticipateEvent;
import com.gloddy.server.user.domain.handler.UserQueryHandler;
import com.gloddy.server.user.infra.repository.UserJpaRepository;
import com.gloddy.server.core.error.handler.errorCode.ErrorCode;
import com.gloddy.server.core.error.handler.exception.UserBusinessException;
import com.gloddy.server.core.response.PageResponse;
import com.gloddy.server.core.utils.DateTimeUtils;
import com.gloddy.server.domain.GroupApplies;
import com.gloddy.server.domain.GroupUsers;
import com.gloddy.server.group.domain.dto.GroupRequest;
import com.gloddy.server.group.domain.dto.GroupResponse;
import com.gloddy.server.group.domain.Group;
import com.gloddy.server.group.infra.repository.GroupJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.*;


@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupJpaRepository groupJpaRepository;
    private final GroupCommandHandler groupCommandHandler;
    private final ApplyJpaRepository applyJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final UserQueryHandler userQueryHandler;
    private final ApplicationEventPublisher eventPublisher;
    private final GroupFactory groupFactory;

    // 같은 학교의 소모임만 조회 -> 소모임 개최자와 해당 사용자의 학교가 같은 경우의 소모임만 조회
    // 참가 멤버 수 -> apply 엔티티에 상태값 추가해 가져오기
    // TODO: exception 처리
    // TODO: 모임 날짜 처리 (LocalDate에 요일도 포함되나 요일은 어찜)
    @Transactional(readOnly = true)
    public PageResponse<GroupResponse.GetGroup> getGroups(Long userId, int size, int page) {
        User user = userJpaRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("유저 없음"));
        Pageable pageable = PageRequest.of(page, size);
        Page<GroupResponse.GetGroup> groups = groupJpaRepository.findBySchoolOrderByIdDesc(pageable, user.getSchool())
                .map(group -> new GroupResponse.GetGroup(
                                group.getId(),
                                group.getFileUrl(),
                                group.getTitle(),
                                group.getContent(),
                                applyJpaRepository.countApplyByGroupIdAndStatus(group.getId(), Status.APPROVE),
                                group.getPlace().getName(),
                                group.getMeetDate()
                        )
                );

        return PageResponse.from(groups);
    }


    @Transactional
    public GroupResponse.Create createGroup(Long captainId, GroupRequest.Create req) {
        User captain = userQueryHandler.findById(captainId);

        Group newGroup = captain.saveGroup(groupFactory, eventPublisher, groupCommandHandler, req);
        return new GroupResponse.Create(newGroup.getId());
    }

    @Transactional(readOnly = true)
    public GroupResponse.GetGroupDetail getGroupDetail(Long userId, Long groupId) {

        User findUser = userJpaRepository.findById(userId)
                .orElseThrow(() -> new UserBusinessException(ErrorCode.USER_NOT_FOUND));

        Group findGroup = groupJpaRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("해당 그룹을 찾을 수 없습니다"));

        List<Apply> allApplyInFindGroup = applyJpaRepository.findAppliesByGroupAndStatusFetchUser(findGroup, Status.APPROVE);

        GroupApplies groupApplies = new GroupApplies(findGroup, allApplyInFindGroup);
        GroupUsers groupUsers = GroupUsers.from(groupApplies);

        Boolean isMyGroup = checkMyGroup(findUser, findGroup, allApplyInFindGroup);
        Boolean isGroupCaptain = isGroupCaptain(findUser, findGroup);

        return mapGroupDetailDto(isMyGroup, isGroupCaptain, groupUsers);
    }

    private GroupResponse.GetGroupDetail mapGroupDetailDto(Boolean myGroupIs, Boolean isGroupCaptain, GroupUsers groupUsers) {
        return new GroupResponse.GetGroupDetail(
                myGroupIs,
                isGroupCaptain,
                groupUsers.getGroup().getTitle(),
                groupUsers.getGroup().getFileUrl(),
                groupUsers.getGroup().getContent(),
                groupUsers.getUserCount(),
                groupUsers.getUserInfoDtos(),
                dateTimeFormatter(groupUsers.getGroup().getMeetDate()),
                groupUsers.getGroup().getDateTime().getStartDateTime().toLocalTime().toString(),
                groupUsers.getGroup().getDateTime().getEndDateTime().toLocalTime().toString(),
                groupUsers.getGroup().getPlace().getName(),
                groupUsers.getGroup().getPlace().getLatitude().toString(),
                groupUsers.getGroup().getPlace().getLongitude().toString()
        );
    }

    private Boolean checkMyGroup(User user, Group group, List<Apply> applies) {
        return isGroupCaptain(user, group) || isGroupMember(user, applies);
    }

    private Boolean isGroupCaptain(User user, Group group) {
        return user.equals(group.getCaptain());
    }

    private Boolean isGroupMember(User user, List<Apply> applies) {
        return applies.stream()
                .anyMatch(apply -> user.equals(apply.getUser()));
    }

    private String dateTimeFormatter(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        String day = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.US);
        return date + " " + day;
    }
}
