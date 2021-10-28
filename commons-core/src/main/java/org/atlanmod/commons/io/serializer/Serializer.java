/*
 * Copyright (c) 2017 Atlanmod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package org.atlanmod.commons.io.serializer;

import org.atlanmod.commons.Throwables;
import org.atlanmod.commons.function.Converter;

import java.io.IOException;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

/**
 * An object that is responsible for encoding and decoding the basic representation of {@link Object}s.
 *
 * @param <T> the type of (de)serialized objects
 * @param <U> the type of the basic representation of the objects
 */
@ParametersAreNonnullByDefault
public interface Serializer<T, U> extends Converter<T, U> {

    /**
     * {@inheritDoc}
     *
     * @see #serialize(Object)
     */
    @Nonnull
    @Override
    default U convert(T t) {
        try {
            return serialize(t);
        }
        catch (IOException e) {
            throw Throwables.shouldNeverHappen(e);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @see #deserialize(Object)
     */
    @Nonnull
    @Override
    default T revert(U data) {
        try {
            return deserialize(data);
        }
        catch (IOException e) {
            throw Throwables.shouldNeverHappen(e);
        }
    }

    /**
     * Write an object of type {@code T} into its basic representation.
     *
     * @param t the object to serialize
     *
     * @return the serialized object
     *
     * @throws IOException if an I/O error occurs during the serialization
     */
    @Nonnull
    U serialize(T t) throws IOException;

    /**
     * Reads and assembles an object of type {@code T} from the given {@code data}.
     *
     * @param data the basic representation of the object
     *
     * @return the deserialized object
     *
     * @throws IOException if an I/O error occurs during the deserialization
     */
    @Nonnull
    T deserialize(U data) throws IOException;
}
