package com.gloddy.server.user.application;

import com.gloddy.server.user.domain.dto.UserResponse;
import com.gloddy.server.user.domain.handler.UserQueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserQueryHandler userQueryHandler;

    public UserResponse.ExistNickname existNickname(String nickname) {
        boolean isExistNickname = userQueryHandler.existsByNickname(nickname);
        return new UserResponse.ExistNickname(isExistNickname);
    }
}
