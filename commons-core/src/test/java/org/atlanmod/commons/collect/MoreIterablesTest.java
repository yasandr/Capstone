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
import java.util.Arrays;
import java.util.Collections;

import javax.annotation.ParametersAreNonnullByDefault;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

/**
 * A test-case that checks the behavior of {@link MoreIterables}.
 */
@ParametersAreNonnullByDefault
class MoreIterablesTest extends AbstractTest {

    @Test
    void testConstructor() throws Exception {
        Constructor<?> constructor = MoreIterables.class.getDeclaredConstructor();
        assertThat(Modifier.isPrivate(constructor.getModifiers())).isTrue();

        constructor.setAccessible(true);

        assertThat(catchThrowable(constructor::newInstance))
                .isInstanceOf(InvocationTargetException.class)
                .hasCauseExactlyInstanceOf(IllegalStateException.class);
    }

    @Test
    void testIsEmptyWithIterator() {
        Iterable<Integer> iterable0 = () -> Collections.<Integer>emptyIterator();
        assertThat(MoreIterables.isEmpty(iterable0)).isTrue();

        Iterable<Integer> iterable1 = () -> Collections.singletonList(0).iterator();
        assertThat(MoreIterables.isEmpty(iterable1)).isFalse();

        Iterable<Integer> iterable2 = () -> Arrays.asList(0, 1).iterator();
        assertThat(MoreIterables.isEmpty(iterable2)).isFalse();
    }

    @Test
    void testIsNotEmptyWithIterator() {
        Iterable<Integer> iterable0 = () -> Collections.<Integer>emptyIterator();
        assertThat(MoreIterables.notEmpty(iterable0)).isFalse();

        Iterable<Integer> iterable1 = () -> Collections.singletonList(0).iterator();
        assertThat(MoreIterables.notEmpty(iterable1)).isTrue();

        Iterable<Integer> iterable2 = () -> Arrays.asList(0, 1).iterator();
        assertThat(MoreIterables.notEmpty(iterable2)).isTrue();
    }

    @Test
    void testIsEmptyWithList() {
        Iterable<Integer> iterable0 = Collections.emptyList();
        assertThat(MoreIterables.isEmpty(iterable0)).isTrue();

        Iterable<Integer> iterable1 = Collections.singletonList(0);
        assertThat(MoreIterables.isEmpty(iterable1)).isFalse();

        Iterable<Integer> iterable2 = Arrays.asList(0, 1);
        assertThat(MoreIterables.isEmpty(iterable2)).isFalse();
    }

    @Test
    void testIsNotEmptyWithList() {
        Iterable<Integer> iterable0 = Collections.emptyList();
        assertThat(MoreIterables.notEmpty(iterable0)).isFalse();

        Iterable<Integer> iterable1 = Collections.singletonList(0);
        assertThat(MoreIterables.notEmpty(iterable1)).isTrue();

        Iterable<Integer> iterable2 = Arrays.asList(0, 1);
        assertThat(MoreIterables.notEmpty(iterable2)).isTrue();
    }
}