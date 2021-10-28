/*
 * Copyright (c) 2017 Atlanmod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package org.atlanmod.commons;

import java.util.function.DoubleConsumer;
import java.util.function.DoubleSupplier;
import java.util.function.DoubleUnaryOperator;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

/**
 * An object that automatically loads a {@code double} by using a {@link DoubleSupplier} during the first call to {@link
 * #getAsDouble()}.
 */
@ParametersAreNonnullByDefault
public final class LazyDouble {

    /**
     * The function used to load the value.
     */
    @Nonnull
    private final DoubleSupplier loadFunction;

    /**
     * The embed value.
     */
    private double value;

    /**
     * Whether the value has been loaded.
     */
    private volatile boolean isLoaded;

    /**
     * Constructs a new {@code LazyDouble}.
     *
     * @param loadFunction the function used to load the value
     */
    private LazyDouble(DoubleSupplier loadFunction) {
        this.loadFunction = loadFunction;
    }

    /**
     * Creates a new lazy double with the embed value.
     *
     * @param value the embed value
     *
     * @return a new lazy object
     */
    @Nonnull
    public static LazyDouble of(double value) {
        return new LazyDouble(() -> value);
    }

    /**
     * Creates a new lazy double with the {@link DoubleSupplier} to load the embed value.
     *
     * @param loadFunction the function used to load the value
     *
     * @return a new lazy object
     */
    @Nonnull
    public static LazyDouble with(DoubleSupplier loadFunction) {
        return new LazyDouble(loadFunction);
    }

    /**
     * Gets the wrapped value. On the first call, the object is created.
     *
     * @return the value
     */
    public double getAsDouble() {
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
                update(loadFunction.getAsDouble());
            }
        }
    }

    /**
     * Updates the wrapped value.
     *
     * @param newValue the new value
     */
    public void update(double newValue) {
        value = newValue;
        isLoaded = true;
    }

    /**
     * Updates the wrapped value with a {@link DoubleUnaryOperator}.
     *
     * @param updateOperator the operator to update the wrapped value
     */
    public void update(DoubleUnaryOperator updateOperator) {
        update(updateOperator.applyAsDouble(getAsDouble()));
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
    public void ifLoaded(DoubleConsumer consumer) {
        if (isLoaded) {
            consumer.accept(value);
        }
    }

    /**
     * Unloads the wrapped value. If the value is not loaded, then this method does nothing.
     */
    public void unload() {
        value = 0D;
        isLoaded = false;
    }
}
