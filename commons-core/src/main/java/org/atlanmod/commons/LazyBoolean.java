/*
 * Copyright (c) 2017 Atlanmod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package org.atlanmod.commons;

import java.util.function.BooleanSupplier;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

/**
 * An object that automatically loads a {@code boolean} by using a {@link BooleanSupplier} during the first call to
 * {@link #getAsBoolean()}.
 */
@ParametersAreNonnullByDefault
public final class LazyBoolean {

    /**
     * The function used to load the value.
     */
    @Nonnull
    private final BooleanSupplier loadFunction;

    /**
     * The embed value.
     */
    private boolean value;

    /**
     * Whether the value has been loaded.
     */
    private volatile boolean isLoaded;

    /**
     * Constructs a new {@code LazyBoolean}.
     *
     * @param loadFunction the function used to load the value
     */
    private LazyBoolean(BooleanSupplier loadFunction) {
        this.loadFunction = loadFunction;
    }

    /**
     * Creates a new lazy boolean with the embed value.
     *
     * @param value the embed value
     *
     * @return a new lazy object
     */
    @Nonnull
    public static LazyBoolean of(boolean value) {
        return new LazyBoolean(() -> value);
    }

    /**
     * Creates a new lazy boolean with the {@link BooleanSupplier} to load the embed value.
     *
     * @param loadFunction the function used to load the value
     *
     * @return a new lazy boolean
     */
    @Nonnull
    public static LazyBoolean with(BooleanSupplier loadFunction) {
        return new LazyBoolean(loadFunction);
    }

    /**
     * Gets the wrapped value. On the first call, the object is created.
     *
     * @return the value
     */
    public boolean getAsBoolean() {
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
                update(loadFunction.getAsBoolean());
            }
        }
    }

    /**
     * Updates the wrapped value.
     *
     * @param newValue the new value
     */
    public void update(boolean newValue) {
        value = newValue;
        isLoaded = true;
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
     * Unloads the wrapped value. If the value is not loaded, then this method does nothing.
     */
    public void unload() {
        value = false;
        isLoaded = false;
    }
}
