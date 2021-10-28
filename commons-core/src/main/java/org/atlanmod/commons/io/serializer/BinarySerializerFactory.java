/*
 * Copyright (c) 2017 Atlanmod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package org.atlanmod.commons.io.serializer;

import org.atlanmod.commons.annotation.Singleton;
import org.atlanmod.commons.annotation.Static;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

/**
 * A factory that creates {@link BinarySerializer} instances.
 */
@Singleton
@ParametersAreNonnullByDefault
public class BinarySerializerFactory {

    /**
     * The default {@link BinarySerializer}.
     */
    @Nonnull
    private final BinarySerializer<?> anySerializer = new ObjectBinarySerializer<>();

    /**
     * Constructs a new {@code BinarySerializerFactory}.
     */
    protected BinarySerializerFactory() {
    }

    /**
     * Returns the instance of this class.
     *
     * @return the instance of this class
     */
    @Nonnull
    public static BinarySerializerFactory getInstance() {
        return Holder.INSTANCE;
    }

    /**
     * Gets the {@link BinarySerializer} for any {@link Object}.
     *
     * @param <T> the type of (de)serialized objects
     *
     * @return a serializer
     */
    @Nonnull
    @SuppressWarnings("unchecked")
    public <T> BinarySerializer<T> forAny() {
        return (BinarySerializer<T>) anySerializer;
    }

    /**
     * The initialization-on-demand holder of the singleton of this class.
     */
    @Static
    private static final class Holder {

        /**
         * The instance of the outer class.
         */
        private static final BinarySerializerFactory INSTANCE = new BinarySerializerFactory();
    }
}
