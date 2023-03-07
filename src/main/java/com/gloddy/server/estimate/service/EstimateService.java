package com.gloddy.server.estimate.service;

import com.gloddy.server.estimate.dto.EstimateRequest;
import com.gloddy.server.estimate.service.mate.MateSaveService;
import com.gloddy.server.estimate.service.praise.PraiseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EstimateService {

    private final PraiseService praiseService;
    private final MateSaveService mateSaveService;

    @Transactional
    public void estimateInGroup(Long userId, Long groupId, EstimateRequest request) {
        praiseService.praiseInGroup(request.getPraiseDtos(), groupId);
        mateSaveService.save(request.getMateDto(), userId);
    }
}
