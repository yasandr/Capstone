/*
 * Copyright (c) 2020 Atlanmod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package org.atlanmod.commons.tuple;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.io.Serializable;
import java.util.Objects;

/**
 * A simple, immutable Tuple with 2 elements: left and right.
 *
 * @param <L> the Generic Type of the left element of this Pair
 * @param <R> the Generic Type of right element of this Pair
 */
@ParametersAreNonnullByDefault
public class Pair<L, R> implements Serializable {

    public final L left;
    public final R right;


    /**
     * Constructs a new Pair instance with elements left and right
     * @param left the left element
     * @param right the right element
     */
    private Pair(final L left, final R right) {
        this.left = left;
        this.right = right;
    }

    /**
     * Creates a new Pair with values left and right
     *
     * @param left  the left value
     * @param right the right value
     * @param <L>   the Class of the left value
     * @param <R>   the Class of the right value
     * @return a new Pair
     */
    @Nonnull
    public static <L, R> Pair<L, R> of(L left, R right) {
        return new Pair<>(left, right);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return left.equals(pair.left) &&
                right.equals(pair.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right);
    }

    /**
     * Creates a new Pair by swapping the elements left and right
     *
     * @return a new Pair with inverted values and Generic Types
     */
    public Pair<R, L> swap() {
        return new Pair<>(right, left);
    }
}
