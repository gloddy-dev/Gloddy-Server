package com.gloddy.server.group.entity.embedded;

import com.gloddy.server.group.entity.UserGroup;
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

    public int getSize() {
        return this.userGroups.size();
    }
}
