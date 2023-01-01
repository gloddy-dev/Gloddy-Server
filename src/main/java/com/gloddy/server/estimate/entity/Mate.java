package com.gloddy.server.estimate.entity;

import com.gloddy.server.auth.entity.User;
import lombok.Builder;

import javax.persistence.*;

@Entity
@Table(name = "mate")
public class Mate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "mate_id")
    private Long mateId;

    @Column(name = "selection_reason", columnDefinition = "longtext")
    private String selectionReason;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Mate(Long mateId, String selectionReason, User user) {
        this.mateId = mateId;
        this.selectionReason = selectionReason;
        this.user = user;
    }
}
