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
 * Static utility methods related to {@code int} and {@link Integer}.
 */
@Static
@ParametersAreNonnullByDefault
public final class Ints {

    private Ints() {
        throw Throwables.notInstantiableClass(getClass());
    }

    /**
     * Encodes an {@code int} to a {@code byte} array.
     *
     * @param value the value to encode
     *
     * @return a {@code byte} array
     */
    @Nonnull
    public static byte[] toBytes(final int value) {
        byte[] bytes = new byte[Integer.BYTES];

        final int length = Integer.BYTES - 1;
        for (int i = length; i >= 0; i--) {
            bytes[i] = (byte) (value >> Byte.SIZE * (length - i));
        }

        return bytes;
    }

    /**
     * Encodes an {@link Integer} to a {@code byte} array.
     *
     * @param value the value to encode
     *
     * @return a {@code byte} array
     *
     * @throws NullPointerException if the {@code value} is {@code null}
     */
    @Nonnull
    public static byte[] toBytes(final Integer value) {
        checkNotNull(value, "value");

        return toBytes(value.intValue());
    }
}
