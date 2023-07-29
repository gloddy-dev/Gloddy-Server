package com.gloddy.server.auth.domain.service;

import com.gloddy.server.auth.domain.User;
import com.gloddy.server.auth.domain.vo.Phone;
import com.gloddy.server.auth.domain.vo.School;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.gloddy.server.auth.domain.dto.AuthRequest.*;

@Component
@RequiredArgsConstructor
public class UserFactory {

    private final UserSignUpPolicy userSignUpPolicy;

    public User getUser(SignUp request) {
        userSignUpPolicy.validate(request.getPhoneNumber());

        Phone phone = getPhone(request.getPhoneNumber());
        School school = getSchool(request.getSchoolInfo());
        return User.builder()
                .phone(phone)
                .school(school)
                .imageUrl(request.getImageUrl())
                .nickname(request.getNickname())
                .birth(request.getBirth())
                .gender(request.getGender())
                .personalities(request.getPersonalities())
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
