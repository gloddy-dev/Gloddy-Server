package com.gloddy.server.estimate.entity.embedded;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Praise {

    @Column(name = "total_calm_count")
    private Integer totalCalmCount;

    @Column(name = "total_kind_count")
    private Integer totalKindCount;

    @Column(name = "total_active_count")
    private Integer totalActiveCount;

    @Column(name = "total_humor_count")
    private Integer totalHumorCount;

    @Column(name = "total_absence_count")
    private Integer totalAbsenceCount;
}
