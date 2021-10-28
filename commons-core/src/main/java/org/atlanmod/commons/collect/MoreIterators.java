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

import java.util.Iterator;
import java.util.Optional;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import static org.atlanmod.commons.Guards.checkNotNull;

/**
 * Static utility methods related to {@link Iterator} instances.
 */
@Static
@ParametersAreNonnullByDefault
public final class MoreIterators {

    private MoreIterators() {
        throw Throwables.notInstantiableClass(getClass());
    }

    /**
     * Determines if the given {@code iterator} contains no element.
     *
     * @param iterator the iterator
     *
     * @return {@code true} if the iterator contains no element
     */
    public static boolean isEmpty(Iterator<?> iterator) {
        checkNotNull(iterator, "iterator");

        return !iterator.hasNext();
    }

    /**
     * Determines if the given {@code iterator} contains at least one element.
     *
     * @param iterator the iterator
     *
     * @return {@code true} if the iterator contains at least one element
     */
    public static boolean notEmpty(Iterator<?> iterator) {
        return !isEmpty(iterator);
    }

    /**
     * Returns the single element contained in {@code iterator}.
     *
     * @param iterator the iterator
     *
     * @return an {@link Optional} containing the single element of the {@code iterator}, or {@link Optional#empty()} if
     * the {@code iterator} is empty.
     *
     * @throws IllegalArgumentException if the {@code iterator} contains more than one element
     */
    @Nonnull
    public static <E> Optional<E> onlyElement(Iterator<E> iterator) {
        E first = null;

        if (iterator.hasNext()) {
            first = iterator.next();

            if (iterator.hasNext()) {
                throw new IllegalArgumentException("Expected one element in the iterator");
            }
        }

        return Optional.ofNullable(first);
    }

    /**
     * Returns the first element contained in {@code iterator}.
     *
     * @param iterator the iterator
     *
     * @return an {@link Optional} containing the first element of the {@code iterator}, or {@link Optional#empty()} if
     * the {@code iterator} is empty.
     */
    @Nonnull
    public static <E> Optional<E> firstElement(Iterator<E> iterator) {
        return iterator.hasNext() ? Optional.of(iterator.next()) : Optional.empty();

    }
}
