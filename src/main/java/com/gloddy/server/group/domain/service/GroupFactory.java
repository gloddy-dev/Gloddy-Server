package com.gloddy.server.group.domain.service;

import com.gloddy.server.auth.domain.User;
import com.gloddy.server.group.domain.Group;
import com.gloddy.server.group.domain.dto.GroupRequest;
import com.gloddy.server.group.domain.vo.GroupDateTime;
import com.gloddy.server.group.domain.vo.GroupPlace;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class GroupFactory {

    public Group getGroupFrom(User captain, GroupRequest.Create request) {
        GroupDateTime dateTime = getGroupDateTime(request.getMeetDate(), request.getStartTime(), request.getEndTime());
        GroupPlace place = getGroupPlace(request.getPlace(), request.getPlace_latitude(), request.getPlace_longitude());
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

    private GroupDateTime getGroupDateTime(LocalDate meetDate, String startTime, String endTime) {
        return GroupDateTime.createFrom(meetDate, startTime, endTime);
    }

    private GroupPlace getGroupPlace(String name, String latitude, String longitude) {
        return new GroupPlace(name, new BigDecimal(latitude), new BigDecimal(longitude));
    }
}
