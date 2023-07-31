package com.gloddy.server.user.application;

import com.gloddy.server.auth.domain.User;
import com.gloddy.server.auth.domain.vo.Profile;
import com.gloddy.server.user.domain.dto.UserUpdateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.gloddy.server.user.domain.dto.UserUpdateRequest.*;

@Service
@RequiredArgsConstructor
public class UserUpdateService {
    private final UserFindService userFindService;

    @Transactional
    public UserUpdateResponse update(Long userId, Info request) {
        User user = userFindService.findById(userId);
        Profile profile = user.getProfile();

        profile.setProfile(
                request.getImageUrl(),
                request.getName(),
                request.getBirth(),
                request.getGender(),
                request.getIntroduce(),
                request.getPersonalities()
        );

        return UserUpdateResponse.of(profile);
    }
}
