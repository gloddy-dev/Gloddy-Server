package com.gloddy.server.user.application;

import com.gloddy.server.auth.domain.User;
import com.gloddy.server.auth.domain.vo.kind.Status;
import com.gloddy.server.user.domain.dto.UserUpdateResponse;
import com.gloddy.server.user.domain.handler.UserQueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.gloddy.server.user.domain.dto.UserUpdateRequest.*;

@Service
@RequiredArgsConstructor
public class UserUpdateService {
    private final UserQueryHandler userQueryHandler;

    @Transactional
    public UserUpdateResponse update(Long userId, Info request) {
        User user = userQueryHandler.findByIdAndStatus(userId, Status.ACTIVE);
        user.updateProfile(
                request.getImageUrl(),
                request.getName(),
                request.getBirth(),
                request.getGender(),
                request.getIntroduce(),
                request.getPersonalities()
        );
        return UserUpdateResponse.of(user.getProfile());
    }
}
