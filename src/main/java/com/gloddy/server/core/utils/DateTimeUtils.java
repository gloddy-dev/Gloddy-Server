package com.gloddy.server.core.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Optional;

import static java.time.LocalDateTime.*;
import static java.time.format.DateTimeFormatter.*;

public class DateTimeUtils {

    public static LocalTime stringToLocalTime(String timeString) {
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

    public static String dateToStringForGroupPreview(LocalDate date) {
        LocalDate localDate = Optional.ofNullable(date)
                .orElse(LocalDate.now());

        DayOfWeek dayOfWeek = localDate.getDayOfWeek();
        String day = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.US);
        return localDate + " " + day;
    }
}
