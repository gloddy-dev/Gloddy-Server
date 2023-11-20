package com.gloddy.server.auth.domain.service;

import com.gloddy.server.user.domain.User;
import com.gloddy.server.user.domain.vo.Phone;
import com.gloddy.server.user.domain.vo.Profile;
import com.gloddy.server.user.domain.vo.School;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.gloddy.server.auth.domain.dto.AuthRequest.*;

@Component
@RequiredArgsConstructor
public class UserFactory {

    private final UserSignUpPolicy userSignUpPolicy;
    private final UserProfileFactory userProfileFactory;

    public User getUser(SignUp request) {
        userSignUpPolicy.validate(request.getPhoneNumber());

        Phone phone = getPhone(request.getPhoneNumber());
        School school = getSchool(request.getSchoolInfo());
        Profile profile = userProfileFactory.getProfile(request);
        return User.builder()
                .phone(phone)
                .school(school)
                .profile(profile)
                .build();
    }

    private Phone getPhone(String phoneNumber) {
        return new Phone(phoneNumber);
    }

    private School getSchool(SignUp.School schoolInfo) {
        if (schoolInfo.isCertifiedStudent()) {
            return School.createCertified(schoolInfo.getSchool(), schoolInfo.getEmail());
        }
        return School.createNoCertified(schoolInfo.getSchool());
    }

}
