package com.gloddy.server.estimate.application.praise;

import com.gloddy.server.core.error.handler.errorCode.ErrorCode;
import com.gloddy.server.core.error.handler.exception.PraiseBusinessException;
import com.gloddy.server.core.event.reliability.ReliabilityEventPublisher;
import com.gloddy.server.core.event.reliability.ReliabilityScoreUpdateEvent;
import com.gloddy.server.estimate.domain.Praise;
import com.gloddy.server.estimate.infra.repository.PraiseJpaRepository;
import com.gloddy.server.user_group.domain.UserGroup;
import com.gloddy.server.group.application.UserGroupFindService;
import com.gloddy.server.estimate.domain.dto.PraiseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static com.gloddy.server.estimate.domain.dto.PraiseResponse.*;
import static com.gloddy.server.estimate.domain.vo.PraiseValue.*;
import static com.gloddy.server.reliability.domain.vo.ScoreType.*;

@Service
@RequiredArgsConstructor
public class PraiseService {

    private final UserGroupFindService userGroupFindService;
    private final PraiseJpaRepository praiseJpaRepository;
    private final ReliabilityEventPublisher reliabilityEventPublisher;

    @Transactional
    public void praise(PraiseDto praiseDto, Long groupId) {
        UserGroup findUserGroup = userGroupFindService.findByUserIdAndGroupId(praiseDto.getUserId(), groupId);
        findUserGroup.receivePraise(praiseDto.getPraiseValue());
        praiseEventPublish(findUserGroup, praiseDto);
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

    private void praiseEventPublish(UserGroup userGroup, PraiseDto praiseDto) {
        if (ABSENCE.equals(praiseDto.getPraiseValue())) {
            if (!(userGroup.isAlreadyAbsenceVoteCountOver())) {
                reliabilityEventPublisher.publish(new ReliabilityScoreUpdateEvent(praiseDto.getUserId(), Absence_Group));
                return;
            }
            return;
        }
        reliabilityEventPublisher.publish(new ReliabilityScoreUpdateEvent(praiseDto.getUserId(), Praised));
    }
}
