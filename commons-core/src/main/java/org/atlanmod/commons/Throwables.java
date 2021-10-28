/*
 * Copyright (c) 2017 Atlanmod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package org.atlanmod.commons;

import org.atlanmod.commons.annotation.Static;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Static utility methods related to {@link Throwable} and {@link Exception} instances.
 */
@Static
@ParametersAreNonnullByDefault
public final class Throwables {

    private Throwables() {
        throw notInstantiableClass(getClass());
    }

    /**
     * Returns a new {@link RuntimeException} when a call leads to a state that should never have occurred under normal
     * conditions.
     *
     * @param e the catched exception
     *
     * @return a new runtime exception
     */
    @Nonnull
    public static RuntimeException shouldNeverHappen(Throwable e) {
        return new IllegalStateException("This should never have happened", e);
    }

    /**
     * Returns a new {@link RuntimeException} thrown when calling a method that is not yet implemented.
     *
     * @param methodName the name of the method not implemented
     *
     * @return a new runtime exception
     */
    @Nonnull
    public static RuntimeException notImplementedYet(String methodName) {
        return new UnsupportedOperationException(String.format("Not implemented yet: %s", methodName));
    }

    /**
     * Returns a new {@link RuntimeException} thrown when calling a constructor of a non-instantiable class.
     *
     * @param type the non-instantiable class
     *
     * @return a new runtime exception
     *
     * @see Static
     */
    @Nonnull
    public static RuntimeException notInstantiableClass(Class<?> type) {
        return new IllegalStateException(String.format("%s is not instantiable; this constructor should not be called", type.getSimpleName()));
    }
}
