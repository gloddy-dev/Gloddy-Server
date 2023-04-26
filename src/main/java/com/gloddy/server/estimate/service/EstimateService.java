package com.gloddy.server.estimate.service;

import com.gloddy.server.estimate.dto.EstimateRequest;
import com.gloddy.server.estimate.service.mate.MateSaveService;
import com.gloddy.server.estimate.service.praise.PraiseService;
import com.gloddy.server.group.service.UserGroupFindService;
import com.gloddy.server.group.service.UserGroupUpdateService;
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

    @Transactional
    public void estimateInGroup(Long userId, Long groupId, EstimateRequest request) {
        userGroupFindService.getUserGroupsToPraise(request.getAllBePraisedUserIds(), groupId);
        request.getPraiseDtos().forEach(praiseDto -> praiseService.praiseInGroup(praiseDto, groupId));
        userGroupUpdateService.completePraise(userId, groupId);
        mateSaveService.save(request.getMateDto(), userId);
    }
}
