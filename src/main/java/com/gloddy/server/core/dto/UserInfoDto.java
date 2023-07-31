package com.gloddy.server.core.dto;

import com.gloddy.server.auth.domain.User;
import com.gloddy.server.auth.domain.vo.Profile;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDto {
    private String name;
    private String imageUrl;

    public static UserInfoDto from(User user) {
        Profile profile = user.getProfile();
        return new UserInfoDto(profile.getNickname(), profile.getImageUrl());
    }
}
