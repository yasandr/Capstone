/*
 * Copyright (c) 2017 Atlanmod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package org.atlanmod.commons;

import java.util.function.IntConsumer;
import java.util.function.IntSupplier;
import java.util.function.IntUnaryOperator;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

/**
 * An object that automatically loads an {@code integer} by using a {@link IntSupplier} during the first call to {@link
 * #getAsInt()}.
 */
@ParametersAreNonnullByDefault
public final class LazyInt {

    /**
     * The function used to load the value.
     */
    @Nonnull
    private final IntSupplier loadFunction;

    /**
     * The embed value.
     */
    private int value;

    /**
     * Whether the value has been loaded.
     */
    private volatile boolean isLoaded;

    /**
     * Constructs a new {@code LazyInt}.
     *
     * @param loadFunction the function used to load the value
     */
    private LazyInt(IntSupplier loadFunction) {
        this.loadFunction = loadFunction;
    }

    /**
     * Creates a new lazy integer with the embed value.
     *
     * @param value the embed value
     *
     * @return a new lazy object
     */
    @Nonnull
    public static LazyInt of(int value) {
        return new LazyInt(() -> value);
    }

    /**
     * Creates a new lazy integer with the {@link IntSupplier} to load the embed value.
     *
     * @param loadFunction the function used to load the value
     *
     * @return a new lazy object
     */
    @Nonnull
    public static LazyInt with(IntSupplier loadFunction) {
        return new LazyInt(loadFunction);
    }

    /**
     * Gets the wrapped value. On the first call, the object is created.
     *
     * @return the value
     */
    public int getAsInt() {
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
                update(loadFunction.getAsInt());
            }
        }
    }

    /**
     * Updates the wrapped value.
     *
     * @param newValue the new value
     */
    public void update(int newValue) {
        value = newValue;
        isLoaded = true;
    }

    /**
     * Updates the wrapped value with a {@link IntUnaryOperator}.
     *
     * @param updateOperator the operator to update the wrapped value
     */
    public void update(IntUnaryOperator updateOperator) {
        update(updateOperator.applyAsInt(getAsInt()));
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
    public void ifLoaded(IntConsumer consumer) {
        if (isLoaded) {
            consumer.accept(value);
        }
    }

    /**
     * Unloads the wrapped value. If the value is not loaded, then this method does nothing.
     */
    public void unload() {
        value = 0;
        isLoaded = false;
    }
}
