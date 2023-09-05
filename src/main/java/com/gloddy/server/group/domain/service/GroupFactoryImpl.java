package com.gloddy.server.group.domain.service;

import com.gloddy.server.auth.domain.User;
import com.gloddy.server.group.domain.Group;
import com.gloddy.server.group.domain.dto.GroupRequest;
import com.gloddy.server.group.domain.vo.GroupDateTime;
import com.gloddy.server.group.domain.vo.GroupPlace;
import com.gloddy.server.group.exception.GroupTimeInvalidException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.gloddy.server.core.utils.DateTimeUtils.concatDateAndTime;
import static com.gloddy.server.core.utils.DateTimeUtils.stringToLocalTime;

@Component
public class GroupFactoryImpl implements GroupFactory {

    @Override
    public Group getGroupFrom(User captain, GroupRequest.Create request) {
        GroupDateTime dateTime = getGroupDateTime(
                request.getMeetDate(),
                request.getStartTime()
        );

        GroupPlace place = getGroupPlace(
                request.getPlaceName(),
                request.getPlaceAddress(),
                request.getPlaceUrl(),
                request.getPlaceLatitude(),
                request.getPlaceLongitude()
        );

        return Group.builder()
                .captain(captain)
                .imageUrl(request.getImageUrl())
                .title(request.getTitle())
                .content(request.getContent())
                .dateTime(dateTime)
                .place(place)
                .maxUser(request.getMaxUser())
                .school(captain.getSchool())
                .build();
    }

    private GroupDateTime getGroupDateTime(LocalDate meetDate, String startTime) {
        LocalDateTime startDateTime = concatDateAndTime(meetDate, stringToLocalTime(startTime));
        validateGroupDateTime(startDateTime);
        return new GroupDateTime(startDateTime);
    }

    private GroupPlace getGroupPlace(String name, String address, String url, String latitude, String longitude) {
        return GroupPlace.builder()
                .name(name)
                .address(address)
                .url(url)
                .latitude(new BigDecimal(latitude))
                .longitude(new BigDecimal(longitude))
                .build();
    }

    private void validateGroupDateTime(LocalDateTime startDateTime) {
        LocalDateTime now = LocalDateTime.now();

        if (startDateTime.isBefore(now)) {
            throw new GroupTimeInvalidException();
        }
    }
}
