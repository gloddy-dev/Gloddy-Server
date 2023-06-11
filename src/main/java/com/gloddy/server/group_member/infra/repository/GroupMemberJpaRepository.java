package com.gloddy.server.group_member.infra.repository;

import com.gloddy.server.group_member.domain.GroupMember;
import com.gloddy.server.group_member.infra.repository.custom.GroupMemberJpaRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GroupMemberJpaRepository extends JpaRepository<GroupMember, Long>, GroupMemberJpaRepositoryCustom {
    Optional<GroupMember> findByUserIdAndGroupId(Long userId, Long groupId);

    GroupMember findFirstByOrderByIdDesc();

    List<GroupMember> findAllByGroupId(Long groupId);

    List<GroupMember> findByUserIdInAndGroupId(List<Long> userIds, Long groupId);
}
