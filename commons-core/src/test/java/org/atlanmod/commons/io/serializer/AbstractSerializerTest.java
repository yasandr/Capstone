/*
 * Copyright (c) 2017 Atlanmod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package org.atlanmod.commons.io.serializer;

import org.atlanmod.commons.AbstractTest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * An abstract test-case that checks the behavior of {@link BinarySerializer} instances.
 */
@ParametersAreNonnullByDefault
public abstract class AbstractSerializerTest extends AbstractTest {

    /**
     * Serializes then deserializes the given {@code value} with the specified {@code serializer}, with the basic
     * methods.
     *
     * @param value      the sample value
     * @param serializer the serializer to use
     * @param <T>        the type of the (de)serialized value
     *
     * @return the value after processing
     */
    protected <T, U> T process(T value, Serializer<T, U> serializer) throws IOException {
        U serialized = serializer.serialize(value);
        return serializer.deserialize(serialized);
    }

    /**
     * Serializes then deserializes the given {@code value} with the specified {@code serializer}, by using a stream.
     *
     * @param value      the sample value
     * @param serializer the serializer to use
     * @param <T>        the type of the (de)serialized value
     *
     * @return the value after processing
     *
     * @throws IOException if an I/O error occurs during the serialization
     */
    protected <T> T processWithStream(T value, BinarySerializer<T> serializer) throws IOException {
        byte[] data;

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream(); ObjectOutput out = new ObjectOutputStream(baos)) {
            serializer.serialize(value, out);
            out.flush();

            data = baos.toByteArray();
        }

        try (ObjectInput in = new ObjectInputStream(new ByteArrayInputStream(data))) {
            return serializer.deserialize(in);
        }
    }
}
