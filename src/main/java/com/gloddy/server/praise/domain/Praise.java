package com.gloddy.server.praise.domain;

import com.gloddy.server.auth.domain.User;
import com.gloddy.server.praise.event.PraiseCountUpdateEvent;
import com.gloddy.server.praise.domain.vo.PraiseValue;
import com.gloddy.server.praise.event.producer.PraiseEventProducer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "praise")
public class Praise {

    private static final int INIT = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
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

    public static Praise empty() {
        return new Praise();
    }

    public void init(User user) {
        this.totalCalmCount = INIT;
        this.totalKindCount = INIT;
        this.totalActiveCount = INIT;
        this.totalHumorCount = INIT;
        this.totalAbsenceCount = INIT;
        this.user = user;
        user.setPraise(this);
    }

    public void plusCount(PraiseValue praiseValue, PraiseEventProducer praiseEventProducer) {
        if (praiseValue.isHumor()) {
            plusHumorCount();
            praiseEventProducer.produceEvent(new PraiseCountUpdateEvent(this.user.getId(), false));
        } else if (praiseValue.isActive()) {
            plusActiveCount();
            praiseEventProducer.produceEvent(new PraiseCountUpdateEvent(this.user.getId(), false));
        } else if (praiseValue.isCalm()) {
            plusCalmCount();
            praiseEventProducer.produceEvent(new PraiseCountUpdateEvent(this.user.getId(), false));
        } else if (praiseValue.isKind()) {
            plusKindCount();
            praiseEventProducer.produceEvent(new PraiseCountUpdateEvent(this.user.getId(), false));
        } else {
            plusAbsenceCount();
            praiseEventProducer.produceEvent(new PraiseCountUpdateEvent(this.user.getId(), true));
        }
    }

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
