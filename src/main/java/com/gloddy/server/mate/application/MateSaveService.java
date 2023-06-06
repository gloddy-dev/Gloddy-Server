package com.gloddy.server.mate.application;

import com.gloddy.server.auth.domain.User;
import com.gloddy.server.core.event.reliability.ReliabilityEventPublisher;
import com.gloddy.server.core.event.reliability.ReliabilityScoreUpdateEvent;
import com.gloddy.server.reliability.domain.vo.ScoreType;
import com.gloddy.server.user.application.UserFindService;
import com.gloddy.server.estimate.domain.dto.MateDto;
import com.gloddy.server.mate.domain.Mate;
import com.gloddy.server.mate.infra.repository.MateJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MateSaveService {
    private final MateJpaRepository mateJpaRepository;
    private final UserFindService userFindService;
    private final ReliabilityEventPublisher reliabilityEventPublisher;


    @Transactional
    public Mate save(MateDto mateDto, Long mateId) {
        User user = userFindService.findById(mateDto.getUserId());
        reliabilityEventPublisher.publish(new ReliabilityScoreUpdateEvent(mateDto.getUserId(), ScoreType.Mated));
        return mateJpaRepository.save(
                Mate.builder()
                   .mateId(mateId)
                   .selectionReason(mateDto.getSelectionReason())
                   .user(user)
                   .build());
    }
}
