package com.gloddy.server.group.domain.vo;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UserGroupVOs {

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(
            name = "group_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_user_group_vo_to_group")
    )
    private List<UserGroupVO> userGroupVOs = new ArrayList<>();

    public static UserGroupVOs empty() {
        return new UserGroupVOs();
    }

    public void addUserGroupVo(UserGroupVO userGroupVO) {
        this.userGroupVOs.add(userGroupVO);
    }

    public int getSize() {
        return this.userGroupVOs.size();
    }
}
