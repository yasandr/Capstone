/*
 * Copyright (c) 2017 Atlanmod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package org.atlanmod.commons.hash;

import org.atlanmod.commons.AbstractTest;
import org.atlanmod.commons.primitive.Longs;

import org.junit.jupiter.api.Test;

import java.util.Objects;

import javax.annotation.ParametersAreNonnullByDefault;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * A test-case that checks the behavior of {@link HashCode}.
 */
@ParametersAreNonnullByDefault
class LongHashCodeTest extends AbstractTest {

    private final static HashCode HASH = new LongHashCode(123456789);

    @Test
    void testBits() {
        assertThat(HASH.bits()).isEqualTo(64);
    }

    @Test
    void testToBytes() {
        assertThat(HASH.toBytes()).isEqualTo(Longs.toBytes(123456789));
    }

    @Test
    void testToHexString() {
        assertThat(HASH.toHexString()).isEqualToIgnoringCase("75bcd15");
    }

    @Test
    void testHashCode() {
        assertThat(HASH.hashCode()).isEqualTo(Objects.hash(123456789));

        HashCode littleHash = new LongHashCode(25);
        assertThat(littleHash.bits()).isEqualTo(64);
        assertThat(littleHash.hashCode()).isEqualTo(56);
    }

    @Test
    void testEquals() {
        //noinspection EqualsWithItself
        assertThat(HASH.equals(HASH)).isTrue();

        //noinspection ObjectEqualsCanBeEquality,ConstantConditions
        assertThat(HASH.equals(null)).isFalse();

        assertThat(HASH.equals(new LongHashCode(123456789))).isTrue();

        assertThat(HASH.equals(new LongHashCode(25))).isFalse();
    }
}