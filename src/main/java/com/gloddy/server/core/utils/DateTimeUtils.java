package com.gloddy.server.core.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class DateTimeUtils {

    public static LocalTime StringToLocalTime(String timeString) {
        return LocalTime.parse(timeString);
    }

    public static LocalDateTime concatDateAndTime(LocalDate date, LocalTime time) {
        return LocalDateTime.of(date, time);
    }
}
