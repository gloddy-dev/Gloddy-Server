package com.gloddy.server.auth.domain.vo;

import com.gloddy.server.authEmail.exception.InvalidEmailException;
import jakarta.persistence.Column;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class School {

    private static final String EMAIL_DELIMITER = "@";
    private static final String STUDENT_EMAIL_FORM = "ac.kr";

    @Column(name = "is_certified_student")
    private boolean isCertifiedStudent;

    @Column(name = "school")
    private String school;

    @Column(name = "email")
    private String email;

    public static School createNoCertified(String school) {
        return new School(false, school, null);
    }

    public static School createCertified(String school, String email) {
        validateStudentEmail(email);
        return new School(true, school, email);
    }

    private static void validateStudentEmail(String email) {
        String[] split = email.split(EMAIL_DELIMITER);

        if (!(split.length == 2) && !(split[1].contains(STUDENT_EMAIL_FORM))) {
            throw new InvalidEmailException();
        }
    }
}
