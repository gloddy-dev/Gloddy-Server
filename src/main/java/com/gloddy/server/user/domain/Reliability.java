package com.gloddy.server.user.domain;

import com.gloddy.server.user.domain.vo.ReliabilityLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import jakarta.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@Table(name = "reliability")
public class Reliability {
    private static final ReliabilityLevel INIT_LEVEL = ReliabilityLevel.HOOD;
    private static final Long INIT_SCORE = 0L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long score;

    @Enumerated(EnumType.STRING)
    private ReliabilityLevel level;

    public Reliability() {
        this.score = INIT_SCORE;
        this.level = INIT_LEVEL;
    }

    public void updateLevel(ReliabilityLevel level) {
        this.level = level;
    }

    public void updateScore(Long score) {
        this.score = score;
    }

    public void upgrade(Long reflectScore) {
        reflectScore(reflectScore);
        this.level = this.level.upgradeLevel(this.score);
    }

    private void reflectScore(Long reflectScore) {
        long newScore = score + reflectScore;
        if (newScore >= INIT_SCORE) {
            this.score = newScore;
            return;
        }
        this.score = INIT_SCORE;
    }
}
