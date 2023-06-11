package com.gloddy.server.mate.application;

import com.gloddy.server.auth.domain.User;
import com.gloddy.server.core.event.reliability.ReliabilityEventPublisher;
import com.gloddy.server.core.event.reliability.ReliabilityScoreUpdateEvent;
import com.gloddy.server.reliability.domain.vo.ScoreType;
import com.gloddy.server.user.application.UserFindService;
import com.gloddy.server.mate.domain.Mate;
import com.gloddy.server.mate.infra.repository.MateJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.gloddy.server.group_member.domain.dto.GroupMemberRequest.Estimate.*;

@Service
@RequiredArgsConstructor
public class MateSaveService {
    private final MateJpaRepository mateJpaRepository;
    private final UserFindService userFindService;
    private final ReliabilityEventPublisher reliabilityEventPublisher;


    @Transactional
    public Mate save(MateInfo mateInfo, Long mateId) {
        User user = userFindService.findById(mateInfo.getUserId());
        reliabilityEventPublisher.publish(new ReliabilityScoreUpdateEvent(mateInfo.getUserId(), ScoreType.Mated));
        return mateJpaRepository.save(
                Mate.builder()
                   .mateId(mateId)
                   .selectionReason(mateInfo.getSelectionReason())
                   .user(user)
                   .build());
    }
}