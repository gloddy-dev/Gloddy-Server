package com.gloddy.server.apply.application;

import com.gloddy.server.apply.domain.dto.ApplyRequest;
import com.gloddy.server.apply.domain.dto.ApplyResponse;
import com.gloddy.server.apply.domain.Apply;
import com.gloddy.server.apply.domain.vo.Status;
import com.gloddy.server.apply.infra.repository.ApplyJpaRepository;
import com.gloddy.server.auth.domain.User;
import com.gloddy.server.core.error.handler.errorCode.ErrorCode;
import com.gloddy.server.core.error.handler.exception.GroupBusinessException;
import com.gloddy.server.core.utils.event.GroupParticipateEvent;
import com.gloddy.server.user.infra.repository.UserRepository;
import com.gloddy.server.group.domain.Group;
import com.gloddy.server.group.infra.repository.GroupJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ApplyService {

    private final ApplyJpaRepository applyJpaRepository;
    private final UserRepository userRepository;
    private final GroupJpaRepository groupJpaRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    // TODO: user exception 처리
    @Transactional
    public ApplyResponse.create createApply(Long userId, Long groupId, ApplyRequest.create request) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("유저 없음"));
        Group group = groupJpaRepository.findById(groupId)
            .orElseThrow(() -> new RuntimeException("그룹 없음"));
        Apply apply = applyJpaRepository.save(
            Apply.builder()
                .user(user)
                .group(group)
                .content(request.getIntroduce())
                .reason(request.getReason())
                .build()
        );
        return new ApplyResponse.create(apply.getId());
    }

    // TODO: exception 처리
    @Transactional
    public void deleteApply(Long userId, Long groupId) {
        Apply apply = applyJpaRepository.findByUserIdAndGroupId(userId, groupId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 지원서"));
        applyJpaRepository.delete(apply);
    }

    // TODO: exception 처리
    @Transactional
    public void updateStatusApply(Long userId, Long groupId, Long applyId, Status status) {
        Apply apply = applyJpaRepository.findById(applyId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 지원서"));
        if(checkCaptain(groupId, userId)){
            apply.updateStatus(status);
        }

        if (apply.isApproved()) {
            applicationEventPublisher.publishEvent(new GroupParticipateEvent(apply.getUser().getId(), groupId));
        }
    }

    public Boolean checkCaptain(Long groupId, Long userId) {
        Group group = groupJpaRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 그룹"));
        if(group.getCaptain().getId().equals(userId)){
            return true;
        }
        throw new GroupBusinessException(ErrorCode.GROUP_NOT_CAPTAIN);
    }
}
