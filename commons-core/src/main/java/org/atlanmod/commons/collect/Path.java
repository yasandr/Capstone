/*
 * Copyright (c) 2021 Naomod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package org.atlanmod.commons.collect;

import javax.annotation.ParametersAreNonnullByDefault;

import static org.atlanmod.commons.Guards.*;

import java.util.Arrays;
import java.util.Objects;

/**
 * This data structure can represent a sequence o elements, such as:
 *
 * - File paths: "/usr/local/bin"
 * - Domain names: "org.atlanmod.commons"
 * - Namespaces: "java.util.function.BiFunction"
 *
 * @param <T> The type of the path elements
 *
 * @author sunye
 * @since 1.1.0
 */
@ParametersAreNonnullByDefault
public class Path<T> {
    private final T[] values;

    @SafeVarargs
    private Path(T... values) {
        this.values = Objects.requireNonNull(values);
    }

    /**
     * Returns the number of elements composing this Path
     *
     * @return the size of this path
     */
    public int size() {
        return values.length;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Path)) return false;

        Path<?> other = (Path<?>) o;
        return Arrays.equals(this.values, other.values);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(values);
    }

    @Override
    public String toString() {
        return Arrays.toString(values);
    }

    /**
     * Returns the last element (length -1) of the path
     *
     * @return the last element
     */
    public T last() {
        return this.get(values.length - 1);
    }

    /**
     * Returns the head, i.e., the first element (0) of the path
     *
     * @return the first element
     */
    public T head() {
        return this.get(0);
    }

    public Path<T> tail() {
        checkState(values.length > 1);

        return new Path<>(Arrays.copyOfRange(values, 1, values.length));
    }

    /**
     * Returns a given element of the path
     *
     * @param index the index of the element
     * @return an element
     */
    private T get(int index) {
        return values[checkElementIndex(index, values.length)];
    }

    /**
     * Creates a new Path from a sequence of values
     *
     * @param values The elements of the path
     * @param <E> The type of the path elements
     * @return the type of the path elements
     */
    @SafeVarargs
    public  static <E> Path<E> of(E... values) {
        return new Path<>(Arrays.copyOf(checkNotNull(values), values.length));
    }
}
