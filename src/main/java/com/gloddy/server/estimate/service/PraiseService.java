package com.gloddy.server.estimate.service;

import com.gloddy.server.Mate.service.UserFindService;
import com.gloddy.server.auth.entity.User;
import com.gloddy.server.auth.handler.UserHandler;
import com.gloddy.server.auth.repository.UserRepository;
import com.gloddy.server.domain.AbsenceInGroupDomain;
import com.gloddy.server.domain.UserPraise;
import com.gloddy.server.estimate.dto.PraiseDto;
import com.gloddy.server.estimate.entity.AbsenceInGroup;
import com.gloddy.server.group.entity.Group;
import com.gloddy.server.group.handler.GroupQueryHandler;
import com.gloddy.server.group.service.GroupUserCountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PraiseService {

    private final GroupUserCountService groupUserCountService;
    private final AbsenceInGroupFindService absenceInGroupFindService;
    private final UserFindService userFindService;

    @Transactional
    public void praiseInGroup(List<PraiseDto> praiseDtos, Long groupId) {

        Long countUserInGroup = groupUserCountService.countUserInGroup(groupId);

        List<UserPraise> userPraises = praiseDtos.stream()
                .map(praiseDto -> {
                    AbsenceInGroup findAbsenceInGroup = absenceInGroupFindService.findByGroupIdAndUserId(groupId, praiseDto.getUserId());
                    AbsenceInGroupDomain absenceInGroupDomain = new AbsenceInGroupDomain(findAbsenceInGroup, countUserInGroup);
                    User findUser = userFindService.findById(praiseDto.getUserId());
                    return new UserPraise(findUser, absenceInGroupDomain, praiseDto.getPraiseValue());
                })
                .collect(Collectors.toUnmodifiableList());

        userPraises.forEach(UserPraise::applyPraisePoint);
    }
}
