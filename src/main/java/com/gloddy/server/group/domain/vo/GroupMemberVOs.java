package com.gloddy.server.group.domain.vo;

import com.gloddy.server.group_member.domain.GroupMember;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Embeddable
public class GroupMemberVOs {

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(
            name = "group_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_user_group_vo_to_group")
    )
    private List<GroupMemberVO> groupMemberVOS = new ArrayList<>();

    public static GroupMemberVOs empty() {
        return new GroupMemberVOs();
    }

    public void addUserGroupVo(GroupMemberVO groupMemberVO) {
        this.groupMemberVOS.add(groupMemberVO);
    }

    public List<GroupMemberVO> getGroupMemberVosWithOut(Long userId) {
        return this.groupMemberVOS.stream()
                .filter(vo -> !vo.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    public int getSize() {
        return this.groupMemberVOS.size();
    }

    public boolean existByUserId(Long userId) {
        return this.groupMemberVOS.stream()
                .anyMatch(userGroupVO -> userGroupVO.getUserId().equals(userId));
    }
}
