/*
 * Copyright (c) 2017 Atlanmod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */
package org.atlanmod.commons.io;

import org.atlanmod.commons.annotation.Static;

import javax.annotation.ParametersAreNonnullByDefault;

import static org.atlanmod.commons.Throwables.notInstantiableClass;

/**
 * Utility static methods for unsigned number classes
 *
 * @author sunye
 * @since 1.1.0
 */
@Static
@ParametersAreNonnullByDefault
public class Numbers {

    private Numbers() {
        throw notInstantiableClass(getClass());
    }

    /**
     * Creates an instance of a subclass of {@code UnsignedNumber}, that is able to represent the value argument.
     *
     * @param value a long
     * @return a sub-instance of UnsignedNumber
     */
    public static UnsignedNumber fromLong(long value) {
        if (value <= UnsignedByte.MAX_VALUE) {
            return ubyte(value);
        } else if (value <= UnsignedShort.MAX_VALUE) {
            return ushort(value);
        } else {
            return uint(value);
        }
    }

    /**
     * Factory method for creating instances of {@code UnsignedByte}
     *
     * @param value a long value
     *
     * @return an instance of {@code UnsignedByte}
     */
    public static UnsignedByte ubyte(long value) {
        return UnsignedByte.fromLong(value);
    }

    /**
     * Factory method for creating instances of {@code UnsignedShort}
     *
     * @param value a long value
     *
     * @return an instance of {@code UnsignedShort}
     */
    public static UnsignedShort ushort(long value) {
        return UnsignedShort.fromLong(value);
    }

    /**
     * Factory method for creating instances of {@code UnsignedInt}
     *
     * @param value a long value
     *
     * @return an instance of {@code UnsignedInt}
     */
    public static UnsignedInt uint(long value) {
        return UnsignedInt.fromLong(value);
    }

    /**
     * Factory method for creating instances of {@code UnsignedVarInt}
     *
     * @param value a long value
     *
     * @return an instance of {@code UnsignedVarInt}
     */
    public static UnsignedVarInt uvarint(long value) {
        return UnsignedVarInt.fromLong(value);
    }
}




