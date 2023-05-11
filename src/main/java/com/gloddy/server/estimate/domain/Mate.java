package com.gloddy.server.estimate.domain;

import com.gloddy.server.auth.domain.User;
import com.gloddy.server.core.entity.common.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "mate")
public class Mate extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "mate_id", nullable = false)
    private Long mateId;

    @Column(name = "selection_reason", columnDefinition = "longtext", nullable = false)
    private String selectionReason;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Builder
    public Mate(Long mateId, String selectionReason, User user) {
        this.mateId = mateId;
        this.selectionReason = selectionReason;
        this.user = user;
    }
}
