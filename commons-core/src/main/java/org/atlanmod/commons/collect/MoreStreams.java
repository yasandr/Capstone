/*
 * Copyright (c) 2017 Atlanmod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package org.atlanmod.commons.collect;

import org.atlanmod.commons.Throwables;
import org.atlanmod.commons.annotation.Static;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntUnaryOperator;
import java.util.function.LongUnaryOperator;
import java.util.stream.Stream;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Static utility methods related to {@link Stream} instances.
 */
@Static
@ParametersAreNonnullByDefault
public final class MoreStreams {

    private MoreStreams() {
        throw Throwables.notInstantiableClass(getClass());
    }

    /**
     * Finds the index of the given {@code value} in the {@code stream}.
     *
     * @param stream the stream to search through for the object
     * @param value  the value to find
     *
     * @return an {@link Optional} containing the index of the value within the stream, {@link Optional#empty()} if not
     * found
     */
    @Nonnull
    public static Optional<Integer> indexOf(Stream<?> stream, Object value) {
        AtomicInteger currentIndex = new AtomicInteger(-1);

        boolean found = stream.peek(e -> currentIndex.incrementAndGet())
                .anyMatch(e -> Objects.equals(e, value));

        return found ?
                Optional.of(currentIndex.get()) :
                Optional.empty();
    }

    /**
     * Finds the last index of the given {@code value} in the {@code stream}.
     *
     * @param stream the stream to traverse for looking for the object
     * @param value  the value to find, may be {@code null}
     *
     * @return an {@link Optional} containing the index of the value within the stream, {@link Optional#empty()} if not
     * found
     */
    @Nonnull
    public static Optional<Integer> lastIndexOf(Stream<?> stream, Object value) {
        AtomicInteger currentIndex = new AtomicInteger(-1);
        AtomicInteger lastIndex = new AtomicInteger(-1);

        stream.peek(e -> currentIndex.incrementAndGet())
                .filter(e -> Objects.equals(e, value))
                .forEach(e -> lastIndex.set(currentIndex.intValue()));

        return lastIndex.get() >= 0
                ? Optional.of(lastIndex.get())
                : Optional.empty();
    }

    /**
     * Returns the number of element in the {@code stream} as an integer;
     *
     * @param stream the stream
     *
     * @return an {@link Optional} containing the size, {@link Optional#empty()} if the stream is empty
     */
    @Nonnull
    public static Optional<Integer> size(Stream<?> stream) {
        return Optional.of(stream.count())
                .map(Long::intValue)
                .filter(s -> s > 0);
    }

    /**
     * Returns an unary operator to iterates over an {@link java.util.stream.IntStream} in the reverse direction.
     *
     * @param startInclusive the (inclusive) initial value
     * @param endExclusive   the exclusive upper bound
     *
     * @return an unary operator
     *
     * @see java.util.stream.IntStream#range(int, int)
     */
    @Nonnull
    public static IntUnaryOperator reverseOrder(int startInclusive, int endExclusive) {
        return i -> endExclusive - i + startInclusive - 1;
    }

    /**
     * Returns an unary operator to iterates over an {@link java.util.stream.LongStream} in the reverse direction.
     *
     * @param startInclusive the (inclusive) initial value
     * @param endExclusive   the exclusive upper bound
     *
     * @return an unary operator
     *
     * @see java.util.stream.LongStream#range(long, long)
     */
    @Nonnull
    public static LongUnaryOperator reverseOrder(long startInclusive, long endExclusive) {
        return i -> endExclusive - i + startInclusive - 1;
    }
}
