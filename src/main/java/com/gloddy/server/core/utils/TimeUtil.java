package com.gloddy.server.core.utils;

import java.time.LocalDate;
import java.time.Period;

public class TimeUtil {

    public static int calculateAge(LocalDate birth) {
        // 생년월일과 현재 날짜를 LocalDate 객체로 생성
        LocalDate currentDate = LocalDate.now();

        // 두 날짜 사이의 차이를 계산하여 나이를 계산
        return Period.between(birth, currentDate).getYears();
    }
}
