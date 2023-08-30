package com.gloddy.server.apply.application;

import com.gloddy.server.apply.domain.dto.ApplyRequest;
import com.gloddy.server.apply.domain.dto.ApplyResponse;
import com.gloddy.server.apply.domain.Apply;
import com.gloddy.server.apply.domain.handler.ApplyCommandHandler;
import com.gloddy.server.apply.domain.handler.ApplyQueryHandler;
import com.gloddy.server.apply.domain.service.ApplyDtoMapper;
import com.gloddy.server.apply.domain.service.ApplyGetExecutor;
import com.gloddy.server.apply.domain.service.ApplyStatusUpdatePolicy;
import com.gloddy.server.apply.domain.service.RejectedApplyCheckExecutor;
import com.gloddy.server.apply.domain.vo.Status;
import com.gloddy.server.apply.event.producer.ApplyEventProducer;
import com.gloddy.server.auth.domain.User;
import com.gloddy.server.core.event.GroupParticipateEvent;
import com.gloddy.server.group.domain.handler.GroupQueryHandler;
import com.gloddy.server.user.domain.handler.UserQueryHandler;
import com.gloddy.server.group.domain.Group;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplyService {

    private final ApplyGetExecutor applyGetExecutor;
    private final ApplyCommandHandler applyCommandHandler;
    private final ApplyQueryHandler applyQueryHandler;
    private final UserQueryHandler userQueryHandler;
    private final GroupQueryHandler groupQueryHandler;
    private final ApplyStatusUpdatePolicy applyStatusUpdatePolicy;
    private final ApplyEventProducer applyEventProducer;
    private final RejectedApplyCheckExecutor rejectedApplyCheckExecutor;

    @Transactional
    public ApplyResponse.Create createApply(Long userId, Long groupId, ApplyRequest.Create request) {
        User user = userQueryHandler.findById(userId);
        Group group = groupQueryHandler.findById(groupId);
        Apply apply = applyCommandHandler.save(
                group.createApply(user, request.getIntroduce(), request.getReason())
        );
        return new ApplyResponse.Create(apply.getId());
    }

    @Transactional
    public void updateStatusApply(Long userId, Long groupId, Long applyId, Status status) {
        User user = userQueryHandler.findById(userId);
        Apply apply = applyQueryHandler.findApplyToUpdateStatus(applyId);

        applyStatusUpdatePolicy.validate(user, apply.getGroup());

        if (status.isApprove()) {
            apply.approveApply(applyEventProducer);
            return;
        }
        apply.refuseApply();
    }

    public ApplyResponse.GetAll getAll(Long userId, Long groupId) {
        List<Apply> applies = applyGetExecutor.getAllWaitApply(userId, groupId);
        return ApplyDtoMapper.mapToGetAll(applies);
    }

    @Transactional
    public void checkRejectedApply(Long userId, Long applyId) {
        rejectedApplyCheckExecutor.check(userId, applyId);
    }
}
