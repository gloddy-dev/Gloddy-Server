package com.gloddy.server.estimate.service.praise;

import ch.qos.logback.core.joran.action.AppenderRefAction;
import com.gloddy.server.auth.entity.User;
import com.gloddy.server.core.error.handler.errorCode.ErrorCode;
import com.gloddy.server.core.error.handler.exception.PraiseBusinessException;
import com.gloddy.server.estimate.dto.PraiseResponse;
import com.gloddy.server.estimate.entity.Praise;
import com.gloddy.server.estimate.repository.PraiseJpaRepository;
import com.gloddy.server.estimate.service.AbsenceInGroupFindService;
import com.gloddy.server.user.service.UserFindService;
import com.gloddy.server.domain.AbsenceInGroupDomain;
import com.gloddy.server.domain.UserPraise;
import com.gloddy.server.estimate.dto.PraiseDto;
import com.gloddy.server.estimate.entity.AbsenceInGroup;
import com.gloddy.server.group.service.GroupUserCountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.gloddy.server.estimate.dto.PraiseResponse.*;

@Service
@RequiredArgsConstructor
public class PraiseService {

    private final GroupUserCountService groupUserCountService;
    private final AbsenceInGroupFindService absenceInGroupFindService;
    private final UserFindService userFindService;
    private final PraiseJpaRepository praiseJpaRepository;

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

    @Transactional(readOnly = true)
    public getPraiseForUser getPraiseForUser(Long userId) {
        Praise praise = praiseJpaRepository.findByUserId(userId)
                .orElseThrow(() -> new PraiseBusinessException(ErrorCode.PRAISE_NOT_FOUND));

        return new getPraiseForUser(
                praise.getTotalCalmCount(),
                praise.getTotalKindCount(),
                praise.getTotalActiveCount(),
                praise.getTotalHumorCount(),
                praise.getTotalAbsenceCount()
        );
    }
}