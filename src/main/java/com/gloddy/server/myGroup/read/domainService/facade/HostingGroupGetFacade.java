package com.gloddy.server.myGroup.read.domainService.facade;

import com.gloddy.server.group.domain.Group;
import com.gloddy.server.myGroup.read.domainService.HostingGroupExistNewApplyChecker;
import com.gloddy.server.myGroup.read.domainService.HostingGroupGetExecutor;
import com.gloddy.server.myGroup.read.dto.MyGroupResponse;
import com.gloddy.server.myGroup.read.util.MyGroupDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.*;

@Component
@RequiredArgsConstructor
public class HostingGroupGetFacade {

    private final HostingGroupGetExecutor hostingGroupGetExecutor;
    private final HostingGroupExistNewApplyChecker hostingGroupExistNewApplyChecker;

    public MyGroupResponse.Hosting getHostingGroups(Long userId) {
        List<Group> hostingGroups = hostingGroupGetExecutor.getHostingGroups(userId);

        return hostingGroups.stream()
                .map(group -> {
                    boolean isExistNewApply = hostingGroupExistNewApplyChecker.isExistNewApply(group.getId());
                    return MyGroupDtoMapper.matToHostingOne(isExistNewApply, group);
                })
                .collect(collectingAndThen(toList(), MyGroupResponse.Hosting::new));
    }
}
