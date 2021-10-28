/*
 * Copyright (c) 2017 Atlanmod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package org.atlanmod.commons.cache;

import org.atlanmod.commons.AbstractTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import javax.annotation.ParametersAreNonnullByDefault;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

/**
 * A test-case that checks the behavior of {@link CacheStats}.
 */
@ParametersAreNonnullByDefault
class CacheStatsTest extends AbstractTest {

    private CacheStats stats;

    @BeforeEach
    void setUp() {
        stats = new CacheStats(30, 10, 15, 5, 2000, 2);
    }

    @Test
    void testConstructor() {
        assertThat(catchThrowable(() -> new CacheStats(0, 0, 0, 0, 0, 0)))
                .isNull();

        assertThat(catchThrowable(() -> new CacheStats(-1, 0, 0, 0, 0, 0)))
                .isInstanceOf(IllegalArgumentException.class);

        assertThat(catchThrowable(() -> new CacheStats(0, -1, 0, 0, 0, 0)))
                .isInstanceOf(IllegalArgumentException.class);

        assertThat(catchThrowable(() -> new CacheStats(0, 0, -1, 0, 0, 0)))
                .isInstanceOf(IllegalArgumentException.class);

        assertThat(catchThrowable(() -> new CacheStats(0, 0, 0, -1, 0, 0)))
                .isInstanceOf(IllegalArgumentException.class);

        assertThat(catchThrowable(() -> new CacheStats(0, 0, 0, 0, -1, 0)))
                .isInstanceOf(IllegalArgumentException.class);

        assertThat(catchThrowable(() -> new CacheStats(0, 0, 0, 0, 0, -1)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void testRequestCount() {
        assertThat(stats.requestCount()).isEqualTo(40);
    }

    @Test
    void testHitCount() {
        assertThat(stats.hitCount()).isEqualTo(30);
    }

    @Test
    void testHitRate() {
        assertThat(stats.hitRate()).isEqualTo(0.75);
    }

    @Test
    void testMissCount() {
        assertThat(stats.missCount()).isEqualTo(10);
    }

    @Test
    void testMissRate() {
        assertThat(stats.missRate()).isEqualTo(0.25);
    }

    @Test
    void testLoadCount() {
        assertThat(stats.loadCount()).isEqualTo(20);
    }

    @Test
    void testLoadSuccessCount() {
        assertThat(stats.loadSuccessCount()).isEqualTo(15);
    }

    @Test
    void testLoadFailureCount() {
        assertThat(stats.loadFailureCount()).isEqualTo(5);
    }

    @Test
    void testLoadFailureRate() {
        assertThat(stats.loadFailureRate()).isEqualTo(0.25);
    }

    @Test
    void testTotalLoadTime() {
        assertThat(stats.totalLoadTime()).isEqualByComparingTo(Duration.ofNanos(2000));
    }

    @Test
    void testAverageLoadPenalty() {
        assertThat(stats.averageLoadPenalty()).isEqualTo(100);
    }

    @Test
    void testEvictionCount() {
        assertThat(stats.evictionCount()).isEqualTo(2);
    }

    @Test
    void testMinus() {
        CacheStats result = stats.minus(stats);

        //noinspection EqualsReplaceableByObjectsCall
        assertThat(stats.equals(result)).isFalse();

        assertThat(stats.hashCode()).isNotEqualTo(result.hashCode());

        assertThat(result.requestCount()).isEqualTo(0);
        assertThat(result.hitCount()).isEqualTo(0);
        assertThat(result.hitRate()).isEqualTo(1.0);
        assertThat(result.missCount()).isEqualTo(0);
        assertThat(result.missRate()).isEqualTo(0.0);
        assertThat(result.loadCount()).isEqualTo(0);
        assertThat(result.loadSuccessCount()).isEqualTo(0);
        assertThat(result.loadFailureCount()).isEqualTo(0);
        assertThat(result.loadFailureRate()).isEqualTo(0.0);
        assertThat(result.totalLoadTime()).isEqualByComparingTo(Duration.ofNanos(0));
        assertThat(result.averageLoadPenalty()).isEqualTo(0);
        assertThat(result.evictionCount()).isEqualTo(0);
    }

    @Test
    void testPlus() {
        CacheStats result = stats.plus(stats);

        //noinspection EqualsReplaceableByObjectsCall
        assertThat(stats.equals(result)).isFalse();
        assertThat(stats.hashCode()).isNotEqualTo(result.hashCode());

        assertThat(result.requestCount()).isEqualTo(80);
        assertThat(result.hitCount()).isEqualTo(60);
        assertThat(result.hitRate()).isEqualTo(0.75);
        assertThat(result.missCount()).isEqualTo(20);
        assertThat(result.missRate()).isEqualTo(0.25);
        assertThat(result.loadCount()).isEqualTo(40);
        assertThat(result.loadSuccessCount()).isEqualTo(30);
        assertThat(result.loadFailureCount()).isEqualTo(10);
        assertThat(result.loadFailureRate()).isEqualTo(0.25);
        assertThat(result.totalLoadTime()).isEqualByComparingTo(Duration.ofNanos(4000));
        assertThat(result.averageLoadPenalty()).isEqualTo(100);
        assertThat(result.evictionCount()).isEqualTo(4);
    }

    @Test
    void testEquals() {
        //noinspection EqualsWithItself
        assertThat(stats.equals(stats)).isTrue();

        //noinspection ConstantConditions
        assertThat(stats.equals(null)).isFalse();

        //noinspection EqualsReplaceableByObjectsCall
        assertThat(stats.equals(new CacheStats(0, 10, 15, 5, 2000, 2))).isFalse();

        //noinspection EqualsReplaceableByObjectsCall
        assertThat(stats.equals(new CacheStats(30, 0, 15, 5, 2000, 2))).isFalse();

        //noinspection EqualsReplaceableByObjectsCall
        assertThat(stats.equals(new CacheStats(30, 10, 0, 5, 2000, 2))).isFalse();

        //noinspection EqualsReplaceableByObjectsCall
        assertThat(stats.equals(new CacheStats(30, 10, 15, 0, 2000, 2))).isFalse();

        //noinspection EqualsReplaceableByObjectsCall
        assertThat(stats.equals(new CacheStats(30, 10, 15, 5, 0, 2))).isFalse();

        //noinspection EqualsReplaceableByObjectsCall
        assertThat(stats.equals(new CacheStats(30, 10, 15, 5, 2000, 0))).isFalse();
    }
}