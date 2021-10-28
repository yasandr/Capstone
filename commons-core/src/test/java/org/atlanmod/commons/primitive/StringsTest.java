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

import javax.annotation.ParametersAreNonnullByDefault;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * A test-case that checks the behavior of {@link Strings}.
 */
@ParametersAreNonnullByDefault
class StringsTest extends AbstractTest {

    @Test
    void testIsNullOrEmpty() {
        String string0 = "";
        String string1 = null;
        String string2 = "MyString";

        assertThat(Strings.isNullOrEmpty(string0)).isTrue();
        assertThat(Strings.isNullOrEmpty(string1)).isTrue();
        assertThat(Strings.isNullOrEmpty(string2)).isFalse();
    }

    @Test
    void testNullToEmpty() {
        String string0 = "";
        String string1 = null;
        String string2 = "MyString";

        assertThat(Strings.nullToEmpty(string0)).isNotNull().isEmpty();
        assertThat(Strings.nullToEmpty(string1)).isNotNull().isEmpty();
        assertThat(Strings.nullToEmpty(string2)).isEqualTo(string2);
    }

    @Test
    void testEmptyToNull() {
        String string0 = "";
        String string1 = null;
        String string2 = "MyString";

        assertThat(Strings.emptyToNull(string0)).isNull();
        assertThat(Strings.emptyToNull(string1)).isNull();
        assertThat(Strings.emptyToNull(string2)).isEqualTo(string2);
    }

    @Test
    void testIsBinary() {
        String string0 = "";
        String string1 = null;
        String string2 = "MyString";
        String string3 = "0123456789ABCDEF";
        String string4 = "0123456789abcdef";
        String string5 = "0123456789aBcdEf";
        String string6 = "G";

        assertThat(Strings.isBinary(string0)).isFalse();
        //noinspection ConstantConditions
        assertThat(Strings.isBinary(string1)).isFalse();
        assertThat(Strings.isBinary(string2)).isFalse();
        assertThat(Strings.isBinary(string3)).isTrue();
        assertThat(Strings.isBinary(string4)).isTrue();
        assertThat(Strings.isBinary(string5)).isTrue();
        assertThat(Strings.isBinary(string6)).isFalse();
    }

    @Test
    void testToBytes() {
        final String string0 = "AtlanmodIsAwesome!";
        byte[] actual0 = Strings.toBytes(string0);
        byte[] expected0 = string0.getBytes();
        assertThat(actual0).containsExactly(expected0);
    }
}