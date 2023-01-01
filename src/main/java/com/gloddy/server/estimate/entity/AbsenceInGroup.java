package com.gloddy.server.estimate.entity;

import com.gloddy.server.auth.entity.User;
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

    @Column(name = "absence_count", nullable = false)
    private Integer absenceCount;

    @Column(name = "absence", nullable = false)
    private Boolean absence;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    public void plusAbsenceCount() {
        this.absenceCount++;
    }

    public void absence() {
        this.absence = true;
    }
}
