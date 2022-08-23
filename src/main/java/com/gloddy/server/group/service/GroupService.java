package com.gloddy.server.group.service;

import com.gloddy.server.auth.entity.User;
import com.gloddy.server.auth.repository.UserRepository;
import com.gloddy.server.group.dto.GroupRequest;
import com.gloddy.server.group.dto.GroupResponse;
import com.gloddy.server.group.entity.Group;
import com.gloddy.server.group.repository.GroupJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupJpaRepository groupJpaRepository;
    private final UserRepository userRepository;

    @Transactional
    public GroupResponse.Create createGroup(Long captainId, GroupRequest.Create req) {

        User captain = userRepository.findById(captainId)
                .orElseThrow(() -> new RuntimeException("해당 회원을 찾을 수 없습니다."));

        Group buildGroup = Group.builder()
                .user(captain)
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
