package com.gloddy.server.user.application;

import com.gloddy.server.user.domain.User;
import com.gloddy.server.user.domain.vo.kind.Status;
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
        User user = userQueryHandler.findByIdFetch(userId);
        user.updateProfile(
                request.getImageUrl(),
                request.getName(),
                request.getBirth(),
                request.getGender(),
                request.getIntroduce(),
                request.getPersonalities(),
                request.getCountryName(),
                request.getCountryImage()
        );
        return UserUpdateResponse.of(user.getProfile());
    }
}
