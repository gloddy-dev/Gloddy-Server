package com.gloddy.server.estimate.service.mate;

import com.gloddy.server.auth.entity.User;
import com.gloddy.server.core.event.reliability.ReliabilityEventPublisher;
import com.gloddy.server.core.event.reliability.ReliabilityScoreUpdateEvent;
import com.gloddy.server.reliability.entity.vo.ScoreType;
import com.gloddy.server.user.service.UserFindService;
import com.gloddy.server.estimate.dto.MateDto;
import com.gloddy.server.estimate.entity.Mate;
import com.gloddy.server.estimate.repository.MateJpaRepository;
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
