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

    public static String dateTimeToString(LocalDateTime date, String pattern) {
        return Optional.ofNullable(date)
                .orElse(now())
                .format(ofPattern(pattern));
    }

    public static String dateToString(LocalDate date, String pattern) {
        return Optional.ofNullable(date)
                .orElse(LocalDate.now())
                .format(ofPattern(pattern));
    }

    public static String dateToStringForGroupPreview(LocalDateTime dateTime) {
        String timePattern = "hh:mma";
        String time = dateTime.format(ofPattern(timePattern).withLocale(Locale.US));

        String datePattern = "yyyy.MM.dd";
        String date = dateTime.format(ofPattern(datePattern));

        DayOfWeek dayOfWeek = dateTime.getDayOfWeek();
        String day = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.US);
        return date + " " + day + " " + time;
    }
}
