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

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import static java.util.Objects.isNull;
import static org.atlanmod.commons.Guards.checkNotNull;

/**
 * Static utility methods related to {@link Iterable} instances.
 */
@Static
@ParametersAreNonnullByDefault
public final class MoreIterables {

    private MoreIterables() {
        throw Throwables.notInstantiableClass(getClass());
    }

    /**
     * Returns a sequential {@link Stream} of the contents of {@code iterable}.
     *
     * @param iterable the iterable
     *
     * @return a sequential {@link Stream} of the contents of {@code iterable}
     */
    @Nonnull
    public static <E> Stream<E> stream(@Nullable Iterable<E> iterable) {
        if (isNull(iterable)) {
            return Stream.empty();
        }

        return StreamSupport.stream(iterable.spliterator(), false);
    }

    /**
     * Returns a parallel {@link Stream} of the contents of {@code iterable}.
     *
     * @param iterable the iterable
     *
     * @return a parallel {@link Stream} of the contents of {@code iterable}
     */
    @Nonnull
    public static <E> Stream<E> parallelStream(@Nullable Iterable<E> iterable) {
        if (isNull(iterable)) {
            return Stream.empty();
        }

        return StreamSupport.stream(iterable.spliterator(), true);
    }

    /**
     * Determines if the given {@code iterable} contains no element.
     *
     * @param iterable the iterable
     *
     * @return {@code true} if the iterable contains no element
     */
    public static boolean isEmpty(Iterable<?> iterable) {
        checkNotNull(iterable, "iterable");

        return iterable instanceof Collection
                ? ((Collection) iterable).isEmpty()
                : MoreIterators.isEmpty(iterable.iterator());
    }

    /**
     * Determines if the given {@code iterable} contains at least one element.
     *
     * @param iterable the iterable
     *
     * @return {@code true} if the iterable contains at least one element
     */
    public static boolean notEmpty(Iterable<?> iterable) {
        return !isEmpty(iterable);
    }

    /**
     * Returns the single element contained in {@code iterable}.
     *
     * @param iterable the iterable
     *
     * @return an {@link Optional} containing the single element of the {@code iterable}, or {@link Optional#empty()} if
     * the {@code iterable} is empty.
     *
     * @throws IllegalArgumentException if the {@code iterable} contains more than one element
     */
    @Nonnull
    public static <E> Optional<E> onlyElement(Iterable<E> iterable) {
        return MoreIterators.onlyElement(iterable.iterator());
    }

    /**
     * Returns the first element contained in {@code iterable}.
     *
     * @param iterable the iterable
     *
     * @return an {@link Optional} containing the first element of the {@code iterable}, or {@link Optional#empty()} if
     * the {@code iterable} is empty.
     *
     * @throws IllegalArgumentException if the {@code iterable} contains more than one element
     */
    @Nonnull
    public static <E> Optional<E> firstElement(Iterable<E> iterable) {
        return MoreIterators.firstElement(iterable.iterator());
    }
}
