package com.gloddy.server.apply.application;

import com.gloddy.server.apply.domain.dto.ApplyRequest;
import com.gloddy.server.apply.domain.dto.ApplyResponse;
import com.gloddy.server.apply.domain.Apply;
import com.gloddy.server.apply.domain.handler.ApplyCommandHandler;
import com.gloddy.server.apply.domain.handler.ApplyQueryHandler;
import com.gloddy.server.apply.domain.service.*;
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
    private final UserQueryHandler userQueryHandler;
    private final GroupQueryHandler groupQueryHandler;
    private final RejectedApplyCheckExecutor rejectedApplyCheckExecutor;
    private final ApplyStatusUpdateExecutor applyStatusUpdateExecutor;

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
    public void updateStatusApply(Long userId, Long applyId, Status status) {
        applyStatusUpdateExecutor.execute(userId, applyId, status);
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
