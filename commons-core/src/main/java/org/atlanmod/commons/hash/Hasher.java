/*
 * Copyright (c) 2017 Atlanmod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package org.atlanmod.commons.hash;

import org.atlanmod.commons.primitive.Booleans;
import org.atlanmod.commons.primitive.Chars;
import org.atlanmod.commons.primitive.Doubles;
import org.atlanmod.commons.primitive.Floats;
import org.atlanmod.commons.primitive.Ints;
import org.atlanmod.commons.primitive.Longs;
import org.atlanmod.commons.primitive.Shorts;
import org.atlanmod.commons.primitive.Strings;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

/**
 * A hash function producing a {@link HashCode} from byte sequences of arbitrary length.
 */
@FunctionalInterface
@ParametersAreNonnullByDefault
public interface Hasher {

    /**
     * Calculates the {@link HashCode} of the given {@code data}.
     *
     * @param data the {@code byte} to hash
     *
     * @return a new hash code
     */
    @Nonnull
    default HashCode hash(byte data) {
        return hash(new byte[]{data});
    }

    /**
     * Calculates the {@link HashCode} of the given {@code data}.
     *
     * @param data the {@code boolean} to hash
     *
     * @return a new hash code
     */
    @Nonnull
    default HashCode hash(boolean data) {
        return hash(Booleans.toBytes(data));
    }

    /**
     * Calculates the {@link HashCode} of the given {@code data}.
     *
     * @param data the {@code char} to hash
     *
     * @return a new hash code
     */
    @Nonnull
    default HashCode hash(char data) {
        return hash(Chars.toBytes(data));
    }

    /**
     * Calculates the {@link HashCode} of the given {@code data}.
     *
     * @param data the {@code short} to hash
     *
     * @return a new hash code
     */
    @Nonnull
    default HashCode hash(short data) {
        return hash(Shorts.toBytes(data));
    }

    /**
     * Calculates the {@link HashCode} of the given {@code data}.
     *
     * @param data the {@code int} to hash
     *
     * @return a new hash code
     */
    @Nonnull
    default HashCode hash(int data) {
        return hash(Ints.toBytes(data));
    }

    /**
     * Calculates the {@link HashCode} of the given {@code data}.
     *
     * @param data the {@code long} to hash
     *
     * @return a new hash code
     */
    @Nonnull
    default HashCode hash(long data) {
        return hash(Longs.toBytes(data));
    }

    /**
     * Calculates the {@link HashCode} of the given {@code data}.
     *
     * @param data the {@code float} to hash
     *
     * @return a new hash code
     */
    @Nonnull
    default HashCode hash(float data) {
        return hash(Floats.toBytes(data));
    }

    /**
     * Calculates the {@link HashCode} of the given {@code data}.
     *
     * @param data the {@code double} to hash
     *
     * @return a new hash code
     */
    @Nonnull
    default HashCode hash(double data) {
        return hash(Doubles.toBytes(data));
    }

    /**
     * Calculates the {@link HashCode} of the given {@code data}.
     *
     * @param data the string to hash
     *
     * @return a new hash code
     */
    @Nonnull
    default HashCode hash(String data) {
        return hash(Strings.toBytes(data));
    }

    /**
     * Calculates the {@link HashCode} of the given {@code byte} array.
     *
     * @param data the {@code byte} array to hash
     *
     * @return a new hash code
     */
    @Nonnull
    HashCode hash(byte[] data);
}
