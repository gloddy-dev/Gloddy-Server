package com.gloddy.server.auth.domain.service;

import com.gloddy.server.auth.domain.vo.Profile;
import com.gloddy.server.auth.domain.vo.kind.Personality;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.gloddy.server.auth.domain.dto.AuthRequest.*;

@Component
public class UserProfileFactory {

    public Profile getProfile(SignUp request) {
        return Profile.builder()
                .imageUrl(request.getImageUrl())
                .nickname(request.getNickname())
                .birth(request.getBirth())
                .gender(request.getGender())
                .introduce(null)
                .personalities(request.getPersonalities())
                .build();
    }
}
