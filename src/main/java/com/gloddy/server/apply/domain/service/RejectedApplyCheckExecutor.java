package com.gloddy.server.apply.domain.service;

import com.gloddy.server.apply.domain.Apply;
import com.gloddy.server.apply.domain.handler.ApplyQueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RejectedApplyCheckExecutor {

    private final ApplyQueryHandler applyQueryHandler;

    public void check(Long userId, Long applyId) {
        Apply apply = applyQueryHandler.findById(applyId);

        validate(apply, userId);
        apply.checkRejected();
    }

    private void validate(Apply apply, Long userId) {
        if (!apply.getUser().getId().equals(userId)) {
            throw new RuntimeException("no authorize rejected apply check");
        }
    }
}
