package com.gloddy.server.user.application.internal;

import com.gloddy.server.user.application.internal.dto.UserPreviewResponse;
import com.gloddy.server.user.domain.handler.UserQueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserInternalQueryService {

    private final UserQueryHandler userQueryHandler;

    public UserPreviewResponse getUserPreview(Long userId) {
        return userQueryHandler.findUserPreviewById(userId);
    }
}
