/*
 * Copyright (c) 2017 Atlanmod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package org.atlanmod.commons.collect;

import org.atlanmod.commons.AbstractTest;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import javax.annotation.ParametersAreNonnullByDefault;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

/**
 * A test-case that checks the behavior of {@link MoreArrays}.
 */
@ParametersAreNonnullByDefault
class MoreArraysTest extends AbstractTest {

    @Test
    void testConstructor() throws Exception {
        Constructor<?> constructor = MoreArrays.class.getDeclaredConstructor();
        assertThat(Modifier.isPrivate(constructor.getModifiers())).isTrue();

        constructor.setAccessible(true);

        assertThat(catchThrowable(constructor::newInstance))
                .isInstanceOf(InvocationTargetException.class)
                .hasCauseExactlyInstanceOf(IllegalStateException.class);
    }

    @Test
    void testNewArray() {
        Object[] array0 = MoreArrays.newArray(Object.class, 1);
        assertThat(array0).isExactlyInstanceOf(Object[].class);
        assertThat(array0).hasSize(1);

        String[] array1 = MoreArrays.newArray(String.class, 2);
        assertThat(array1).isExactlyInstanceOf(String[].class)
                .hasSize(2);
    }

    @Test
    void testInvalidNewArray() {
        //noinspection ConstantConditions
        assertThat(catchThrowable(() -> MoreArrays.newArray(null, 0)))
                .isExactlyInstanceOf(NullPointerException.class);

        assertThat(catchThrowable(() -> MoreArrays.newArray(Object.class, -1)))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void testResize() {
        Integer[] array0 = new Integer[]{0, 1, 2, 3};
        assertThat(array0).hasSize(4);

        Integer[] array1 = MoreArrays.resize(array0, 5);
        assertThat(array0).hasSize(4);
        assertThat(array1).hasSize(5);

        assertThat(array1).containsExactly(0, 1, 2, 3, null);

        // newLength < length
        assertThat(catchThrowable(() -> MoreArrays.resize(array1, 2)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void testAppend() {
        Integer[] array0 = new Integer[0];
        assertThat(array0).hasSize(0);

        // Insert first
        Integer[] array1 = MoreArrays.append(array0, 0);
        assertThat(array1).hasSize(1);
        assertThat(array1[0]).isEqualTo(0);

        // First array must not change
        assertThat(array0).hasSize(0);

        // Insert before
        Integer[] array2 = MoreArrays.append(array1, 1);
        assertThat(array2).hasSize(2);
        assertThat(array2[0]).isEqualTo(0);
        assertThat(array2[1]).isEqualTo(1);
    }

    @Test
    void testAdd() {
        Integer[] array0 = new Integer[0];
        assertThat(array0).hasSize(0);

        // Insert first
        Integer[] array1 = MoreArrays.add(array0, 0, 0);
        assertThat(array1).hasSize(1);
        assertThat(array1).containsExactly(0);

        // First array must not change
        assertThat(array0).hasSize(0);

        // Insert before
        Integer[] array2 = MoreArrays.add(array1, 0, 1);
        assertThat(array2).hasSize(2);
        assertThat(array2).containsExactly(1, 0);

        // Insert after
        Integer[] array3 = MoreArrays.add(array2, 2, 2);
        assertThat(array3).hasSize(3);
        assertThat(array3).containsExactly(1, 0, 2);

        // index > size
        assertThat(catchThrowable(() -> MoreArrays.add(array3, 10, 10)))
                .isInstanceOf(IndexOutOfBoundsException.class);
    }

    @Test
    void testAddAllBytes() {
        byte[] array1 = new byte[] {6,5,4,3,2,1};
        byte[] array2 = new byte[] {1, 2, 3};
        byte[] expected = new byte[] {6,5,4,3,2,1,1,2,3};

        byte[] joinedArray = MoreArrays.addAll(array1, array2);

        assertThat(joinedArray).isEqualTo(expected);
    }

    @Test
    void testRemove() {
        Integer[] array0 = new Integer[]{0, 1, 2, 3, 4};
        assertThat(array0).hasSize(5);

        // Remove first
        Integer[] array1 = MoreArrays.remove(array0, 0);
        assertThat(array1).hasSize(4);
        assertThat(array1).containsExactly(1, 2, 3, 4);

        // First array must not change
        assertThat(array0).hasSize(5);

        // Remove middle
        Integer[] array2 = MoreArrays.remove(array1, 2);
        assertThat(array2).hasSize(3);
        assertThat(array2).containsExactly(1, 2, 4);

        // Remove last
        Integer[] array3 = MoreArrays.remove(array2, 2);
        assertThat(array3).hasSize(2);
        assertThat(array3).containsExactly(1, 2);

        // index > size
        assertThat(catchThrowable(() -> MoreArrays.remove(array3, 10)))
                .isInstanceOf(IndexOutOfBoundsException.class);
    }

    @Test
    void testContains() {
        Integer[] array0 = new Integer[]{0, 1, 2, 3, 4};

        assertThat(MoreArrays.contains(array0, 0)).isTrue();
        assertThat(MoreArrays.contains(array0, 2)).isTrue();
        assertThat(MoreArrays.contains(array0, 4)).isTrue();

        assertThat(MoreArrays.contains(array0, 10)).isFalse();
    }

    @Test
    void testIndexOf() {
        Integer[] array0 = new Integer[]{0, 1, 2, 2, 0, 1};

        assertThat(MoreArrays.indexOf(array0, 0)).isEqualTo(0);
        assertThat(MoreArrays.indexOf(array0, 1)).isEqualTo(1);
        assertThat(MoreArrays.indexOf(array0, 2)).isEqualTo(2);

        assertThat(MoreArrays.indexOf(array0, 10)).isEqualTo(MoreArrays.NO_INDEX);
    }

    @Test
    void testLastIndexOf() {
        Integer[] array0 = new Integer[]{0, 1, 2, 2, 0, 1};

        assertThat(MoreArrays.lastIndexOf(array0, 0)).isEqualTo(4);
        assertThat(MoreArrays.lastIndexOf(array0, 1)).isEqualTo(5);
        assertThat(MoreArrays.lastIndexOf(array0, 2)).isEqualTo(3);

        assertThat(MoreArrays.lastIndexOf(array0, 10)).isEqualTo(MoreArrays.NO_INDEX);
    }

    @Test
    void testBytesToPrimitive() {
        Byte[] boxedArray = new Byte[] {0, 1, 2, 3, 4};
        byte[] primitiveArray = new byte[] {0, 1, 2, 3, 4};

        assertThat(primitiveArray).isEqualTo(MoreArrays.toPrimitive(boxedArray));
    }

    @Test
    void testBytesToObject() {
        Byte[] boxedArray = new Byte[] {0, 1, 2, 3, 4};
        byte[] primitiveArray = new byte[] {0, 1, 2, 3, 4};

        assertThat(boxedArray).isEqualTo(MoreArrays.toObject(primitiveArray));
    }

    @Test
    void testHead() {
        Integer[] array0 = new Integer[]{0, 1, 2, 2, 0, 1};
        assertThat(MoreArrays.head(array0)).isEqualTo(array0[0]);
    }

    @Test
    void testTail() {
        Integer[] data = new Integer[]{0, 1, 2, 2, 0, 1};
        Integer[] expected = new Integer[]{1, 2, 2, 0, 1};

        assertThat(MoreArrays.tail(data)).isEqualTo(expected);
    }

    @Test
    void testEmptyTail() {
        Integer[] data = new Integer[]{0};
        Integer[] expected = new Integer[]{};

        assertThat(MoreArrays.tail(data)).isEqualTo(expected);
    }
}