package com.gloddy.server.myGroup.read.util;

import com.gloddy.server.group.domain.Group;
import com.gloddy.server.myGroup.read.dto.MyGroupResponse;

public class MyGroupDtoMapper {

    public static MyGroupResponse.Participating.One mapToParticipatingOne(boolean isNew, Group group) {
        return new MyGroupResponse.Participating.One(
                isNew,
                mapToGroupInfo(group)
        );
    }

    public static MyGroupResponse.Hosting.One matToHostingOne(boolean isExistNewApply, Group group) {
        return new MyGroupResponse.Hosting.One(
                isExistNewApply,
                mapToGroupInfo(group)
        );
    }

    private static MyGroupResponse.GroupInfo mapToGroupInfo(Group group) {
        return new MyGroupResponse.GroupInfo(
                group.getId(),
                group.getImageUrl(),
                group.getTitle(),
                group.getContent(),
                group.getMemberCount(),
                group.getMaxUser(),
                group.getPlace().getName(),
                group.getPlace().getAddress(),
                group.getMeetDate().toString(),
                group.getStartDateTime().toLocalTime().toString(),
                group.getEndDateTime().toLocalTime().toString()
        );
    }
}
