/*
 * Copyright (c) 2017 Atlanmod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package org.atlanmod.commons.collect;

import org.atlanmod.commons.AbstractTest;

import org.junit.jupiter.api.Test;

import javax.annotation.ParametersAreNonnullByDefault;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * A test-case that checks the behavior of {@link Range}.
 */
@ParametersAreNonnullByDefault
class RangeTest extends AbstractTest {

    @Test
    void testOpen() {
        Range<Integer> r0 = Range.open(0, 2);

        assertThat(r0.contains(-1)).isFalse();
        assertThat(r0.contains(0)).isFalse();
        assertThat(r0.contains(1)).isTrue();
        assertThat(r0.contains(2)).isFalse();
        assertThat(r0.contains(3)).isFalse();
    }

    @Test
    void testClosed() {
        Range<Integer> r0 = Range.closed(0, 2);

        assertThat(r0.contains(-1)).isFalse();
        assertThat(r0.contains(0)).isTrue();
        assertThat(r0.contains(1)).isTrue();
        assertThat(r0.contains(2)).isTrue();
        assertThat(r0.contains(3)).isFalse();
    }

    @Test
    void testOpenClosed() {
        Range<Integer> r0 = Range.openClosed(0, 2);

        assertThat(r0.contains(-1)).isFalse();
        assertThat(r0.contains(0)).isFalse();
        assertThat(r0.contains(1)).isTrue();
        assertThat(r0.contains(2)).isTrue();
        assertThat(r0.contains(3)).isFalse();
    }

    @Test
    void testClosedOpen() {
        Range<Integer> r0 = Range.closedOpen(0, 2);

        assertThat(r0.contains(-1)).isFalse();
        assertThat(r0.contains(0)).isTrue();
        assertThat(r0.contains(1)).isTrue();
        assertThat(r0.contains(2)).isFalse();
        assertThat(r0.contains(3)).isFalse();
    }

    @Test
    void testSingleton() {
        Range<Integer> r0 = Range.singleton(1);

        assertThat(r0.contains(-1)).isFalse();
        assertThat(r0.contains(0)).isFalse();
        assertThat(r0.contains(1)).isTrue();
        assertThat(r0.contains(2)).isFalse();
        assertThat(r0.contains(3)).isFalse();
    }

    @Test
    void testAtLeast() {
        Range<Integer> r0 = Range.atLeast(1);

        assertThat(r0.contains(-1)).isFalse();
        assertThat(r0.contains(0)).isFalse();
        assertThat(r0.contains(1)).isTrue();
        assertThat(r0.contains(2)).isTrue();
        assertThat(r0.contains(3)).isTrue();
    }

    @Test
    void testAtMost() {
        Range<Integer> r0 = Range.atMost(1);

        assertThat(r0.contains(-1)).isTrue();
        assertThat(r0.contains(0)).isTrue();
        assertThat(r0.contains(1)).isTrue();
        assertThat(r0.contains(2)).isFalse();
        assertThat(r0.contains(3)).isFalse();
    }

    @Test
    void testGreaterThan() {
        Range<Integer> r0 = Range.greaterThan(1);

        assertThat(r0.contains(-1)).isFalse();
        assertThat(r0.contains(0)).isFalse();
        assertThat(r0.contains(1)).isFalse();
        assertThat(r0.contains(2)).isTrue();
        assertThat(r0.contains(3)).isTrue();
    }

    @Test
    void testLessThan() {
        Range<Integer> r0 = Range.lessThan(1);

        assertThat(r0.contains(-1)).isTrue();
        assertThat(r0.contains(0)).isTrue();
        assertThat(r0.contains(1)).isFalse();
        assertThat(r0.contains(2)).isFalse();
        assertThat(r0.contains(3)).isFalse();
    }

    @Test
    void testAll() {
        Range<Integer> r0 = Range.all();

        assertThat(r0.contains(-1)).isTrue();
        assertThat(r0.contains(0)).isTrue();
        assertThat(r0.contains(1)).isTrue();
        assertThat(r0.contains(2)).isTrue();
        assertThat(r0.contains(3)).isTrue();
    }

    @Test
    void testEmpty() {
        Range<Integer> r0 = Range.empty();

        assertThat(r0.contains(-1)).isFalse();
        assertThat(r0.contains(0)).isFalse();
        assertThat(r0.contains(1)).isFalse();
        assertThat(r0.contains(2)).isFalse();
        assertThat(r0.contains(3)).isFalse();
    }

    @Test
    void testAnd() {
        Range<Integer> r0 = Range.closed(0, 2);
        Range<Integer> r1 = Range.closed(1, 3);

        Range<Integer> r01 = Range.and(r0, r1);

        assertThat(r01.contains(0)).isFalse();
        assertThat(r01.contains(1)).isTrue();
        assertThat(r01.contains(2)).isTrue();
        assertThat(r01.contains(3)).isFalse();
    }

    @Test
    void testOr() {
        Range<Integer> r0 = Range.closed(0, 2);
        Range<Integer> r1 = Range.closed(1, 3);

        Range<Integer> r01 = Range.or(r0, r1);

        assertThat(r01.contains(0)).isTrue();
        assertThat(r01.contains(1)).isTrue();
        assertThat(r01.contains(2)).isTrue();
        assertThat(r01.contains(3)).isTrue();
    }

    @Test
    void testXor() {
        Range<Integer> r0 = Range.closed(0, 2);
        Range<Integer> r1 = Range.closed(1, 3);
        Range<Integer> r01 = Range.xor(r0, r1);

        assertThat(r01.contains(0)).isTrue();
        assertThat(r01.contains(1)).isFalse();
        assertThat(r01.contains(2)).isFalse();
        assertThat(r01.contains(3)).isTrue();
    }
}
