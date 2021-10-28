/*
 * Copyright (c) 2020 Atlanmod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */
package org.atlanmod.testing;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Stream;
import org.atlanmod.commons.reflect.MoreReflection;

/**
 * Verifies that the {@code equals()} method of a class was correctly implemented, checking that:
 *
 * - An object is not equal to {@code null}
 * - An object is equal to self
 * - An object is equal to another similar object (created with the same arguments)
 * - An object is not equal to objects created with different arguments
 *
 * @param <T>
 */
public class EqualsVerifier<T> {
    private final Class<T> type;
    private Object[] arguments;
    private Object[] variants;

    /**
     * Creates an instance of {@code EqualsVerifier} for class {@code type}.
     *
     * @param type the class to be verified.
     */
    EqualsVerifier(Class<T> type) {
        this.type = type;
    }

    /**
     * Arguments are used to create a reference instance of class {@code type}. The reference instance is used
     * to the comparisons to self, null and to another instance created with the same arguments.
     *
     * The types and the length of {@code arguments} must match either a visible constructor or a static
     * method (a factory) that returns an instance of {@code type}.
     *
     *
     * @param arguments an array of instances of {@link Object}.
     * @return the current verifier.
     */
    public EqualsVerifier<T> withArguments(Object... arguments) {
        this.arguments = arguments;
        return this;
    }

    /**
     * Variants are used to create instances of {@code type} that are different from the reference instance.
     * Each different instance is created with 1 element of {@code variants}.
     *
     * For instance, if arguments = {a, b, c} and variants = {d, e, f}, three different instances will be created:
     *
     * - Type(d, b, c)
     * - Type(a, e, c) and
     * - Type(a, b, f)
     *
     * The length of {@code variants} must be exactly the same as the length of {@code arguments}.
     * The type of the elements of {@code variants} can be different from those of {@code arguments}.
     * However, they must be able to assign the correspondent parameter of the constructor or factory method.
     *
     * @param variants
     * @return
     */
    public EqualsVerifier<T> andVariants(Object... variants) {
        this.variants = variants;
        return this;
    }

    /**
     * Verifies the implementation of method {@code equals()}.
     *
     */
    public void check() {   
        checkArguments(arguments, variants);
        Class[] argumentTypes = mapToClasses(arguments);

        Function<Object[], T> instantiator = MoreReflection.getInstantiator(type, argumentTypes);

        Object[] freaks = new Object[arguments.length];
        for (int i = 0; i < arguments.length; i++) {
            Object[] variation = Arrays.copyOf(arguments, arguments.length);
            variation[i] = variants[i];
            freaks[i] = instantiator.apply(variation);
        }

        Object one = instantiator.apply(arguments);
        Object clone = instantiator.apply(arguments);

        assertIsEqualsToSelf(one);
        assertIsEqual(one, clone);
        assertNotEqualToNull(one);
        for (Object each : freaks) {
            assertNotEqual(one,each);
        }
    }

    private  void checkArguments(Object[] arguments, Object[] variants) {
        int length = arguments.length;
        if (variants.length != length) {
            throw new IllegalArgumentException("Argument arrays must have the same length");
        }
        for (int i = 0; i < length; i++) {
            if (arguments[i].equals(variants[i])) {
                throw new IllegalArgumentException("Argument arrays must have different elements");
            }
        }
    }

    private static Class[] mapToClasses(Object[] objects) {
        return Stream.of(objects)
            .map(Object::getClass)
            .toArray(Class[]::new);
    }

    public static void assertIsEqual(Object one, Object other) {
        if (!one.equals(other)) {
            throw new AssertionError("Expecting objects to be equal");
        } else if (!other.equals(one)) {
            throw new AssertionError("Equals is supposed to be symmetric");
        } else if (one.hashCode() != other.hashCode()) {
            throw new AssertionError("Equal objects must have the same hash code");
        }
    }

    public static void assertNotEqual(Object one, Object other) {
        if (one.equals(other)) {
            throw new AssertionError("Expecting objects NOT to be equal");
        }
    }

    public static void assertNotEqualToNull(Object one) {
        if (one.equals(null)) {
            throw new AssertionError("Non-null objets should not be equal to null");
        }
    }

    @SuppressWarnings("EqualsWithItself")
    public static void assertIsEqualsToSelf(Object one) {
        if (!one.equals(one)) {
            throw new AssertionError("Object should be equal to itself");
        }
    }
}
