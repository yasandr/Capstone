/*
 * Copyright (c) 2017 Atlanmod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package org.atlanmod.commons.io.serializer;

import org.atlanmod.commons.primitive.Bytes;
import org.atlanmod.commons.primitive.Strings;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

/**
 * A {@link StringSerializer} that encodes and decodes a {@code byte} array to a Base16 representation.
 *
 * @param <T> the type of (de)serialized objects
 *
 * @see Bytes#toStringBinary(byte[])
 * @see Strings#toBytesBinary(String)
 */
@ParametersAreNonnullByDefault
final class Base16Serializer<T> extends AbstractStringSerializer<T> {

    /**
     * Constructs a new {@code Base16Serializer}.
     *
     * @param serializer the serializer to use before applying a {@link String} encoding or decoding
     */
    protected Base16Serializer(@Nonnull BinarySerializer<T> serializer) {
        super(serializer);
    }

    @Nonnull
    @Override
    protected String encode(byte[] data) {
        return Bytes.toStringBinary(data);
    }

    @Nonnull
    @Override
    protected byte[] decode(String data) {
        return Strings.toBytesBinary(data);
    }
}
