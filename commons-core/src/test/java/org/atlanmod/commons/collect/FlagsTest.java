package org.atlanmod.commons.collect;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class FlagsTest {
    @ParameterizedTest
    @ValueSource(ints = {8, 16, 56, 128, 256, 13})
    void testGetSet(int size) {
        Flags bitSet = new Flags(size);
        for (int i = 0; i < size; i++) {
            assertThat(bitSet.get(i)).isFalse();
        }

        for (int i = 0; i < size; i++) {
            bitSet.set(i);
        }

        for (int i = 0; i < size; i++) {
            assertThat(bitSet.get(i)).isTrue();
        }
    }

    @Test
    void testToAndFromArray() {
        Flags expected = new Flags(15);
        expected.set(10);
        expected.set(2);
        byte[] bytes = expected.toBytes();
        Flags actual = Flags.fromBytes(bytes);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void testToAndFromArrayOneValue() {
        Flags expected = new Flags(8);
        expected.set(4);
        byte[] bytes = expected.toBytes();
        Flags actual = Flags.fromBytes(bytes);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void testBitsetLength() {
        Flags expected = new Flags(8);
        expected.set(4);
        byte[] bytes = expected.toBytes();

        assertThat(bytes.length).isEqualTo(1);
    }

    @Test
    void testGetWithEmptyFlags() {
        Flags flags = new Flags(8);
        for (int i = 0; i < 8; i++) {
            assertThat(flags.get(i)).isFalse();
        }
    }
}