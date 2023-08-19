package com.gloddy.server.group.domain.service;

import com.gloddy.server.auth.domain.User;
import com.gloddy.server.group.domain.Group;
import org.springframework.data.domain.Page;

import static com.gloddy.server.group.domain.dto.GroupResponse.*;

public class GroupDtoMapper {

    public static Page<GetGroup> mapToGetGroupPageFrom(Page<Group> groupPage) {
        return groupPage.map(GetGroup::from);
    }

    public static GetGroupDetail mapToGetGroupDetailFrom(User user, Group group, GroupChecker groupChecker) {
        return new GetGroupDetail(
                groupChecker.isMyGroup(user, group),
                groupChecker.isGroupCaptain(user, group),
                group.getTitle(),
                group.getImageUrl(),
                group.getContent(),
                group.getMaxUser(),
                group.getMemberCount(),
                group.getMeetDate().toString(),
                group.getStartDateTime().toLocalTime().toString(),
                group.getEndDateTime().toLocalTime().toString(),
                group.getPlace().getName(),
                group.getPlace().getAddress(),
                group.getPlace().getLatitude().toString(),
                group.getPlace().getLongitude().toString()
        );
    }

}
