package com.gloddy.server.group_member.domain.service;

import com.gloddy.server.group_member.domain.GroupMember;
import com.gloddy.server.group_member.domain.handler.GroupMemberQueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static com.gloddy.server.group_member.domain.dto.GroupMemberRequest.Estimate.*;

@Component
@RequiredArgsConstructor
public class GroupMemberPraisePolicy {

    private final GroupMemberQueryHandler groupMemberQueryHandler;

    public void validate(Long groupId, Long estimatorUserId, List<PraiseInfo> praiseInfos) {
        List<GroupMember> allMembersInGroup = groupMemberQueryHandler.findAllByGroupId(groupId);

        List<Long> allUserIdsFromPraiseInfos = getAllUserIds(praiseInfos);
        List<Long> allUserIds = merge(estimatorUserId, allUserIdsFromPraiseInfos);

        List<GroupMember> members = groupMemberQueryHandler.findAllByUserIdInAndGroupId(allUserIds, groupId);

        if (allMembersInGroup.equals(members)) {
            throw new RuntimeException();
        }
    }

    private List<Long> merge(Long estimatorUserId, List<Long> allUserIdsFromPraiseInfos) {
        allUserIdsFromPraiseInfos.add(estimatorUserId);
        return allUserIdsFromPraiseInfos;
    }

    private List<Long> getAllUserIds(List<PraiseInfo> praiseInfos) {
        return praiseInfos.stream()
                .map(PraiseInfo::getUserId)
                .collect(Collectors.toUnmodifiableList());
    }
}
