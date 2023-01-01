package com.gloddy.server.estimate.entity;

import com.gloddy.server.auth.entity.User;
import com.gloddy.server.estimate.entity.embedded.Mate;
import com.gloddy.server.estimate.entity.embedded.Praise;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "estimate")
public class Estimate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "estimate")
    private User user;

    @Embedded
    private Praise praise;

    @Embedded
    private Mate mate;
}
