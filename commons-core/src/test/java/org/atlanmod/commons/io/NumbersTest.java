package org.atlanmod.commons.io;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

class NumbersTest {

    @ParameterizedTest
    @DisplayName(value = "When a number is smaller or equal to UnsignedByte.MAX_VALUE, fromLong() must return an instance of UnsignedByte")
    @ValueSource(longs = {0, 125, UnsignedByte.MAX_VALUE})
    void createUnsignedByteFromLong(long value) {
        UnsignedNumber number = Numbers.fromLong(value);

        assertThat(number).isInstanceOf(UnsignedByte.class);
    }

    @ParameterizedTest
    @DisplayName(value = "When a number is between UnsignedByte.MAX_VALUE and UnsignedShort.MAX_VALUE fromLong() must return an instance of UnsignedShort")
    @ValueSource(longs = {UnsignedByte.MAX_VALUE + 1, UnsignedByte.MAX_VALUE << 2, UnsignedShort.MAX_VALUE})
    void createUnsignedShortFromLong(long value) {
        UnsignedNumber number = Numbers.fromLong(value);

        assertThat(number).isInstanceOf(UnsignedShort.class);
    }

    @ParameterizedTest
    @DisplayName(value = "When a number is between UnsignedShort.MAX_VALUE and UnsignedInt.MAX_VALUE fromLong() must return an instance of UnsignedInt")
    @ValueSource(longs = {UnsignedShort.MAX_VALUE + 1, UnsignedShort.MAX_VALUE << 2, UnsignedInt.MAX_VALUE})
    void createUnsignedIntFromLong(long value) {
        UnsignedNumber number = Numbers.fromLong(value);

        assertThat(number).isInstanceOf(UnsignedInt.class);
    }
}