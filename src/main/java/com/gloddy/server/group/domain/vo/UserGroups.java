package com.gloddy.server.group.domain.vo;

import com.gloddy.server.user_group.domain.UserGroup;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Embeddable
public class UserGroups {

    @OneToMany(mappedBy = "group")
    public List<UserGroup> userGroups = new ArrayList<>();

    public static UserGroups empty() {
        return new UserGroups();
    }

    public void addUserGroups(UserGroup userGroup) {
        this.userGroups.add(userGroup);
    }

    public int getSize() {
        return this.userGroups.size();
    }
}
