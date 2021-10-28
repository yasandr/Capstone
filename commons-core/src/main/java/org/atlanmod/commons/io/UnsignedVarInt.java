/*
 * Copyright (c) 2021 Naomod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */
package org.atlanmod.commons.io;

import javax.annotation.Nonnull;
import java.nio.ByteBuffer;
import java.util.Objects;

import static org.atlanmod.commons.Guards.*;
import static org.atlanmod.commons.Preconditions.requireThat;

/**
 * The `UnsignedVarInt` class allows the representation of up to 7-Bytes positive integers.
 * The main difference with {@code UnsignedInt} is that when serialized, it uses between 1
 * and 8 bytes, where each byte uses 7 bits to represent data and 1 bit to signify if the
 * reading/parsing must continue or stop:
 *
 *    1  2  3  4  5  6  7  8
 *  +--+--+--+--+--+--+--+---+
 *  |       Data         | c |
 *  +--+--+--+--+--+--+--+---+
 *
 *  If c is set to 0, the serialized number is lesser or equal to {@0x7F} and the reading
 *  stops. If the c is set to one, the next byte will be considered part of the current
 *  read integer.
 *
 * For instance, the 1-byte int {@code 0xFF }, in binaries {@code [1111 1111]}, is
 * serialized using a 2-bytes array: {@code [1111 1111], [0000 0001]}. Note that
 * here we follow the little-endian order.
 *
 * @author sunye
 * @since 1.1.0
 */
public class UnsignedVarInt extends UnsignedNumber implements Comparable<UnsignedVarInt> {

    // @formatter:off
    /**
     * A constant holding the maximum value a {@code UnsignedVarInt} can have: 0.
     */
    public static final long    MIN_VALUE = 0;

    /**
     * A constant holding the maximum value a {@code UnsignedVarInt} can have, 2<sup>56</sup>-1.
     */
    public  static final long   MAX_VALUE = 0xfffffffffffffffL;
    private static final long   UNSIGNED_MASK = 0xFFFFFFFFFFFFFFL;
    // @formatter: on

    private final long value;

    /**
     * Private constructor, use {@code UnsignedVarInt::fromLong()}.
     *
     * @param value a positive long
     */
    private UnsignedVarInt(long value) {
        requireThat(value).isLessThanOrEqualTo(MAX_VALUE);
        requireThat(value).isGreaterThanOrEqualTo(MIN_VALUE);

        this.value = value;
    }

    /**
     * Wraps an `int` value into a `UnsignedVarInt`.
     *
     * @param value A unsigned 32-bits unsigned int value.
     * @return un object wrapping the unsigned int value.
     */
    public static UnsignedVarInt fromInt(int value) {
        return fromLong(value);
    }


    public static UnsignedVarInt fromLong(long value) {
        checkGreaterThanOrEqualTo(value, MIN_VALUE, "value");
        checkLessThanOrEqualTo(value, MAX_VALUE, "value");

        long unsigned = value & UNSIGNED_MASK;
        return new UnsignedVarInt(unsigned);
    }

    /**
     * Compares this object to the specified object.
     * The result is `true` if and only if the argument is not null and is an `UnsignedVarInt` object that contains
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

        UnsignedVarInt that = (UnsignedVarInt) obj;
        return value == that.value;
    }

    /**
     * Returns a hash code for this `UnsignedVarInt`.
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
    public int compareTo(UnsignedVarInt other) {
        return Long.compare(this.value, other.value);
    }

    /**
     * Encodes a {@code UnsignedInt} to a {@code byte} array, following the little-endian order.
     *
     * @return a {@code byte} array
     */
    @Nonnull
    public byte[] toBytes() {
        if (value == 0) return new byte[] {0};
        int length = 0;
        while ((value >> (7 * length++)) > 0x7F);

        byte[] bytes = new byte[length];

        for (int i = 0; i < length; i++) {
            int shift = 7 * i;
            bytes[i] = (byte) (0x80 | (value >> shift));
        }

        bytes[length -1] &= 0x7F; // Unset the continuation bit

        return bytes;
    }


    /**
     * Creates an instance of {@code UnsignedVarInt} from a by array.
     * The length of the byte array must be between 1 and 8.
     *
     * @param bytes the byte array.
     * @return an instance of {@code UnsignedVarInt}.
     */
    public static UnsignedVarInt fromBytes(byte[] bytes) {
        checkNotNull(bytes, "bytes");

        long value = 0;
        int i = 0;

        // While continuation bit is set:
        while (((bytes[i] & 0x80) > 0) && i < bytes.length) {
            long cleanValue = (bytes[i] & 0x7F);
            cleanValue = cleanValue << (7 * i);
            value |= (cleanValue);
            i++;
        }

        value |= ((long) bytes[i] << (7 * i));

        return UnsignedVarInt.fromLong(value);
    }

    /**
     * Creates an instance of {@code UnsignedVarInt} from a by array.
     * The length of the byte array must be between 1 and 8.
     *
     * @param buffer the byte array.
     * @return an instance of {@code UnsignedVarInt}.
     */
    public static UnsignedVarInt fromByteBuffer(ByteBuffer buffer) {
        checkNotNull(buffer, "buffer");

        long value = 0;
        int i = 0;
        byte current;

        // While continuation bit is set:
        while ((((current = buffer.get()) & 0x80) > 0) && i < 8 && buffer.hasRemaining()) {
            long cleanValue = (current & 0x7F);
            cleanValue = cleanValue << (7 * i);
            value |= (cleanValue);
            i++;
        }

        value |= ((long) current << (7 * i));

        return UnsignedVarInt.fromLong(value);
    }
}
