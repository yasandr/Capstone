/*
 * Copyright (c) 2020 Atlanmod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */
package org.atlanmod.testing;


import org.atlanmod.commons.Throwables;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Entry point for verification methods that improve unit tests.
 * Each method in this class is a static factory for a specific verification object.
 *
 * For instance:
 *
 * <pre><code class='java'>
 * {@link Verifier#verifyEqualsOf(Class) verifyEqualsOf(String.class)}
 *      .{@link EqualsVerifier#withArguments(Object...) withArguments("a String"}
 *      .{@link EqualsVerifier#andVariants(Object...) andVariants("another String"}
 *      .{@link EqualsVerifier#check() check()}
 *
 * </code></pre>
 */
public class Verifier {
    private static final Map <Class<?>, Generator<?> > generators= new HashMap<>();
    private Verifier() {
        throw Throwables.notInstantiableClass(getClass());
    }

    /**
     * Creates a {@link EqualsVerifier} for class {@code type}.
     *
     * @param type the class whose {@code equals()} method will be verified.
     * @param <T> the actual class of the class {@type}.
     * @return an instance of {@link EqualsVerifier}.
     */
     public static <T> EqualsVerifier<T> verifyEqualsOf(Class<T> type) {
        return new EqualsVerifier<>(type);
    }


     public static <T extends Serializable> SerializationVerifier<T> verifySerializer(Class<T> type) {
        return new SerializationVerifier<>(type);
    }

    public static <T> void registerGenerator(Class <T> klass, Generator <T> generator){
         generators.put(klass, generator);
    }

}
