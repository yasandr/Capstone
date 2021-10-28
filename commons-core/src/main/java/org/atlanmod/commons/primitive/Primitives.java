/*
 * Copyright (c) 2017 Atlanmod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package org.atlanmod.commons.primitive;

import org.atlanmod.commons.Throwables;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nonnull;

/**
 * Static utility methods related to Java primitive types.
 */
public final class Primitives {

    /**
     * A set of primitive types.
     */
    @Nonnull
    private static final Set<Class<?>> PRIMITIVE_TYPES = new HashSet<>();

    static {
        PRIMITIVE_TYPES.add(Boolean.class);
        PRIMITIVE_TYPES.add(Character.class);
        PRIMITIVE_TYPES.add(Byte.class);
        PRIMITIVE_TYPES.add(Short.class);
        PRIMITIVE_TYPES.add(Integer.class);
        PRIMITIVE_TYPES.add(Long.class);
        PRIMITIVE_TYPES.add(Float.class);
        PRIMITIVE_TYPES.add(Double.class);
        PRIMITIVE_TYPES.add(Void.class);
    }

    private Primitives() {
        throw Throwables.notInstantiableClass(getClass());
    }

    /**
     * Returns {@code true} if the {@code type} is considered as a Java primitive type, or its boxed equivalent, or a
     * {@link String} type.
     *
     * @param type the type to check
     *
     * @return {@code true} if the {@code type} is considered as a primitive type.
     */
    public static boolean isPrimitiveOrString(Class<?> type) {
        return isPrimitive(type) || type == String.class || isBoxed(type);
    }

    /**
     * Returns {@code true} if the {@code type} is a boxed Java primitive type.
     *
     * @param type the type to check
     *
     * @return {@code true} if the {@code type} is a boxed Java primitive type
     */
    public static boolean isBoxed(Class<?> type) {
        return PRIMITIVE_TYPES.contains(type);
    }

    /**
     * Returns {@code true} if the {@code type} is a Java primitive type.
     *
     * @param type the type to check
     *
     * @return {@code true} if the {@code type} is a Java primitive type
     */
    public static boolean isPrimitive(Class<?> type) {
        return type.isPrimitive();
    }
}
