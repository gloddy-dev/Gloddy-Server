package com.gloddy.server.group.service;

import com.gloddy.server.apply.repository.ApplyJpaRepository;
import com.gloddy.server.auth.entity.User;
import com.gloddy.server.auth.repository.UserRepository;
import com.gloddy.server.core.response.PageResponse;
import com.gloddy.server.group.dto.response.GetGroupResponse;
import com.gloddy.server.group.repository.GroupJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupJpaRepository groupJpaRepository;
    private final ApplyJpaRepository applyJpaRepository;
    private final UserRepository userRepository;

    // TODO: 참가 멤버 수 -> apply 엔티티에 상태값 추가해 가져오기
    // TODO: exception 처리
    // TODO: 모임 날짜 처리 (LocalDate에 요일도 포함되나 요일은 어찜)
    @Transactional(readOnly = true)
    public PageResponse<GetGroupResponse.GetGroup> getGroups(Long userId, int size, int page) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("유저 없음"));
        Pageable pageable = PageRequest.of(page, size);
        Page<GetGroupResponse.GetGroup> groups = groupJpaRepository.findBySchoolOrderByIdDesc(pageable, user.getSchool())
             .map(group -> new GetGroupResponse.GetGroup(
                  group.getTitle(),
                  group.getContent(),
                  group.getPlace(),
                  group.getMeetDate()
             )
        );

        return PageResponse.from(groups);
    }
    
    @Transactional
    public GroupResponse.Create createGroup(Long captainId, GroupRequest.Create req) {

        User captain = userRepository.findById(captainId)
                .orElseThrow(() -> new RuntimeException("해당 회원을 찾을 수 없습니다."));

        Group buildGroup = Group.builder()
                .user(captain)
                .fileUrl(req.getFileUrl())
                .title(req.getTitle())
                .content(req.getContent())
                .meetDate(req.getMeetDate())
                .startTime(req.getStartTime())
                .endTime(req.getEndTime())
                .place(req.getPlace())
                .placeLatitude(req.getPlace_latitude())
                .placeLongitude(req.getPlace_longitude())
                .maxUser(req.getMaxUser())
                .build();

        Group saveGroup = groupJpaRepository.save(buildGroup);

        return new GroupResponse.Create(saveGroup.getId());
    }
 }
