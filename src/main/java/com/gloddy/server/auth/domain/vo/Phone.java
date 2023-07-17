package com.gloddy.server.auth.domain.vo;

import com.gloddy.server.auth.exception.InValidPhoneNumberException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static org.springframework.util.StringUtils.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
@EqualsAndHashCode(of = {"phoneNumber"})
public class Phone {

    private static final String DELIMITER = "-";

    @Column(name = "phone_number")
    private String phoneNumber;

    public Phone(String phone) {
        if (!isValid(phone)) {
            throw new InValidPhoneNumberException();
        }
        String[] split = phone.split(DELIMITER);
        this.phoneNumber = phone;
    }

    private boolean isValid(String phone) {
        return hasText(phone) && phone.split(DELIMITER).length == 3;
    }

    @Override
    public String toString() {
        return phoneNumber;
    }
}
