package com.gloddy.server.group.domain.vo;

import com.gloddy.server.group.exception.GroupTimeInvalidException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.gloddy.server.core.utils.DateTimeUtils.*;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class GroupDateTime {

    @Column(name = "start_date_time")
    private LocalDateTime startDateTime;

    @Column(name = "end_date_time")
    private LocalDateTime endDateTime;

    public static GroupDateTime createFrom(LocalDate meetDate, String startTime, String endTime) {
        LocalDateTime startDateTime = concatDateAndTime(meetDate, stringToLocalTime(startTime));
        LocalDateTime endDateTime = concatDateAndTime(meetDate, stringToLocalTime(endTime));
        return new GroupDateTime(startDateTime, endDateTime);
    }

    public GroupDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
        this.endDateTime = startDateTime.plusDays(1);
    }
}
