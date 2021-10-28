/*
 * Copyright (c) 2021 Naomod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */
package org.atlanmod.commons.io;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.nio.ByteBuffer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.atlanmod.commons.io.Numbers.uvarint;

class UnsignedVarIntTest {

    @Test
    void testToBytes() {
        UnsignedVarInt i = uvarint(0x80);
        byte[] expected = new byte[]{(byte) 0x80, 0x01};
        assertThat(i.toBytes()).isEqualTo(expected);
    }

    @Test
    void testToBytes0xFF() {
        UnsignedVarInt i = uvarint(0xFFL);
        byte[] expected = new byte[]{(byte) 0xFF, 0x01};
        assertThat(i.toBytes()).isEqualTo(expected);
    }

    @Test
    void testToBytes0xFFFF() {
        UnsignedVarInt i = uvarint(0xFFFFL);
        byte[] expected = new byte[]{(byte) 0xFF, (byte) 0xFF, 0x03};
        assertThat(i.toBytes()).isEqualTo(expected);
    }

    @Test
    void testToBytes0xFFFFFFFF() {
        UnsignedVarInt i = uvarint(0xFFFFFFFFL);
        byte[] expected = new byte[]{(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, 0x0F};
        assertThat(i.toBytes()).isEqualTo(expected);
    }

    @Test
    void testFromBytes() {
        UnsignedVarInt expected = uvarint(0x80);
        UnsignedVarInt actual = UnsignedVarInt.fromBytes(new byte[]{(byte) 0x80, 0x01});
        assertThat(actual).isEqualTo(expected);
    }


    @Test
    void testFromBytes0xFFFFFFFFL() {
        UnsignedVarInt expected = uvarint(0xFFFFFFFFL);
        UnsignedVarInt actual = UnsignedVarInt.fromBytes(new byte[]{(byte) 0xFF,
                (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, 0x0F});
        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @ValueSource(longs = {0, 0x7FL, 0x80L, 0xFFL, 0XFFFFL, 0xFFFFFFL, 0xFFFFFFFFL, UnsignedVarInt.MAX_VALUE})
    void testToBytesAndBack(long value) {
        UnsignedVarInt expected = uvarint(value);
        byte[] bytes = expected.toBytes();

        UnsignedVarInt actual = UnsignedVarInt.fromBytes(bytes);
        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @ValueSource(longs = {0, 0x7FL, 0x80L, 0xFFL, 0XFFFFL, 0xFFFFFFL, 0xFFFFFFFFL, UnsignedVarInt.MAX_VALUE})
    void testTFromByteBufferAndBack(long value) {
        UnsignedVarInt expected = uvarint(value);
        ByteBuffer buffer = ByteBuffer.wrap(expected.toBytes());

        UnsignedVarInt actual = UnsignedVarInt.fromByteBuffer(buffer);
        assertThat(actual).isEqualTo(expected);
    }
}