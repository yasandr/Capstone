/*
 * Copyright (c) 2017 Atlanmod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package org.atlanmod.commons.collect;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.Immutable;

import static org.atlanmod.commons.Guards.checkLessThanOrEqualTo;
import static org.atlanmod.commons.Guards.checkNotNull;

/**
 * A range (or "interval") defines the boundaries around a contiguous span of values of integer.
 */
@Immutable
@ParametersAreNonnullByDefault
public interface IntRange {

    /**
     * Creates a range that contains all values strictly greater than {@code lower} and strictly less than {@code
     * upper}.
     *
     * @param lower the lower bound of the range (exclusive)
     * @param upper the upper bound of the range (exclusive)
     *
     * @return a new range
     *
     * @throws IllegalArgumentException if {@code lower} is greater than {@code upper}
     */
    @Nonnull
    static IntRange open(int lower, int upper) {
        checkLessThanOrEqualTo(lower, upper, "upper (%d) must not be less than lower (%d)", upper, lower);
        return v -> v > lower && v < upper;
    }

    /**
     * Creates a range that contains all values greater than or equal to {@code lower} and less than or equal to {@code
     * upper}.
     *
     * @param lower the lower bound of the range (inclusive)
     * @param upper the upper bound of the range (inclusive)
     *
     * @return a new range
     *
     * @throws IllegalArgumentException if {@code lower} is greater than {@code upper}
     */
    @Nonnull
    static IntRange closed(int lower, int upper) {
        checkLessThanOrEqualTo(lower, upper, "upper (%d) must not be less than lower (%d)", upper, lower);
        return v -> v >= lower && v <= upper;
    }

    /**
     * Creates a range that contains all values strictly greater than {@code lower} and less than or equal to {@code
     * upper}.
     *
     * @param lower the lower bound of the range (exclusive)
     * @param upper the upper bound of the range (inclusive)
     *
     * @return a new range
     *
     * @throws IllegalArgumentException if {@code lower} is greater than {@code upper}
     */
    @Nonnull
    static IntRange openClosed(int lower, int upper) {
        checkLessThanOrEqualTo(lower, upper, "upper (%d) must not be less than lower (%d)", upper, lower);
        return v -> v > lower && v <= upper;
    }

    /**
     * Creates a range that contains all values greater than or equal to {@code lower} and strictly less than {@code
     * upper}.
     *
     * @param lower the lower bound of the range (inclusive)
     * @param upper the upper bound of the range (exclusive)
     *
     * @return a new instance of range
     *
     * @throws IllegalArgumentException if {@code lower} is greater than {@code upper}
     */
    @Nonnull
    static IntRange closedOpen(int lower, int upper) {
        checkLessThanOrEqualTo(lower, upper, "upper (%d) must not be less than lower (%d)", upper, lower);
        return v -> v >= lower && v < upper;
    }

    /**
     * Creates a range that contains only the given {@code value}. The returned range is closed on both ends.
     *
     * @param value the unique value of the range
     *
     * @return a new range
     *
     * @see #closed(int, int)
     */
    @Nonnull
    static IntRange singleton(int value) {
        return closed(value, value);
    }

    /**
     * Creates a range that contains all values greater than or equal to {@code lower}.
     *
     * @param lower the lower bound of the range (inclusive)
     *
     * @return a new range
     */
    @Nonnull
    static IntRange atLeast(int lower) {
        return v -> v >= 0;
    }

    /**
     * Creates a range that contains all values less than or equal to {@code upper}.
     *
     * @param upper the upper bound of the range (inclusive)
     *
     * @return a new range
     */
    @Nonnull
    static IntRange atMost(int upper) {
        return v -> v <= upper;
    }

    /**
     * Creates a range that contains all values strictly greater than {@code lower}.
     *
     * @param lower the lower bound of the range (exclusive)
     *
     * @return a new range
     */
    @Nonnull
    static IntRange greaterThan(int lower) {
        return v -> v > lower;
    }

    /**
     * Creates a range that contains all values strictly less than {@code upper}.
     *
     * @param upper the upper bound of the range (exclusive)
     *
     * @return a new range
     */
    @Nonnull
    static IntRange lessThan(int upper) {
        return v -> v < upper;
    }

    /**
     * Creates a range that contains every value of type {@code C}.
     *
     * @return a new range
     */
    @Nonnull
    static IntRange all() {
        return v -> true;
    }

    /**
     * Creates a range that does not contain any value.
     *
     * @return a new range
     */
    @Nonnull
    static IntRange empty() {
        return v -> false;
    }

    /**
     * Creates a range that contains all values enclosed by both ranges.
     * <p>
     * The intersection exists if and only if the two ranges are connected.
     *
     * @param r1 the first range
     * @param r2 the second range
     *
     * @return a new range
     *
     * @throws NullPointerException if any argument is {@code null}
     * @see Boolean#logicalAnd(boolean, boolean)
     */
    @Nonnull
    static IntRange and(IntRange r1, IntRange r2) {
        checkNotNull(r1, "r1");
        checkNotNull(r2, "r2");
        return v -> Boolean.logicalAnd(r1.contains(v), r2.contains(v));
    }

    /**
     * Creates a range that contains all values enclosed by at least one range.
     *
     * @param r1 the first range
     * @param r2 the second range
     *
     * @return a new range
     *
     * @throws NullPointerException if any argument is {@code null}
     * @see Boolean#logicalOr(boolean, boolean)
     */
    @Nonnull
    static IntRange or(IntRange r1, IntRange r2) {
        checkNotNull(r1, "r1");
        checkNotNull(r2, "r2");
        return v -> Boolean.logicalOr(r1.contains(v), r2.contains(v));
    }

    /**
     * Creates a range that contains all values enclosed by one range, but not another.
     *
     * @param r1 the first range
     * @param r2 the second range
     *
     * @return a new range
     *
     * @throws NullPointerException if any argument is {@code null}
     * @see Boolean#logicalXor(boolean, boolean)
     */
    @Nonnull
    static IntRange xor(IntRange r1, IntRange r2) {
        checkNotNull(r1, "r1");
        checkNotNull(r2, "r2");
        return v -> Boolean.logicalXor(r1.contains(v), r2.contains(v));
    }

    /**
     * Returns {@code true} if {@code value} is within the bounds of this range.
     *
     * @param value the value to be tested for inclusion
     *
     * @return {@code true} if {@code value} is within the bounds of this range
     */
    boolean contains(int value);

    /**
     * Returns {@code true} if every element in values is contained in this range.
     *
     * @param values the value to be tested for inclusion
     *
     * @return {@code true} if every element in values is contained in this range
     *
     * @throws NullPointerException if any argument is {@code null}
     * @see #contains(int)
     */
    default boolean containsAll(Iterable<Integer> values) {
        checkNotNull(values, "values");
        return MoreIterables.stream(values).allMatch(this::contains);
    }
}
