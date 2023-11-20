package com.gloddy.server.user.domain.dto;

import com.gloddy.server.user.domain.vo.Profile;
import com.gloddy.server.user.domain.vo.kind.Gender;
import com.gloddy.server.user.domain.vo.kind.Personality;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
public class UserUpdateResponse {
    private String imageUrl;
    private String name;
    private LocalDate birth;
    private Gender gender;
    private String introduce;
    private List<Personality> personality;

    public static UserUpdateResponse of(Profile profile) {
        return new UserUpdateResponse(
                profile.getImageUrl(),
                profile.getNickname(),
                profile.getBirth(),
                profile.getGender(),
                profile.getIntroduce(),
                profile.getPersonalities()
        );
    }
}
