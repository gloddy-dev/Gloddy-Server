package com.gloddy.server.estimate.service.praise;

import com.gloddy.server.auth.entity.User;
import com.gloddy.server.core.error.handler.errorCode.ErrorCode;
import com.gloddy.server.core.error.handler.exception.PraiseBusinessException;
import com.gloddy.server.core.event.reliability.ReliabilityEventPublisher;
import com.gloddy.server.estimate.entity.Praise;
import com.gloddy.server.estimate.repository.PraiseJpaRepository;
import com.gloddy.server.user.service.UserFindService;
import com.gloddy.server.estimate.dto.PraiseDto;
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
    private final UserFindService userFindService;
    private final PraiseJpaRepository praiseJpaRepository;
    private final ReliabilityEventPublisher reliabilityEventPublisher;

    @Transactional
    public void praiseInGroup(List<PraiseDto> praiseDtos, Long groupId) {
        
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
