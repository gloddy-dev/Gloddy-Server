package com.gloddy.server.auth.domain.service;

import com.gloddy.server.user.domain.vo.Country;
import com.gloddy.server.user.domain.vo.Profile;
import org.springframework.stereotype.Component;

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
                .country(new Country(request.getCountryName(), request.getCountryImage()))
                .build();
    }
}
