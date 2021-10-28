/*
 * Copyright (c) 2017 Atlanmod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package org.atlanmod.commons.primitive;

import org.atlanmod.commons.Throwables;
import org.atlanmod.commons.annotation.Static;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import static org.atlanmod.commons.Guards.checkNotNull;

/**
 * Static utility methods related to {@code double} and {@link Double}.
 */
@Static
@ParametersAreNonnullByDefault
public final class Doubles {

    private Doubles() {
        throw Throwables.notInstantiableClass(getClass());
    }

    /**
     * Encodes a {@code double} to a {@code byte} array.
     *
     * @param value the value to encode
     *
     * @return a {@code byte} array
     *
     * @see Longs#toBytes(long)
     * @see Double#doubleToLongBits(double)
     */
    @Nonnull
    public static byte[] toBytes(final double value) {
        // Encoded as long
        return Longs.toBytes(Double.doubleToLongBits(value));
    }

    /**
     * Encodes a {@link Double} to a {@code byte} array.
     *
     * @param value the value to encode
     *
     * @return a {@code byte} array
     *
     * @throws NullPointerException if the {@code value} is {@code null}
     */
    @Nonnull
    public static byte[] toBytes(final Double value) {
        checkNotNull(value, "value");

        return toBytes(value.doubleValue());
    }
}
