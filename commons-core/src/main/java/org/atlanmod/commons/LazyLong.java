/*
 * Copyright (c) 2017 Atlanmod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package org.atlanmod.commons;

import java.util.function.LongConsumer;
import java.util.function.LongSupplier;
import java.util.function.LongUnaryOperator;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

/**
 * An object that automatically loads a {@code long} by using a {@link LongSupplier} during the first call to {@link
 * #getAsLong()}.
 */
@ParametersAreNonnullByDefault
public final class LazyLong {

    /**
     * The function used to load the value.
     */
    @Nonnull
    private final LongSupplier loadFunction;

    /**
     * The embed value.
     */
    private long value;

    /**
     * Whether the value has been loaded.
     */
    private volatile boolean isLoaded;

    /**
     * Constructs a new {@code LazyLong}.
     *
     * @param loadFunction the function used to load the value
     */
    private LazyLong(LongSupplier loadFunction) {
        this.loadFunction = loadFunction;
    }

    /**
     * Creates a new lazy long with the embed value.
     *
     * @param value the embed value
     *
     * @return a new lazy object
     */
    @Nonnull
    public static LazyLong of(long value) {
        return new LazyLong(() -> value);
    }

    /**
     * Creates a new lazy long with the {@link LongSupplier} to load the embed value.
     *
     * @param loadFunction the function used to load the value
     *
     * @return a new lazy object
     */
    @Nonnull
    public static LazyLong with(LongSupplier loadFunction) {
        return new LazyLong(loadFunction);
    }

    /**
     * Gets the wrapped value. On the first call, the object is created.
     *
     * @return the value
     */
    public long getAsLong() {
        if (!isLoaded) {
            load();
        }
        return value;
    }

    /**
     * Loads the value.
     */
    private void load() {
        synchronized (loadFunction) {
            if (!isLoaded) {
                update(loadFunction.getAsLong());
            }
        }
    }

    /**
     * Updates the wrapped value.
     *
     * @param newValue the new value
     */
    public void update(long newValue) {
        value = newValue;
        isLoaded = true;
    }

    /**
     * Updates the wrapped value with a {@link LongUnaryOperator}.
     *
     * @param updateOperator the operator to update the wrapped value
     */
    public void update(LongUnaryOperator updateOperator) {
        update(updateOperator.applyAsLong(getAsLong()));
    }

    /**
     * Returns {@code true} if the value has been loaded.
     *
     * @return {@code true} if the value has been loaded
     */
    public boolean isLoaded() {
        return isLoaded;
    }

    /**
     * If a value is loaded, invoke the specified {@code consumer} with the value, otherwise do nothing.
     *
     * @param consumer action to be executed if a value is present
     */
    public void ifLoaded(LongConsumer consumer) {
        if (isLoaded) {
            consumer.accept(value);
        }
    }

    /**
     * Unloads the wrapped value. If the value is not loaded, then this method does nothing.
     */
    public void unload() {
        value = 0L;
        isLoaded = false;
    }
}
