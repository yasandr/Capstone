/*
 * Copyright (c) 2017 Atlanmod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package org.atlanmod.commons.hash;

import org.atlanmod.commons.primitive.Longs;

import java.util.Objects;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.Immutable;

/**
 * An immutable hash code with a {@link Long} representation (64 bits).
 */
@Immutable
@ParametersAreNonnullByDefault
final class LongHashCode implements HashCode {

    @SuppressWarnings("JavaDoc")
    private static final long serialVersionUID = 6258681675820336026L;

    /**
     * The bytes representation of this hash code.
     */
    private final long value;

    /**
     * Constructs a new {@code HashCode} with the given {@code value}.
     *
     * @param value the long representation of this hash code
     */
    public LongHashCode(long value) {
        this.value = value;
    }

    @Nonnegative
    @Override
    public int bits() {
        return Long.SIZE;
    }

    @Nonnull
    @Override
    public byte[] toBytes() {
        return Longs.toBytes(value);
    }

    @Override
    public long toLong() {
        return value;
    }

    @Nonnull
    @Override
    public String toHexString() {
        return Long.toHexString(value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LongHashCode that = (LongHashCode) o;
        return value == that.value;
    }

    @Override
    public String toString() {
        return String.format("HashCode {%s}", toHexString());
    }
}
