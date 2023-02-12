package com.gloddy.server.core.dto;

import com.gloddy.server.auth.entity.User;
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
        return new UserInfoDto(user.getName(), user.getImageUrl());
    }
}
