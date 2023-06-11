package com.gloddy.server.apply.domain;

import com.gloddy.server.apply.domain.vo.Status;
import com.gloddy.server.auth.domain.User;
import com.gloddy.server.core.entity.common.BaseTimeEntity;
import com.gloddy.server.core.event.GroupParticipateEvent;
import com.gloddy.server.group.domain.Group;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;

import javax.persistence.*;

@Entity
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

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Builder
    public Apply(User user, Group group, String content, String reason) {
        this.user = user;
        this.group = group;
        this.content = content;
        this.reason = reason;
        this.status = Status.WAIT;
    }

    public void approveApply(ApplicationEventPublisher applicationEventPublisher) {
        this.status = Status.APPROVE;
        applicationEventPublisher.publishEvent(new GroupParticipateEvent(this.user.getId(), this.group.getId()));
    }

    public void refuseApply() {
        this.status = Status.REFUSE;
    }

    public boolean isApproved() {
        return this.status.isApprove();
    }
}
