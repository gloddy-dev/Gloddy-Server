package com.gloddy.server.estimate.entity;

import com.gloddy.server.auth.entity.User;
import com.gloddy.server.estimate.entity.embedded.Mate;
import com.gloddy.server.estimate.entity.embedded.PraiseInGroup;
import com.gloddy.server.group.entity.Group;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "estimate_in_group")
public class EstimateInGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private PraiseInGroup praiseInGroup;

    @Column(name = "absence_count")
    private Integer absenceCount;

    @Column(name = "absence")
    private Boolean absence;

    @Embedded
    private Mate mate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;
}
