package com.gloddy.server.group.service;

import com.gloddy.server.apply.entity.Apply;
import com.gloddy.server.apply.entity.vo.Status;
import com.gloddy.server.apply.repository.ApplyJpaRepository;
import com.gloddy.server.auth.entity.User;
import com.gloddy.server.core.utils.event.GroupParticipateEvent;
import com.gloddy.server.core.event.reliability.ReliabilityEventPublisher;
import com.gloddy.server.core.event.reliability.ReliabilityScoreUpdateEvent;
import com.gloddy.server.reliability.entity.vo.ScoreType;
import com.gloddy.server.user.repository.UserRepository;
import com.gloddy.server.core.error.handler.errorCode.ErrorCode;
import com.gloddy.server.core.error.handler.exception.UserBusinessException;
import com.gloddy.server.core.response.PageResponse;
import com.gloddy.server.core.utils.DateTimeUtils;
import com.gloddy.server.domain.GroupApplies;
import com.gloddy.server.domain.GroupUsers;
import com.gloddy.server.group.dto.GroupRequest;
import com.gloddy.server.group.dto.GroupResponse;
import com.gloddy.server.group.entity.Group;
import com.gloddy.server.group.repository.GroupJpaRepository;
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
    private final ApplyJpaRepository applyJpaRepository;
    private final UserRepository userRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final ReliabilityEventPublisher reliabilityEventPublisher;

    // 같은 학교의 소모임만 조회 -> 소모임 개최자와 해당 사용자의 학교가 같은 경우의 소모임만 조회
    // 참가 멤버 수 -> apply 엔티티에 상태값 추가해 가져오기
    // TODO: exception 처리
    // TODO: 모임 날짜 처리 (LocalDate에 요일도 포함되나 요일은 어찜)
    @Transactional(readOnly = true)
    public PageResponse<GroupResponse.GetGroup> getGroups(Long userId, int size, int page) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("유저 없음"));
        Pageable pageable = PageRequest.of(page, size);
        Page<GroupResponse.GetGroup> groups = groupJpaRepository.findBySchoolOrderByIdDesc(pageable, user.getSchool())
                .map(group -> new GroupResponse.GetGroup(
                                group.getId(),
                                group.getFileUrl(),
                                group.getTitle(),
                                group.getContent(),
                                applyJpaRepository.countApplyByGroupIdAndStatus(group.getId(), Status.APPROVE),
                                group.getPlace(),
                                group.getMeetDate()
                        )
                );

        return PageResponse.from(groups);
    }


    // TODO: school 컬럼 추가하셈
    @Transactional
    public GroupResponse.Create createGroup(Long captainId, GroupRequest.Create req) {

        User captain = userRepository.findById(captainId)
                .orElseThrow(() -> new RuntimeException("해당 회원을 찾을 수 없습니다."));

        Group buildGroup = Group.builder()
                .user(captain)
                .fileUrl(req.getFileUrl())
                .title(req.getTitle())
                .content(req.getContent())
                .startTime(
                        DateTimeUtils.concatDateAndTime(
                                req.getMeetDate(),
                                DateTimeUtils.StringToLocalTime(req.getStartTime())
                        )
                )
                .endTime(
                        DateTimeUtils.concatDateAndTime(
                                req.getMeetDate(),
                                DateTimeUtils.StringToLocalTime(req.getEndTime())
                        )
                )
                .place(req.getPlace())
                .placeLatitude(req.getPlace_latitude())
                .placeLongitude(req.getPlace_longitude())
                .maxUser(req.getMaxUser())
                .school(captain.getSchool())
                .build();

        Group saveGroup = groupJpaRepository.save(buildGroup);

        eventPublisher.publishEvent(new GroupParticipateEvent(captainId, saveGroup.getId()));
        reliabilityEventPublisher.publish(new ReliabilityScoreUpdateEvent(captain, ScoreType.Created_Group));

        return new GroupResponse.Create(saveGroup.getId());
    }

    @Transactional(readOnly = true)
    public GroupResponse.GetGroupDetail getGroupDetail(Long userId, Long groupId) {

        User findUser = userRepository.findById(userId)
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
                groupUsers.getGroup().getStartTime().toLocalTime().toString(),
                groupUsers.getGroup().getEndTime().toLocalTime().toString(),
                groupUsers.getGroup().getPlace(),
                groupUsers.getGroup().getPlaceLatitude(),
                groupUsers.getGroup().getPlaceLongitude()
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
