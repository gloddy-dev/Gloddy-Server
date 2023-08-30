package com.gloddy.server.myGroup.read.domainService;

import com.gloddy.server.apply.domain.Apply;
import com.gloddy.server.apply.domain.handler.ApplyQueryHandler;
import com.gloddy.server.apply.domain.vo.Status;
import com.gloddy.server.group.domain.Group;
import com.gloddy.server.myGroup.read.dto.MyGroupResponse;
import com.gloddy.server.myGroup.read.util.MyGroupDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import static java.util.Comparator.*;
import static java.util.stream.Collectors.*;

@Component
@RequiredArgsConstructor
public class WaitingGroupGetExecutor {

    private final ApplyQueryHandler applyQueryHandler;

    public MyGroupResponse.Waiting getWaitingGroups(Long userId) {
        List<Apply> waitApplies = applyQueryHandler.findAllByUserIdAndStatus(userId, Status.WAIT);

        return waitApplies.stream()
                .sorted(applyCreatedAtDesc())
                .map(Apply::getGroup)
                .filter(this::isNotEndGroup)
                .map(MyGroupDtoMapper::mapToWaitingOne)
                .collect(collectingAndThen(toList(), MyGroupResponse.Waiting::new));
    }

    private boolean isNotEndGroup(Group group) {
        return group.getDateTime().getStartDateTime().isAfter(LocalDateTime.now());
    }

    private Comparator<? super Apply> applyCreatedAtDesc() {
        return comparing(Apply::getCreatedAt).reversed();
    }
}
