package com.gloddy.server.mate.application;

import com.gloddy.server.user.domain.User;
import com.gloddy.server.mate.event.MateCreateEvent;
import com.gloddy.server.mate.event.producer.MateEventProducer;
import com.gloddy.server.mate.domain.Mate;
import com.gloddy.server.mate.infra.repository.MateJpaRepository;
import com.gloddy.server.user.domain.handler.UserQueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.gloddy.server.group_member.domain.dto.GroupMemberRequest.Estimate.*;

@Service
@RequiredArgsConstructor
public class MateSaveService {
    private final MateJpaRepository mateJpaRepository;
    private final UserQueryHandler userQueryHandler;
    private final MateEventProducer mateEventProducer;

    @Transactional
    public Mate save(MateInfo mateInfo, Long mateId) {
        User user = userQueryHandler.findById(mateInfo.getUserId());
        mateEventProducer.produceEvent(new MateCreateEvent(mateInfo.getUserId()));
        return mateJpaRepository.save(
                Mate.builder()
                   .mateId(mateId)
                   .selectionReason(mateInfo.getSelectionReason())
                   .user(user)
                   .build());
    }
}
