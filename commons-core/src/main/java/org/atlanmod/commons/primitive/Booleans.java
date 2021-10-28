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

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import static org.atlanmod.commons.Guards.checkNotNull;

/**
 * Static utility methods related to {@code boolean} and {@link Boolean}.
 */
@Static
@ParametersAreNonnullByDefault
public final class Booleans {

    private Booleans() {
        throw Throwables.notInstantiableClass(getClass());
    }

    /**
     * Encodes a {@code boolean} to a {@code byte} array.
     *
     * @param value the value to encode
     *
     * @return a {@code byte} array
     */
    @Nonnull
    public static byte[] toBytes(final boolean value) {
        return new byte[]{value ? (byte) 1 : (byte) 0};
    }

    /**
     * Encodes a {@link Boolean} to a {@code byte} array.
     *
     * @param value the value to encode
     *
     * @return a {@code byte} array
     *
     * @throws NullPointerException if the {@code value} is {@code null}
     */
    @Nonnull
    public static byte[] toBytes(final Boolean value) {
        checkNotNull(value, "value");

        return toBytes(value.booleanValue());
    }

    /**
     * Returns the integer value of a boolean {@code value}. In other words, returns {@code 1} when {@code value ==
     * true}, {@code 0} otherwise.
     *
     * @param value the boolean value
     *
     * @return an {@code int}
     */
    @Nonnegative
    public static int toInt(final boolean value) {
        return value ? 1 : 0;
    }
}
