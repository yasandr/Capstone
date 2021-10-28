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

import javax.annotation.ParametersAreNonnullByDefault;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * A test-case that checks the behavior of {@link Floats}.
 */
@ParametersAreNonnullByDefault
class FloatsTest extends AbstractTest {

    @Test
    void testToBytes() {
        final Float float0 = 139895433915.09579569E18f;
        byte[] actual0 = Floats.toBytes(float0);
        byte[] expected0 = ByteBuffer.allocate(Float.BYTES).putFloat(float0).array();
        assertThat(actual0).containsExactly(expected0);
    }
}