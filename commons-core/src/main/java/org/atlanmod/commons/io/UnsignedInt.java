/*
 * Copyright (c) 2021 Naomod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */
package org.atlanmod.commons.io;

import javax.annotation.Nonnull;
import java.util.Objects;

import static org.atlanmod.commons.Guards.*;

/**
 * The `UnsignedInt` class allows the representation of unsigned 32-bit values.
 * It wraps a value of the primitive getType `long` in an object.
 * An object of getType `UnsignedInt` contains a single field whose getType is `long`.
 *
 * @author sunye
 * @since 1.1.0
 */
public class UnsignedInt extends UnsignedNumber implements Comparable<UnsignedInt> {

    public static final long MIN_VALUE = 0;
    public static final long MAX_VALUE = 0xffffffffL;
    public static final int SIZE = 32;
    public static final int BYTES = SIZE / Byte.SIZE;
    private static final long UNSIGNED_INT_MASK = 0xFFFFFFFFL;

    private final long value;

    private UnsignedInt(long value) {
        this.value = value;
    }

    /**
     * Wraps an `int` value into a `UnsignedInt`.
     *
     * @param value A unsigned 32-bits unsigned int value.
     * @return un object wrapping the unsigned int value.
     */
    public static UnsignedInt fromInt(int value) {
        return new UnsignedInt(value & UNSIGNED_INT_MASK);
    }


    public static UnsignedInt fromLong(long value) {
        checkArgument(value >= MIN_VALUE && value <= MAX_VALUE);

        long unsigned = value & UNSIGNED_INT_MASK;
        return new UnsignedInt(unsigned);
    }

    /**
     * Compares this object to the specified object.
     * The result is `true` if and only if the argument is not null and is an `UnsignedInt` object that contains
     * the same `long` value as this object.
     *
     * @param obj the object to compare with.
     *
     * @return `true` if the objects are the same; `false` otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        //@formatter:off
        if (this == obj) {return true;}
        if (obj == null || getClass() != obj.getClass()) {return false;}
        //@formatter:on

        UnsignedInt that = (UnsignedInt) obj;
        return value == that.value;
    }

    /**
     * Returns a hash code for this `UnsignedInt`.
     * @return a hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public int intValue() {
        return (int) value;
    }

    @Override
    public long longValue() {
        return value;
    }

    @Override
    public float floatValue() {
        return (float) value;
    }

    @Override
    public double doubleValue() {
        return (double) value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public int compareTo(UnsignedInt other) {
        return Long.compare(this.value, other.value);
    }

    /**
     * Encodes a {@code UnsignedInt} to a {@code byte} array, following the big endian order.
     *
     * @return a {@code byte} array
     */
    @Nonnull
    public byte[] toBytes() {
        final int length = UnsignedInt.BYTES;
        final int value = this.intValue();

        byte[] bytes = new byte[length];

        for (int i = 0; i < length; i++) {
            int shift = Byte.SIZE * (length - i - 1);
            bytes[i] = (byte) (value >> shift);
        }

        return bytes;
    }

    public static UnsignedInt fromBytes(byte[] bytes) {
        checkNotNull(bytes, "bytes");
        checkEqualTo(bytes.length, UnsignedInt.BYTES, "bytes has wrong size: %d", bytes.length);

        int value = 0;
        final int length = UnsignedInt.BYTES - 1;
        for (int i = length; i >= 0; i--) {
            value |= (bytes[i]) << Byte.SIZE * (length - i);
        }

        return UnsignedInt.fromInt(value);
    }
}
