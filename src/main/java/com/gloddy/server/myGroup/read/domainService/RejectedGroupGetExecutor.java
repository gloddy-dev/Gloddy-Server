package com.gloddy.server.myGroup.read.domainService;

import com.gloddy.server.apply.domain.Apply;
import com.gloddy.server.apply.domain.handler.ApplyQueryHandler;
import com.gloddy.server.apply.domain.vo.Status;
import com.gloddy.server.myGroup.read.dto.MyGroupResponse;
import com.gloddy.server.myGroup.read.util.MyGroupDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

@Component
@RequiredArgsConstructor
public class RejectedGroupGetExecutor {

    private final ApplyQueryHandler applyQueryHandler;

    public MyGroupResponse.Rejected getRejectedGroups(Long userId) {
        List<Apply> refusedApply = applyQueryHandler.findAllByUserIdAndStatus(userId, Status.REFUSE);

        return refusedApply.stream()
                .sorted(applyUpdatedAttDesc())
                .filter(this::isNotCheckRejected)
                .map(apply -> MyGroupDtoMapper.mapToRejectedOne(apply.getId(), apply.getGroup()))
                .collect(Collectors.collectingAndThen(Collectors.toList(), MyGroupResponse.Rejected::new));
    }

    private Comparator<? super Apply> applyUpdatedAttDesc() {
        return comparing(Apply::getUpdatedAt).reversed();
    }

    private boolean isNotCheckRejected(Apply apply) {
        return !apply.isCheckRejected();
    }
}
