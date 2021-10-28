/*
 * Copyright (c) 2017 Atlanmod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package org.atlanmod.commons.hash;

import java.io.Serializable;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.Immutable;

/**
 * An immutable hash code of arbitrary bit length.
 */
@Immutable
@ParametersAreNonnullByDefault
public interface HashCode extends Serializable {

    /**
     * Returns the number of bits in this hash code; a positive multiple of 8.
     *
     * @return the number of bits
     */
    @Nonnegative
    int bits();

    /**
     * Returns the value of this hash code as a byte array.
     *
     * @return a byte array
     */
    @Nonnull
    byte[] toBytes();

    /**
     * Returns the long representation of this hash code.
     *
     * @return a long
     */
    long toLong();

    /**
     * Returns the literal representation of this hash code.
     *
     * @return a string
     */
    @Nonnull
    String toHexString();
}
