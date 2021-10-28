/*
 * Copyright (c) 2017 Atlanmod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package org.atlanmod.commons.reflect;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Exception thrown when an error occurred when using reflection.
 */
@ParametersAreNonnullByDefault
public class ReflectionException extends RuntimeException {

    private static final long serialVersionUID = 408359593875469468L;

    /**
     * Constructs an {@code ReflectionException} with {@code null} as its error detail message.
     */
    public ReflectionException() {
    }

    /**
     * Constructs an {@code ReflectionException} with the specified detail {@code message}.
     *
     * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method)
     */
    public ReflectionException(String message) {
        super(message);
    }

    /**
     * Constructs an {@code ReflectionException} with the specified {@code cause} and its detail message.
     *
     * @param cause the cause (which is saved for later retrieval by the {@link #getCause()} method). (A {@code null}
     *              value is permitted, and indicates that the cause is nonexistent or unknown.)
     */
    public ReflectionException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs an {@code ReflectionException} with the specified detail {@code message} and {@code cause}.
     * <p>
     * The detail message associated with cause is not automatically incorporated into this exception's detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method)
     * @param cause   the cause (which is saved for later retrieval by the {@link #getCause()} method). (A {@code null}
     *                value is permitted, and indicates that the cause is nonexistent or unknown.)
     */
    public ReflectionException(String message, Throwable cause) {
        super(message, cause);
    }
}