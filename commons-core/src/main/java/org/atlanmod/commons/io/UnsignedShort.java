/*
 * Copyright (c) 2021 Naomod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */
package org.atlanmod.commons.io;

import org.atlanmod.commons.Guards;

import javax.annotation.Nonnull;

import static org.atlanmod.commons.Guards.*;

/**
 * The `UnsignedShort` class allows the representation of unsigned 16-bit values.
 * It wraps a value of the primitive getType `int` in an object.
 * An object of getType `UnsignedShort` contains a single field whose getType is `int`.
 *
 *  @author sunye
 *  @since 1.1.0
 */
public class UnsignedShort extends UnsignedNumber implements Comparable<UnsignedShort> {

    public  static final int MIN_VALUE = 0;
    public  static final int MAX_VALUE = 0xFFFF;
    public  static final int SIZE = 16;
    public  static final int BYTES = SIZE / Byte.SIZE;
    private static final int UNSIGNED_SHORT_MASK = 0xFFFF;

    private final int value;

    /**
     * Constructs a newly allocated {@code UnsignedShort} object that
     * represents the specified unsigned short value.
     *
     * @param   value   the value to be represented by the
     *                  {@code UnsignedShort} object.
     *
     */
    protected UnsignedShort(int value) {
        Guards.checkLessThanOrEqualTo(value, UnsignedShort.MAX_VALUE);
        Guards.checkGreaterThanOrEqualTo(value, UnsignedShort.MIN_VALUE);

        this.value = value;
    }


    /**
     * Returns the value of this {@code UnsignedShort} as an {@code int}.
     * @return the numeric value represented by this object after conversion to type {@code int}.
     */
    public int intValue() {
        return value;
    }

    /**
     * Returns the value of this {@code UnsignedShort} as an {@code long}.
     * @return the numeric value represented by this object after conversion to type {@code long}.
     */
    @Override
    public long longValue() {
        return value;
    }

    /**
     * Returns the value of this {@code UnsignedShort} as an {@code float}.
     * @return the numeric value represented by this object after conversion to type {@code float}.
     */
    @Override
    public float floatValue() {
        return (float) value;
    }

    /**
     * Returns the value of this {@code UnsignedShort} as an {@code double}.
     * @return the numeric value represented by this object after conversion to type {@code double}.
     */
    @Override
    public double doubleValue() {
        return value;
    }

    /**
     *  Returns the value of this {@code UnsignedShort} as a {@code short}.
     *  May result in a negative value.
     */
    @Override
    public short shortValue() {
        return (short) value;
    }

    /**
     * Returns a {@code String} object representing this
     * {@code UnsignedShort}'s value.
     *
     * @return  a string representation of the value of this object in
     *          base&nbsp;10.
     */
    @Override
    public String toString() {
        return String.valueOf(value);
    }

    /**
     * Compares this object to the specified object.  The result is
     * {@code true} if and only if the argument is not
     * {@code null} and is an {@code UnsignedShort} object that
     * contains the same {@code int} value as this object.
     *
     * @param   other   the object to compare with.
     * @return  {@code true} if the objects are the same;
     *          {@code false} otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof UnsignedShort) {
            return value == ((UnsignedShort)other).value;
        }
        return false;
    }

    /**
     * Returns a hash code for this {@code UnsignedShort}.
     *
     * @return  a hash code value for this object, equal to the
     *          primitive {@code int} value represented by this
     *          {@code UnsignedShort} object.
     */
    @Override
    public int hashCode() {
        return UnsignedShort.hashCode(value);
    }

    /**
     * Returns a hash code for an {@code int} value; compatible with
     * {@code UnsignedShort.hashCode()}.
     *
     * @param value the value to hash
     *
     * @return a hash code value for an {@code int} value.
     */
    public static int hashCode(int value) {
        return value;
    }

    /**
     * Creates an instance of {@code UnsignedShort} from a primitive {@code short} value [-32768|32767].
     * The sign bit is considered as the highest bit of the short value.
     * For instance, the primitive short value -32,768 is converted to 65,535.
     *
     *
     * @param value the primitive short containing an unsigned short value
     * @return a {@code UnsignedShort} instance that wraps an unsigned short.
     */
    public static UnsignedShort fromShort(short value) {
        return new UnsignedShort(value & UNSIGNED_SHORT_MASK);
    }

    /**
     * Creates an instance of {@code UnsignedShort} from a primitive {@code int} value.
     * @param value a {@code int} value between [0|65535]
     *
     * @return an instance of {@code UnsignedShort} representing the value.
     */
    public static UnsignedShort fromInt(int value) {
        Guards.checkLessThanOrEqualTo(value, UnsignedShort.MAX_VALUE);
        Guards.checkGreaterThanOrEqualTo(value, UnsignedShort.MIN_VALUE);

        int unsigned = value & UNSIGNED_SHORT_MASK;
        return new UnsignedShort(unsigned);
    }


    public static UnsignedShort fromLong(long value) {
        checkArgument(value <= UnsignedShort.MAX_VALUE, "value");
        checkArgument(value >= UnsignedShort.MIN_VALUE, "value");

        int unsigned = (int) value & UNSIGNED_SHORT_MASK;
        return new UnsignedShort(unsigned);
    }

    /**
     * Compares two {@code UnsignedShort} objects numerically.
     *
     * @param   other the {@code UnsignedShort} to be compared.
     * @return  the value {@code 0} if this {@code UnsignedShort} is equal to the argument
     *          {@code UnsignedShort};
     *          a value less than {@code 0} if this {@code UnsignedShort} is numerically
     *          less than the argument {@code UnsignedShort};
     *          and a value greater than {@code 0} if this {@code UnsignedShort} is numerically
     *          greater than the argument {@code UnsignedShort} (signed comparison).
     */
    @Override
    public int compareTo(UnsignedShort other) {
        return Integer.compare(this.value, other.value);
    }

    /**
     * Compares two {@code UnsignedShort} objects.
     *
     * @return {@code true} of this {@code UnsignedShort} is strictly less than the argument,
     *         {@code false} otherwise.
     */
    public boolean isLessThan(UnsignedShort other) {
        return value < other.value;
    }

    /**
     * Returns true if the value of this {@code UnsignedShort} is zero.
     *
     * @return {@code true} if value is zero,
     *          {@code false} otherwise.
     */
    public boolean isZero() {
        return value == 0;
    }

    /**
     * Encodes a {@code UnsignedShort} to a {@code byte} array, following the big endian order.
     *
     * @return a {@code byte} array
     */
    @Nonnull
    public byte[] toBytes() {
        byte[] bytes = new byte[UnsignedShort.BYTES];
        final int length = UnsignedShort.BYTES - 1;
        for (int i = length; i >= 0; i--) {
            bytes[i] = (byte) (this.shortValue() >> UnsignedShort.SIZE * (length - i));
        }

        return bytes;
    }

    public static UnsignedShort fromBytes(byte[] bytes) {
        checkNotNull(bytes, "bytes");
        checkEqualTo(bytes.length, UnsignedShort.BYTES, "bytes has wrong size: %d", bytes.length);

        short value = 0;
        final int length = Short.BYTES - 1;
        for (int i = length; i >= 0; i--) {
            value |= (bytes[i]) << Byte.SIZE * (length - i);
        }

        return UnsignedShort.fromShort(value);
    }
}
