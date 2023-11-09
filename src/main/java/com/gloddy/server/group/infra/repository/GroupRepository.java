package com.gloddy.server.group.infra.repository;

import com.gloddy.server.batch.group.repository.IGroupRepository;
import com.gloddy.server.group.domain.Group;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GroupRepository implements IGroupRepository {
    private final GroupJpaRepository groupJpaRepository;

    @Override
    public List<Group> findApproachingGroups(LocalDateTime currentDateTime) {
        LocalDateTime dateTime = currentDateTime.plusHours(1);
        return groupJpaRepository.findAllByStartDateTimeEqFetchGroupMemberVos(dateTime);
    }

    @Override
    public List<Group> findEndGroups(LocalDateTime currentDateTime) {
        return groupJpaRepository.findAllByEndDateTimeEqFetchGroupMemberVos(currentDateTime);
    }
}
