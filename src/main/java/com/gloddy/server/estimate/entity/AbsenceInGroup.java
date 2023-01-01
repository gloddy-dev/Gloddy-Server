package com.gloddy.server.estimate.entity;

import com.gloddy.server.auth.entity.User;
import com.gloddy.server.estimate.entity.embedded.PraiseInGroup;
import com.gloddy.server.group.entity.Group;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "absence_in_group")
public class AbsenceInGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "absence_count")
    private Integer absenceCount;

    @Column(name = "absence")
    private Boolean absence;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

    public void plusAbsenceCount() {
        this.absenceCount++;
    }
}
