package com.gloddy.server.group_member.infra.repository.custom;

import com.gloddy.server.auth.domain.User;
import com.gloddy.server.group.domain.Group;
import com.gloddy.server.group_member.domain.GroupMember;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GroupMemberJpaRepositoryCustom {

    List<Group> findExpectedGroupsByUser(User user);

    Page<GroupMember> findParticipatedGroupsByUser(User user, Pageable pageable);

    List<GroupMember> findUserGroupsToPraiseByUserIdInAndGroupId(List<Long> userIds, Long groupId);

    List<GroupMember> findAllByGroupIdFetchUserAndGroup(Long groupId);
}
