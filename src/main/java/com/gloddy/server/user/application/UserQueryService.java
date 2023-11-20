package com.gloddy.server.user.application;

import com.gloddy.server.user.domain.dto.PraiseResponse;
import com.gloddy.server.user.domain.handler.UserQueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserQueryService {
    private final UserQueryHandler userQueryHandler;

    public PraiseResponse.GetPraiseForUser getUserPraise(Long userId) {
        return userQueryHandler.findPraiseDtoByUserId(userId);
    }
}
