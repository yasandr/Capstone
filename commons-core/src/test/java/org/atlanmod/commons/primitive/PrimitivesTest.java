/*
 * Copyright (c) 2017 Atlanmod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package org.atlanmod.commons.primitive;

import org.atlanmod.commons.AbstractTest;

import org.junit.jupiter.api.Test;

import java.util.Date;

import javax.annotation.ParametersAreNonnullByDefault;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * A test-case that checks the behavior of {@link Primitives}.
 */
@ParametersAreNonnullByDefault
class PrimitivesTest extends AbstractTest {

    @Test
    void testIsPrimitive() {
        assertThat(Primitives.isPrimitive(boolean.class)).isTrue();
        assertThat(Primitives.isPrimitive(Boolean.class)).isFalse();

        assertThat(Primitives.isPrimitive(char.class)).isTrue();
        assertThat(Primitives.isPrimitive(Character.class)).isFalse();

        assertThat(Primitives.isPrimitive(byte.class)).isTrue();
        assertThat(Primitives.isPrimitive(Byte.class)).isFalse();

        assertThat(Primitives.isPrimitive(short.class)).isTrue();
        assertThat(Primitives.isPrimitive(Short.class)).isFalse();

        assertThat(Primitives.isPrimitive(int.class)).isTrue();
        assertThat(Primitives.isPrimitive(Integer.class)).isFalse();

        assertThat(Primitives.isPrimitive(long.class)).isTrue();
        assertThat(Primitives.isPrimitive(Long.class)).isFalse();

        assertThat(Primitives.isPrimitive(float.class)).isTrue();
        assertThat(Primitives.isPrimitive(Float.class)).isFalse();

        assertThat(Primitives.isPrimitive(double.class)).isTrue();
        assertThat(Primitives.isPrimitive(Double.class)).isFalse();

        assertThat(Primitives.isPrimitive(String.class)).isFalse();

        assertThat(Primitives.isPrimitive(Date.class)).isFalse();
    }

    @Test
    void testIsBoxed() {
        assertThat(Primitives.isBoxed(boolean.class)).isFalse();
        assertThat(Primitives.isBoxed(Boolean.class)).isTrue();

        assertThat(Primitives.isBoxed(char.class)).isFalse();
        assertThat(Primitives.isBoxed(Character.class)).isTrue();

        assertThat(Primitives.isBoxed(byte.class)).isFalse();
        assertThat(Primitives.isBoxed(Byte.class)).isTrue();

        assertThat(Primitives.isBoxed(short.class)).isFalse();
        assertThat(Primitives.isBoxed(Short.class)).isTrue();

        assertThat(Primitives.isBoxed(int.class)).isFalse();
        assertThat(Primitives.isBoxed(Integer.class)).isTrue();

        assertThat(Primitives.isBoxed(long.class)).isFalse();
        assertThat(Primitives.isBoxed(Long.class)).isTrue();

        assertThat(Primitives.isBoxed(float.class)).isFalse();
        assertThat(Primitives.isBoxed(Float.class)).isTrue();

        assertThat(Primitives.isBoxed(double.class)).isFalse();
        assertThat(Primitives.isBoxed(Double.class)).isTrue();

        assertThat(Primitives.isBoxed(String.class)).isFalse();

        assertThat(Primitives.isBoxed(Date.class)).isFalse();
    }

    @Test
    void testIsPrimitiveOrString() {
        assertThat(Primitives.isPrimitiveOrString(boolean.class)).isTrue();
        assertThat(Primitives.isPrimitiveOrString(Boolean.class)).isTrue();

        assertThat(Primitives.isPrimitiveOrString(char.class)).isTrue();
        assertThat(Primitives.isPrimitiveOrString(Character.class)).isTrue();

        assertThat(Primitives.isPrimitiveOrString(byte.class)).isTrue();
        assertThat(Primitives.isPrimitiveOrString(Byte.class)).isTrue();

        assertThat(Primitives.isPrimitiveOrString(short.class)).isTrue();
        assertThat(Primitives.isPrimitiveOrString(Short.class)).isTrue();

        assertThat(Primitives.isPrimitiveOrString(int.class)).isTrue();
        assertThat(Primitives.isPrimitiveOrString(Integer.class)).isTrue();

        assertThat(Primitives.isPrimitiveOrString(long.class)).isTrue();
        assertThat(Primitives.isPrimitiveOrString(Long.class)).isTrue();

        assertThat(Primitives.isPrimitiveOrString(float.class)).isTrue();
        assertThat(Primitives.isPrimitiveOrString(Float.class)).isTrue();

        assertThat(Primitives.isPrimitiveOrString(double.class)).isTrue();
        assertThat(Primitives.isPrimitiveOrString(Double.class)).isTrue();

        assertThat(Primitives.isPrimitiveOrString(String.class)).isTrue();

        assertThat(Primitives.isPrimitiveOrString(Date.class)).isFalse();
    }
}