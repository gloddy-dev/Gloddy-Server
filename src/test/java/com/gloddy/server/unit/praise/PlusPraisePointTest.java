package com.gloddy.server.unit.praise;

import com.gloddy.server.common.praise.PraiseDomainTest;
import com.gloddy.server.user.domain.Praise;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Praise Domain Unit Test - PlusPraisePointTest ")
public class PlusPraisePointTest extends PraiseDomainTest {

    @Test
    @DisplayName("칭찬 포인트 플러스 - 친절")
    void 친절_plusPraisePointTest() {
        Praise praise = getInitPraise();
        praise.plusKindCount();

        assertThat(praise.getTotalKindCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("칭찬 포인트 플러스 - 차분")
    void plusPraisePointTest() {
        Praise praise = getInitPraise();
        praise.plusCalmCount();

        assertThat(praise.getTotalCalmCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("칭찬 포인트 플러스 - 적극")
    void 적극_plusPraisePointTest() {
        Praise praise = getInitPraise();
        praise.plusActiveCount();

        assertThat(praise.getTotalActiveCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("칭찬 포인트 플러스 - 유머")
    void 유머_plusPraisePointTest() {
        Praise praise = getInitPraise();
        praise.plusHumorCount();

        assertThat(praise.getTotalHumorCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("칭찬 포인트 플러스 - 불참")
    void 불참_plusPraisePointTest() {
        Praise praise = getInitPraise();
        praise.plusAbsenceCount();

        assertThat(praise.getTotalAbsenceCount()).isEqualTo(1);
    }
}
