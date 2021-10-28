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

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.ParametersAreNonnullByDefault;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * A test-case that checks the behavior of {@link Bytes}.
 */
@ParametersAreNonnullByDefault
class BytesTest extends AbstractTest {

    @Test
    void testToBoolean() {
        boolean actual0 = Bytes.toBoolean(Booleans.toBytes(Boolean.TRUE));
        assertThat(actual0).isTrue();

        boolean actual1 = Bytes.toBoolean(Booleans.toBytes(Boolean.FALSE));
        assertThat(actual1).isFalse();
    }

    @Test
    void testToShort() {
        final short expected0 = 28433;
        byte[] bytes = ByteBuffer.allocate(Short.BYTES).putShort(expected0).array();
        short actual0 = Bytes.toShort(bytes);
        assertThat(actual0).isEqualTo(expected0);
    }

    @Test
    void testToChar() {
        final char expected0 = 'N';
        byte[] bytes = ByteBuffer.allocate(Character.BYTES).putChar(expected0).array();
        char actual0 = Bytes.toChar(bytes);
        assertThat(actual0).isEqualTo(expected0);
    }

    @Test
    void testToInt() {
        final int expected0 = 1654125381;
        byte[] bytes = ByteBuffer.allocate(Integer.BYTES).putInt(expected0).array();
        int actual0 = Bytes.toInt(bytes);
        assertThat(actual0).isEqualTo(expected0);
    }

    @Test
    void testToLong() {
        final long long0 = 1354566516474223156L;
        byte[] bytes = ByteBuffer.allocate(Long.BYTES).putLong(long0).array();
        long actual0 = Bytes.toLong(bytes);
        assertThat(actual0).isEqualTo(long0);
    }

    @Test
    void testToFloat() {
        final float expected0 = 139895433915.09579569E18f;
        byte[] bytes = ByteBuffer.allocate(Float.BYTES).putFloat(expected0).array();
        float actual0 = Bytes.toFloat(bytes);
        assertThat(actual0).isEqualTo(expected0);
    }

    @Test
    void testToDouble() {
        final double expected0 = 19876412.08910810486479E196;
        byte[] bytes = ByteBuffer.allocate(Double.BYTES).putDouble(expected0).array();
        double actual0 = Bytes.toDouble(bytes);
        assertThat(actual0).isEqualTo(expected0);
    }

    @Test
    void testToString() {
        final String expected0 = "AtlanmodIsAwesome!";
        byte[] bytes = expected0.getBytes();
        String actual0 = Bytes.toString(bytes);
        assertThat(actual0).isEqualTo(expected0);
    }

    @Test
    void testToStringBinaryAndReverse() {
        String expected0 = "AtlanmodIsAwesome!";
        byte[] bytes = Strings.toBytes(expected0);

        String actual0 = Bytes.toStringBinary(bytes);
        assertThat(actual0).isEqualTo("41746c616e6d6f644973417765736f6d6521");

        byte[] actualBytes0 = Strings.toBytesBinary(actual0);
        assertThat(actualBytes0).contains(bytes);

        assertThat(Bytes.toString(actualBytes0)).isEqualTo(expected0);
    }

    @Test
    void testAsList() {
        byte[] bytes = new byte[] {1, 2, 3, 4, 5};

        final List<Byte> actual = Bytes.asList(bytes);
        assertThat(actual.size()).isEqualTo(bytes.length);

        List<Byte> expected = new ArrayList<>(bytes.length);
        for(byte each : bytes) {
            expected.add(each);
        }

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void testAsListEmpty() {
        final List<Byte> actual = Bytes.asList();
        assertThat(actual).isEmpty();
    }

    @Test
    void testToArray() {
        byte[] expected = new byte[] {1, 2, 3, 4, 5};

        List<Byte> expectedAsList = new ArrayList<>();
        for(byte each : expected) {
            expectedAsList.add(each);
        }

        assertThat(expectedAsList.size()).isEqualTo(expected.length);

        final byte[] actual = Bytes.toArray(expectedAsList);
        assertThat(actual.length).isEqualTo(expected.length);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void testToArrayEmpty() {
        final byte[] actual = Bytes.toArray(Collections.emptyList());
        assertThat(actual).isEmpty();
    }
}