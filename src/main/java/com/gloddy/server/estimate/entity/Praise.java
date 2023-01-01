package com.gloddy.server.estimate.entity;

import com.gloddy.server.auth.entity.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "praise")
public class Praise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "praise")
    private User user;

    @Column(name = "total_calm_count", nullable = false)
    private Integer totalCalmCount;

    @Column(name = "total_kind_count", nullable = false)
    private Integer totalKindCount;

    @Column(name = "total_active_count", nullable = false)
    private Integer totalActiveCount;

    @Column(name = "total_humor_count", nullable = false)
    private Integer totalHumorCount;

    @Column(name = "total_absence_count", nullable = false)
    private Integer totalAbsenceCount;

    public void plusCalmCount() {
        totalCalmCount++;
    }
    public void plusKindCount() {
        totalKindCount++;
    }
    public void plusActiveCount() {
        totalActiveCount++;
    }
    public void plusHumorCount() {
        totalHumorCount++;
    }

    public void plusAbsenceCount() {
        totalAbsenceCount++;
    }
}
