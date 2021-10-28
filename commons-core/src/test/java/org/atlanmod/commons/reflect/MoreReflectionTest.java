/*
 * Copyright (c) 2017 Atlanmod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package org.atlanmod.commons.reflect;

import org.atlanmod.commons.AbstractTest;
import org.junit.jupiter.api.Test;

import javax.annotation.ParametersAreNonnullByDefault;
import java.io.File;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@ParametersAreNonnullByDefault
class MoreReflectionTest extends AbstractTest {

    @Test
    void getAllInterfaces() {
        assertImplements(Object.class);
        assertImplements(File.class, Serializable.class, Comparable.class);
        assertImplements(ArrayList.class, List.class, Collection.class, Iterable.class, RandomAccess.class, Cloneable.class, Serializable.class);
    }

    private void assertImplements(Class<?> type, Class<?>... interfaces) {
        Set<Class<?>> set = MoreReflection.getAllInterfaces(type);
        assertThat(set).containsExactlyInAnyOrder(interfaces);
    }

    @Test
    void matchesCompatibleSignature() {
        Optional<Method> threeParametersMethod =
                Stream.of(MoreReflectionTestData.class.getMethods())
                        .filter(each -> "aMethodWithThreeParameters".equals(each.getName()))
                        .findFirst();

        Class[] args = {String.class, Integer.class, List.class};
        assertThat(MoreReflection.matches(threeParametersMethod.get(), args)).isTrue();
    }

    @Test
    void doesNotMatchIncompatibleSignature() {
        Optional<Method> threeParametersMethod =
                Stream.of(MoreReflectionTestData.class.getMethods())
                        .filter(each -> "aMethodWithThreeParameters".equals(each.getName()))
                        .findFirst();

        Class[] wrongSignature = {Integer.class, String.class, List.class};
        assertThat(MoreReflection.matches(threeParametersMethod.get(), wrongSignature)).isFalse();
    }

    @Test
    void getInstantiatorWithConstructor() {
        Function<Object[], MoreReflectionTestData> instantiator = MoreReflection.getInstantiator(MoreReflectionTestData.class,
                new Class[]{String.class});

        MoreReflectionTestData newInstance = instantiator.apply(new Object[]{"constructor"});
        assertThat(newInstance).isNotNull();
        assertThat(newInstance.name()).isEqualTo("constructor");

    }

    @Test
    void getInstantiatorWithFactoryMethod() {
        Function<Object[], MoreReflectionTestData> instantiator = MoreReflection.getInstantiator(MoreReflectionTestData.class,
                new Class[]{});

        MoreReflectionTestData newInstance = instantiator.apply(new Object[]{});
        assertThat(newInstance).isNotNull();
        assertThat(newInstance.name()).isEqualTo("factory");
    }

    @Test
    void testIsAssignable_Primitive_to_Wrapper() {
        assertThat(MoreReflection.isAssignable(Boolean.class, boolean.class)).isTrue();
        assertThat(MoreReflection.isAssignable(Byte.class, byte.class)).isTrue();
        assertThat(MoreReflection.isAssignable(Character.class, char.class)).isTrue();
        assertThat(MoreReflection.isAssignable(Double.class, double.class)).isTrue();
        assertThat(MoreReflection.isAssignable(Float.class, float.class)).isTrue();
        assertThat(MoreReflection.isAssignable(Integer.class, int.class)).isTrue();
        assertThat(MoreReflection.isAssignable(Long.class, long.class)).isTrue();
        assertThat(MoreReflection.isAssignable(Short.class, short.class)).isTrue();
    }

    @Test
    void testIsAssignable_Wrapper_to_Primitive() {
        assertThat(MoreReflection.isAssignable(boolean.class, Boolean.class)).isTrue();
        assertThat(MoreReflection.isAssignable(byte.class, Byte.class)).isTrue();
        assertThat(MoreReflection.isAssignable(char.class, Character.class)).isTrue();
        assertThat(MoreReflection.isAssignable(double.class, Double.class)).isTrue();
        assertThat(MoreReflection.isAssignable(float.class, Float.class)).isTrue();
        assertThat(MoreReflection.isAssignable(int.class, Integer.class)).isTrue();
        assertThat(MoreReflection.isAssignable(long.class, Long.class)).isTrue();
        assertThat(MoreReflection.isAssignable(short.class, Short.class)).isTrue();
    }

    @Test
    void testIsAssignable_Primitive_to_Primitive() {
        assertThat(MoreReflection.isAssignable(boolean.class, boolean.class)).isTrue();
        assertThat(MoreReflection.isAssignable(byte.class, byte.class)).isTrue();
        assertThat(MoreReflection.isAssignable(char.class, char.class)).isTrue();
        assertThat(MoreReflection.isAssignable(double.class, double.class)).isTrue();
        assertThat(MoreReflection.isAssignable(float.class, float.class)).isTrue();
        assertThat(MoreReflection.isAssignable(int.class, int.class)).isTrue();
        assertThat(MoreReflection.isAssignable(long.class, long.class)).isTrue();
        assertThat(MoreReflection.isAssignable(short.class, short.class)).isTrue();
    }

    @Test
    void testIsAssignable_Wrapper_to_Wrapper() {
        assertThat(MoreReflection.isAssignable(Boolean.class, Boolean.class)).isTrue();
        assertThat(MoreReflection.isAssignable(Byte.class, Byte.class)).isTrue();
        assertThat(MoreReflection.isAssignable(Character.class, Character.class)).isTrue();
        assertThat(MoreReflection.isAssignable(Double.class, Double.class)).isTrue();
        assertThat(MoreReflection.isAssignable(Float.class, Float.class)).isTrue();
        assertThat(MoreReflection.isAssignable(Integer.class, Integer.class)).isTrue();
        assertThat(MoreReflection.isAssignable(Long.class, Long.class)).isTrue();
        assertThat(MoreReflection.isAssignable(Short.class, Short.class)).isTrue();
    }

    @Test
    void testSoftInstantiateDoesntCallConstructor() {
        B b = MoreReflection.softInstantiate(B.class);
    }

    @Test
    void testSoftInstantiateCallsSuperConstructor() {
        B b = MoreReflection.softInstantiate(B.class, A.class);
        assertThat(b.wasCalled).isTrue();
    }
}

class MoreReflectionTestData {

    private final String name;

    public MoreReflectionTestData(String str) {
        name = str;
    }

    public void aMethodWithThreeParameters(String str, Integer i, List<String> list) {
    }

    public static MoreReflectionTestData getInstance() {
        return new MoreReflectionTestData("factory");
    }

    public String name() {
        return name;
    }
}

class A {
    boolean wasCalled = false;
    public A() {
        wasCalled = true;
    }
}

class B extends A {
    public B() {
        fail("Should not be called");
    }
}
