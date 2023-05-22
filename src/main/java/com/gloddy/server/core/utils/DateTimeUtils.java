package com.gloddy.server.core.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import static java.time.LocalDateTime.*;
import static java.time.format.DateTimeFormatter.*;

public class DateTimeUtils {

    public static LocalTime StringToLocalTime(String timeString) {
        return LocalTime.parse(timeString);
    }

    public static LocalDateTime concatDateAndTime(LocalDate date, LocalTime time) {
        return of(date, time);
    }

    public static String dateTimeToString(LocalDateTime date) {
        return Optional.ofNullable(date)
                .orElse(now())
                .format(ofPattern("yyyy-MM-dd HH:mm"));
    }
}
