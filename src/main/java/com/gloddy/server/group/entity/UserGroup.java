package com.gloddy.server.group.entity;

import com.gloddy.server.auth.entity.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "user_group")
public class UserGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

    //나중에 스케줄링으로 처리하면 너무 편할 듯
    @Column(name = "is_end")
    private boolean isEnd;

    @Column(name = "is_praised")
    private boolean isPraised;

    public static UserGroup empty() {
        return new UserGroup();
    }

    public void init(User user, Group group) {
        this.user = user;
        this.group = group;
        this.isEnd = false;
        this.isPraised = false;
    }
}
