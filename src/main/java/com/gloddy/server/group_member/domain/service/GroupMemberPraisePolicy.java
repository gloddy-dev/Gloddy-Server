package com.gloddy.server.group_member.domain.service;

import com.gloddy.server.group_member.domain.GroupMember;
import com.gloddy.server.group_member.domain.handler.GroupMemberQueryHandler;
import com.gloddy.server.group_member.exception.InvalidRequestGroupMemberPraiseException;
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

        List<GroupMember> allGroupMembers = groupMemberQueryHandler.findAllByGroupId(groupId);

        List<Long> userIds = getUserIdsFromRequest(praiseInfos, estimatorUserId);
        List<GroupMember> findGroupMembers = groupMemberQueryHandler.findAllByUserIdInAndGroupId(userIds, groupId);

        validateIsAllGroupMemberPraised(allGroupMembers, findGroupMembers);
    }

    private void validateIsAllGroupMemberPraised(List<GroupMember> allGroupMembers, List<GroupMember> findGroupMembers) {
        if (!allGroupMembers.equals(findGroupMembers)) {
            throw new InvalidRequestGroupMemberPraiseException();
        }
    }

    private List<Long> getUserIdsFromRequest(List<PraiseInfo> praiseInfos, Long estimatorUserId) {
        List<Long> userIds = getUserIdsFromPraiseInfos(praiseInfos);
        userIds.add(estimatorUserId);
        return userIds;
    }

    private List<Long> getUserIdsFromPraiseInfos(List<PraiseInfo> praiseInfos) {
        return praiseInfos.stream()
                .map(PraiseInfo::getUserId)
                .collect(Collectors.toList());
    }
}
