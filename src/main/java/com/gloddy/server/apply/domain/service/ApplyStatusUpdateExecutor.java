package com.gloddy.server.apply.domain.service;

import com.gloddy.server.apply.domain.Apply;
import com.gloddy.server.apply.domain.handler.ApplyQueryHandler;
import com.gloddy.server.apply.domain.vo.Status;
import com.gloddy.server.apply.event.producer.ApplyEventProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApplyStatusUpdateExecutor {

    private final ApplyStatusUpdatePolicy applyStatusUpdatePolicy;
    private final ApplyQueryHandler applyQueryHandler;
    private final ApplyEventProducer applyEventProducer;

    public void execute(Long userId, Long applyId, Status status) {
        Apply apply = applyQueryHandler.findById(applyId);

        applyStatusUpdatePolicy.validate(userId, apply.getGroup(), status);

        if (status.isApprove()) {
            apply.approveApply(applyEventProducer);
        } else if (status.isRefuse()) {
            apply.refuseApply();
        } else {
            throw new RuntimeException("Apply Status Invalid");
        }
    }
}
