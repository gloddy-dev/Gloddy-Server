package com.gloddy.server.apply.domain;

import com.gloddy.server.apply.domain.vo.Status;
import com.gloddy.server.apply.event.producer.ApplyEventProducer;
import com.gloddy.server.user.domain.User;
import com.gloddy.server.core.entity.common.BaseTimeEntity;
import com.gloddy.server.core.event.GroupParticipateEvent;
import com.gloddy.server.group.domain.Group;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "apply")
public class Apply extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

    @Column(name = "content", columnDefinition = "longtext")
    private String content;

    @Column(name = "reason", columnDefinition = "longtext")
    private String reason;

    @Builder.Default
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status = Status.WAIT;

    @Builder.Default
    @Column(name = "is_check_rejected")
    private boolean isCheckRejected = false;

    public void approveApply(ApplyEventProducer applyEventProducer) {
        this.status = Status.APPROVE;
        applyEventProducer.produceEvent(new GroupParticipateEvent(this.user.getId(), this.group.getId()));
    }

    public Long getUserId() {
        return this.user.getId();
    }

    public void refuseApply() {
        this.status = Status.REFUSE;
    }

    public boolean isApproved() {
        return this.status.isApprove();
    }

    public void checkRejected() {
        if (this.status.equals(Status.REFUSE)) {
            this.isCheckRejected = true;
        } else {
            throw new RuntimeException("cant check Rejected");
        }
    }
}
