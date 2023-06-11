package com.gloddy.server.praise.application;

import com.gloddy.server.core.error.handler.errorCode.ErrorCode;
import com.gloddy.server.core.error.handler.exception.PraiseBusinessException;
import com.gloddy.server.praise.domain.Praise;
import com.gloddy.server.praise.domain.handler.PraiseQueryHandler;
import com.gloddy.server.praise.domain.vo.PraiseValue;
import com.gloddy.server.praise.event.producer.PraiseEventProducer;
import com.gloddy.server.praise.infra.repository.PraiseJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static com.gloddy.server.praise.domain.dto.PraiseResponse.*;

@Service
@RequiredArgsConstructor
public class PraiseService {

    private final PraiseJpaRepository praiseJpaRepository;
    private final PraiseQueryHandler praiseQueryHandler;
    private final PraiseEventProducer praiseEventProducer;

    @Transactional
    public void updatePraisePoint(Long userId, PraiseValue praiseValue) {
        Praise praise = praiseQueryHandler.findByUserId(userId);
        praise.plusCount(praiseValue, praiseEventProducer);
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
