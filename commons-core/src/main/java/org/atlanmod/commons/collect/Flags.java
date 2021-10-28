/*
 * Copyright (c) 2021 Naomod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package org.atlanmod.commons.collect;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.Arrays;

import static org.atlanmod.commons.Guards.checkGreaterThanOrEqualTo;
import static org.atlanmod.commons.Guards.checkPositionIndex;

/**
 * This class implements a fixed-size set of bits.
 * <p>
 * The set is composed of a fix number of elements, where each element is indexed by a positive integer and
 * has a boolean value.
 *
 * @author sunye
 * @since 1.1.0
 */
public class Flags implements Serializable {
    private byte[] bytes;

    /**
     * Creates a bit set with a predetermined number of elements.
     *
     * @param size the number of elements
     */
    public Flags(int size) {
        checkGreaterThanOrEqualTo(size, 0);

        // Don't remove casts, otherwise
        // Math.ceil() may not work as expected
        int bytesLength = (int) Math.ceil((double) size / Byte.SIZE );
        bytes = new byte[bytesLength];
    }

    @Nonnull
    private Flags(byte[] bytes) {
        checkGreaterThanOrEqualTo(bytes.length, 0);

        this.bytes = Arrays.copyOf(bytes, bytes.length);
    }

    /**
     * Sets the bit at the specified index to true.
     *
     * @param index a bit index
     */
    public void set(int index) {
        this.set(index, true);
    }

    /**
     * Sets the bit at the specified index to a specific value.
     *
     * @param index a bit index
     * @param value a boolean
     */
    public void set(int index, boolean value) {
        checkPositionIndex(index, bytes.length * Byte.SIZE);

        int position = index / Byte.SIZE;
        int shift = index % Byte.SIZE;

        if (value) {
            bytes[position] |= (1 << shift);
        } else {
            bytes[position] &= ~(1 << shift);
        }
    }

    /**
     * Returns the value of the bit with the specified index.
     * The value is true if the bit corresponding to the index is set to 1. Returns false otherwise.
     *
     * @param index the bit index
     * @return the value of the bit corresponding to the specified index
     */
    public boolean get(int index) {
        checkPositionIndex(index, bytes.length*Byte.SIZE);

        int position = index / Byte.SIZE;
        int shift = index % Byte.SIZE;
        return (bytes[position] & (1 << shift)) != 0;
    }

    /**
     * Returns a new byte array containing all the bits in this bit set.
     *
     * @return a byte array containing a little-endian representation of all the bits in this bit set
     */
    public byte[] toBytes() {
        return Arrays.copyOf(bytes, bytes.length);
    }

    /**
     * Writes a byte array containing a little-endian representation of this bit set to a {@code ByteBuffer}.
     *
     * @param buffer the {@ByteBuffer}
     */
    public void writeOn(ByteBuffer buffer) {
        assert bytes.length < buffer.remaining() : "ByteArray must have enough place for this Bit Set";

        buffer.put(bytes);
    }

    /**
     * Compares this object against another object.
     * The result is {@code true} iff the other object is not {@code null} and is a {@code Flags}
     * object that has exactly the same set of bits set to {@code true} as this one.
     *
     * @param other the object to compare with
     * @return {@code true} if the objects are the same; {@code false} otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        Flags that = (Flags) other;
        return Arrays.equals(bytes, that.bytes);
    }

    /**
     * Returns the hash code value for this bit set.
     *
     * @return the hash code value
     */
    @Override
    public int hashCode() {
        return Arrays.hashCode(bytes);
    }

    @Override
    public String toString() {
        return  Arrays.toString(bytes);
    }

    /**
     * Creates an instance of {@code Flags} from a byte array. For deserialization purposes.
     *
     * @param bytes an array of at least one element.
     *
     * @return an instance of {@code Flags}
     */
    public static Flags fromBytes(byte[] bytes) {
        return new Flags(bytes);
    }
}