package com.gloddy.server.estimate.application;

import com.gloddy.server.core.event.reliability.ReliabilityEventPublisher;
import com.gloddy.server.core.event.reliability.ReliabilityScoreUpdateEvent;
import com.gloddy.server.estimate.domain.dto.EstimateRequest;
import com.gloddy.server.mate.application.MateSaveService;
import com.gloddy.server.praise.application.PraiseService;
import com.gloddy.server.user_group.application.UserGroupFindService;
import com.gloddy.server.user_group.application.UserGroupUpdateService;
import com.gloddy.server.reliability.domain.vo.ScoreType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EstimateService {

    private final UserGroupFindService userGroupFindService;
    private final PraiseService praiseService;
    private final MateSaveService mateSaveService;
    private final UserGroupUpdateService userGroupUpdateService;
    private final ReliabilityEventPublisher reliabilityEventPublisher;

    @Transactional
    public void estimateInGroup(Long userId, Long groupId, EstimateRequest request) {
        userGroupFindService.getUserGroupsToPraise(request.getAllBePraisedUserIds(), groupId);
        request.getPraiseDtos().forEach(praiseDto -> praiseService.praise(praiseDto, groupId));
        userGroupUpdateService.completePraise(userId, groupId);
        mateSaveService.save(request.getMateDto(), userId);
        reliabilityEventPublisher.publish(new ReliabilityScoreUpdateEvent(userId, ScoreType.Estimated));
    }
}
