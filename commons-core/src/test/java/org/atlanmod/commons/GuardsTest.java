/*
 * Copyright (c) 2017 Atlanmod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package org.atlanmod.commons;

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
 * A test-case that checks the behavior of {@link Guards}.
 */
@ParametersAreNonnullByDefault
class GuardsTest extends AbstractTest {

    @Test
    void testConstructor() throws Exception {
        Constructor<?> constructor = Guards.class.getDeclaredConstructor();
        assertThat(Modifier.isPrivate(constructor.getModifiers())).isTrue();

        constructor.setAccessible(true);

        assertThat(catchThrowable(constructor::newInstance))
                .isInstanceOf(InvocationTargetException.class)
                .hasCauseExactlyInstanceOf(IllegalStateException.class);
    }

    @Test
    void testCheckArgument() {
        assertThat(catchThrowable(() -> Guards.checkArgument(true)))
                .isNull();

        assertThat(catchThrowable(() -> Guards.checkArgument(false)))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasNoCause()
                .hasMessage(null);
    }

    @Test
    void testCheckArgumentWithMessage() {
        assertThat(catchThrowable(() -> Guards.checkArgument(true, "Message0")))
                .isNull();

        assertThat(catchThrowable(() -> Guards.checkArgument(false, "Message0")))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasNoCause()
                .hasMessage("Message0");
    }

    @Test
    void testCheckArgumentWithPattern() {
        assertThat(catchThrowable(() -> Guards.checkArgument(true, "Message%d", 0)))
                .isNull();

        assertThat(catchThrowable(() -> Guards.checkArgument(false, "Message%d", 0)))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasNoCause()
                .hasMessage("Message0");
    }

    @Test
    void testCheckState() {
        assertThat(catchThrowable(() -> Guards.checkState(true)))
                .isNull();

        assertThat(catchThrowable(() -> Guards.checkState(false)))
                .isExactlyInstanceOf(IllegalStateException.class)
                .hasNoCause()
                .hasMessage(null);
    }

    @Test
    void testCheckStateWithMessage() {
        assertThat(catchThrowable(() -> Guards.checkState(true, "Message0")))
                .isNull();

        assertThat(catchThrowable(() -> Guards.checkState(false, "Message0")))
                .isExactlyInstanceOf(IllegalStateException.class)
                .hasNoCause()
                .hasMessage("Message0");
    }

    @Test
    void testCheckStateWithPattern() {
        assertThat(catchThrowable(() -> Guards.checkState(true, "Message%d", 0)))
                .isNull();

        assertThat(catchThrowable(() -> Guards.checkState(false, "Message%d", 0)))
                .isExactlyInstanceOf(IllegalStateException.class)
                .hasNoCause()
                .hasMessage("Message0");
    }

    @Test
    void testCheckNotNull() {
        assertThat(catchThrowable(() -> Guards.checkNotNull(new Object())))
                .isNull();

        assertThat(catchThrowable(() -> Guards.checkNotNull(null)))
                .isExactlyInstanceOf(NullPointerException.class)
                .hasNoCause()
                .hasMessage(null);
    }

    @Test
    void testCheckNotNullWithMessage() {
        assertThat(catchThrowable(() -> Guards.checkNotNull(new Object(), "Message0")))
                .isNull();

        assertThat(catchThrowable(() -> Guards.checkNotNull(null, "Message0")))
                .isExactlyInstanceOf(NullPointerException.class)
                .hasNoCause()
                .hasMessage("Message0");
    }

    @Test
    void testCheckNotNullWithPattern() {
        assertThat(catchThrowable(() -> Guards.checkNotNull(new Object(), "Message%d", 0)))
                .isNull();

        assertThat(catchThrowable(() -> Guards.checkNotNull(null, "Message%d", 0)))
                .isExactlyInstanceOf(NullPointerException.class)
                .hasNoCause()
                .hasMessage("Message0");
    }

    @Test
    void testCheckNotContainsNull() {
        assertThat(catchThrowable(() -> Guards.checkNotContainsNull(Collections.emptyList())))
                .isNull();

        assertThat(catchThrowable(() -> Guards.checkNotContainsNull(Collections.singletonList(0))))
                .isNull();

        assertThat(catchThrowable(() -> Guards.checkNotContainsNull(Arrays.asList(0, 1, 2, 3))))
                .isNull();

        assertThat(catchThrowable(() -> Guards.checkNotContainsNull(Arrays.asList(0, 1, null, 2))))
                .isExactlyInstanceOf(NullPointerException.class)
                .hasNoCause()
                .hasMessage("the collection contains at least one null element");
    }

    @Test
    void testCheckElementIndex() {
        // index < 0
        assertThat(catchThrowable(() -> Guards.checkElementIndex(-1, 0)))
                .isExactlyInstanceOf(IndexOutOfBoundsException.class)
                .hasNoCause()
                .hasMessage("index (-1) must not be negative");

        // size < 0
        assertThat(catchThrowable(() -> Guards.checkElementIndex(0, -1)))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasNoCause()
                .hasMessage("size (-1) must not be negative");

        // index == size
        assertThat(catchThrowable(() -> Guards.checkElementIndex(0, 0)))
                .isExactlyInstanceOf(IndexOutOfBoundsException.class)
                .hasNoCause()
                .hasMessage("index (0) must be less than size (0)");

        // index < size
        assertThat(catchThrowable(() -> Guards.checkElementIndex(0, 1)))
                .isNull();

        // index > size
        assertThat(catchThrowable(() -> Guards.checkElementIndex(1, 0)))
                .isExactlyInstanceOf(IndexOutOfBoundsException.class)
                .hasNoCause()
                .hasMessage("index (1) must be less than size (0)");
    }

    @Test
    void testCheckPositionIndex() {
        // index < 0
        assertThat(catchThrowable(() -> Guards.checkPositionIndex(-1, 0)))
                .isExactlyInstanceOf(IndexOutOfBoundsException.class)
                .hasNoCause()
                .hasMessage("index (-1) must not be negative");

        // size < 0
        assertThat(catchThrowable(() -> Guards.checkPositionIndex(0, -1)))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasNoCause()
                .hasMessage("size (-1) must not be negative");

        // index == size
        assertThat(catchThrowable(() -> Guards.checkPositionIndex(0, 0)))
                .isNull();

        // index < size
        assertThat(catchThrowable(() -> Guards.checkPositionIndex(0, 1)))
                .isNull();

        // index > size
        assertThat(catchThrowable(() -> Guards.checkPositionIndex(1, 0)))
                .isExactlyInstanceOf(IndexOutOfBoundsException.class)
                .hasNoCause()
                .hasMessage("index (1) must not be greater than size (0)");
    }

    @Test
    void testCheckEqualTo() {
        assertThat(catchThrowable(() -> Guards.checkEqualTo(null, null)))
                .isNull();

        assertThat(catchThrowable(() -> Guards.checkEqualTo(10, 10)))
                .isNull();

        assertThat(catchThrowable(() -> Guards.checkEqualTo(10, 0)))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasNoCause()
                .hasMessage("value (10) must be equal to 0");
    }

    @Test
    void testCheckLessThan() {
        // value > upperBound
        assertThat(catchThrowable(() -> Guards.checkLessThan(10, 9)))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasNoCause()
                .hasMessage("value (10) must be less than 9");

        // value == upperBound
        assertThat(catchThrowable(() -> Guards.checkLessThan(10, 10)))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasNoCause()
                .hasMessage("value (10) must be less than 10");

        // value < upperBound
        assertThat(catchThrowable(() -> Guards.checkLessThan(10, 11)))
                .isNull();
    }

    @Test
    void testCheckLessThanOrEqualTo() {
        // value > upperBound
        assertThat(catchThrowable(() -> Guards.checkLessThanOrEqualTo(10, 9)))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasNoCause()
                .hasMessage("value (10) must not be greater than 9");

        // value == upperBound
        assertThat(catchThrowable(() -> Guards.checkLessThanOrEqualTo(10, 10)))
                .isNull();

        // value < upperBound
        assertThat(catchThrowable(() -> Guards.checkLessThanOrEqualTo(10, 11)))
                .isNull();
    }

    @Test
    void testCheckGreaterThan() {
        // value > upperBound
        assertThat(catchThrowable(() -> Guards.checkGreaterThan(10, 9)))
                .isNull();

        // value == upperBound
        assertThat(catchThrowable(() -> Guards.checkGreaterThan(10, 10)))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasNoCause()
                .hasMessage("value (10) must be greater than 10");

        // value < upperBound
        assertThat(catchThrowable(() -> Guards.checkGreaterThan(10, 11)))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasNoCause()
                .hasMessage("value (10) must be greater than 11");
    }

    @Test
    void testCheckGreaterThanOrEqualTo() {
        // value > upperBound
        assertThat(catchThrowable(() -> Guards.checkGreaterThanOrEqualTo(10, 9)))
                .isNull();

        // value == upperBound
        assertThat(catchThrowable(() -> Guards.checkGreaterThanOrEqualTo(10, 10)))
                .isNull();

        // value < upperBound
        assertThat(catchThrowable(() -> Guards.checkGreaterThanOrEqualTo(10, 11)))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasNoCause()
                .hasMessage("value (10) must not be less than 11");
    }
}