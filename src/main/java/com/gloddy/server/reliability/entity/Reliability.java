package com.gloddy.server.reliability.entity;

import com.gloddy.server.reliability.entity.vo.ReliabilityLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Reliability {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long userId;

    private Long score;

    @Enumerated(EnumType.STRING)
    private ReliabilityLevel level;

    @Builder
    public Reliability(Long userId, Long score, ReliabilityLevel level) {
        this.userId = userId;
        this.score = score;
        this.level = level;
    }
}
