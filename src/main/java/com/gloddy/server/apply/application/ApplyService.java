package com.gloddy.server.apply.application;

import com.gloddy.server.apply.domain.dto.ApplyRequest;
import com.gloddy.server.apply.domain.dto.ApplyResponse;
import com.gloddy.server.apply.domain.Apply;
import com.gloddy.server.apply.domain.handler.ApplyCommandHandler;
import com.gloddy.server.apply.domain.handler.ApplyQueryHandler;
import com.gloddy.server.apply.domain.service.ApplyStatusUpdatePolicy;
import com.gloddy.server.apply.domain.vo.Status;
import com.gloddy.server.auth.domain.User;
import com.gloddy.server.group.domain.handler.GroupQueryHandler;
import com.gloddy.server.user.domain.handler.UserQueryHandler;
import com.gloddy.server.group.domain.Group;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ApplyService {

    private final ApplyCommandHandler applyCommandHandler;
    private final ApplyQueryHandler applyQueryHandler;
    private final UserQueryHandler userQueryHandler;
    private final GroupQueryHandler groupQueryHandler;
    private final ApplyStatusUpdatePolicy applyStatusUpdatePolicy;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Transactional
    public ApplyResponse.Create createApply(Long userId, Long groupId, ApplyRequest.Create request) {
        User user = userQueryHandler.findById(userId);
        Group group = groupQueryHandler.findById(groupId);
        Apply apply = applyCommandHandler.save(
                group.createApply(user, request.getIntroduce(), request.getReason())
        );
        return new ApplyResponse.Create(apply.getId());
    }

    // TODO: exception 처리
    // TODO: 모임 나갔는데, Apply 삭제? -> 다시 설계할 필요가 있을 듯
//    @Transactional
//    public void deleteApply(Long userId, Long groupId) {
//        Apply apply = applyJpaRepository.findByUserIdAndGroupId(userId, groupId)
//                .orElseThrow(() -> new RuntimeException("존재하지 않는 지원서"));
//        applyJpaRepository.delete(apply);
//    }

    @Transactional
    public void updateStatusApply(Long userId, Long groupId, Long applyId, Status status) {
        User user = userQueryHandler.findById(userId);
        Apply apply = applyQueryHandler.findApplyToUpdateStatus(applyId);

        applyStatusUpdatePolicy.validate(user, apply.getGroup());

        if (status.isApprove()) {
            apply.approveApply(applicationEventPublisher);
            return;
        }
        apply.refuseApply();
    }
}
