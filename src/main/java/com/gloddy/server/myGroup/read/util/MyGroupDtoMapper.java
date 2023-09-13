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

    public static MyGroupResponse.Waiting.One mapToWaitingOne(Group group) {
        return new MyGroupResponse.Waiting.One(
                mapToGroupInfo(group)
        );
    }

    public static MyGroupResponse.Rejected.One mapToRejectedOne(Long applyId, Group group) {
        return new MyGroupResponse.Rejected.One(
                applyId,
                mapToGroupInfo(group)
        );
    }

    public static MyGroupResponse.NotEstimated.One mapToNotEstimatedOne(Long userId, Group group) {
        return new MyGroupResponse.NotEstimated.One(
                group.getCaptain().getId().equals(userId),
                mapToGroupInfo(group)
        );
    }

    public static MyGroupResponse.GroupInfo mapToGroupInfo(Group group) {
        return new MyGroupResponse.GroupInfo(
                group.getId(),
                group.getImageUrl(),
                group.getTitle(),
                group.getContent(),
                group.getMemberCount(),
                group.getMaxUser(),
                group.getPlace().getId(),
                group.getPlace().getName(),
                group.getPlace().getAddress(),
                group.getMeetDate().toString(),
                group.getStartDateTime().toLocalTime().toString(),
                group.getEndDateTime().toLocalTime().toString()
        );
    }
}
