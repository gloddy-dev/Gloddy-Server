package com.gloddy.server.group.domain.vo;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Embeddable
public class GroupMemberVOs {

    @OneToMany(cascade = CascadeType.ALL)
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

    public int getSize() {
        return this.groupMemberVOS.size();
    }

    public boolean existByUserId(Long userId) {
        return this.groupMemberVOS.stream()
                .anyMatch(userGroupVO -> userGroupVO.getUserId().equals(userId));
    }
}
