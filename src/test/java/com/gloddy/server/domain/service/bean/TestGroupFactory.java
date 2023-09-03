package com.gloddy.server.domain.service.bean;

import com.gloddy.server.auth.domain.User;
import com.gloddy.server.group.domain.Group;
import com.gloddy.server.group.domain.dto.GroupRequest;
import com.gloddy.server.group.domain.service.GroupFactory;
import com.gloddy.server.group.domain.vo.GroupDateTime;
import com.gloddy.server.group.domain.vo.GroupPlace;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.gloddy.server.core.utils.DateTimeUtils.concatDateAndTime;
import static com.gloddy.server.core.utils.DateTimeUtils.stringToLocalTime;

@Component
@Primary
public class TestGroupFactory implements GroupFactory {
    @Override
    public Group getGroupFrom(User captain, GroupRequest.Create request) {
        GroupDateTime dateTime = getGroupDateTime(
                request.getMeetDate(),
                request.getStartTime()
        );

        GroupPlace place = getGroupPlace(
                request.getPlaceName(),
                request.getPlaceAddress(),
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
        return new GroupDateTime(startDateTime);
    }

    private GroupPlace getGroupPlace(String name, String address, String latitude, String longitude) {
        return GroupPlace.builder()
                .name(name)
                .address(address)
                .latitude(new BigDecimal(latitude))
                .longitude(new BigDecimal(longitude))
                .build();
    }
}
