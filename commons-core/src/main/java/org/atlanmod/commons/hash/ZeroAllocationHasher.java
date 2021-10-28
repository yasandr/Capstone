/*
 * Copyright (c) 2017 Atlanmod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package org.atlanmod.commons.hash;

import net.openhft.hashing.LongHashFunction;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

/**
 * A {@link Hasher} that delegate its calls to a {@link LongHashFunction}.
 *
 * @see <a href="https://github.com/OpenHFT/Zero-Allocation-Hashing">OpenHFT/Zero-Allocation-Hashing</a>.
 */
@ParametersAreNonnullByDefault
final class ZeroAllocationHasher implements Hasher {

    /**
     * The delegated hash function.
     */
    @Nonnull
    private final LongHashFunction delegate;

    /**
     * Constructs a new {@code ZeroAllocationHasher}.
     *
     * @param delegate he delegated hash function
     */
    public ZeroAllocationHasher(LongHashFunction delegate) {
        this.delegate = delegate;
    }

    @Nonnull
    @Override
    public HashCode hash(byte data) {
        return createHashCode(delegate.hashByte(data));
    }

    @Nonnull
    @Override
    public HashCode hash(boolean data) {
        return createHashCode(delegate.hashBoolean(data));
    }

    @Nonnull
    @Override
    public HashCode hash(char data) {
        return createHashCode(delegate.hashChar(data));
    }

    @Nonnull
    @Override
    public HashCode hash(short data) {
        return createHashCode(delegate.hashShort(data));
    }

    @Nonnull
    @Override
    public HashCode hash(int data) {
        return createHashCode(delegate.hashInt(data));
    }

    @Nonnull
    @Override
    public HashCode hash(long data) {
        return createHashCode(delegate.hashLong(data));
    }

    @Nonnull
    @Override
    public HashCode hash(float data) {
        return createHashCode(delegate.hashInt(Float.floatToRawIntBits(data)));
    }

    @Nonnull
    @Override
    public HashCode hash(double data) {
        return createHashCode(delegate.hashLong(Double.doubleToRawLongBits(data)));
    }

    @Nonnull
    @Override
    public HashCode hash(String data) {
        return createHashCode(delegate.hashChars(data));
    }

    @Nonnull
    @Override
    public HashCode hash(byte[] data) {
        return createHashCode(delegate.hashBytes(data));
    }

    /**
     * Creates a new {@link HashCode} from a long {@code hashCode}.
     *
     * @param hashCode the result of the hash function
     *
     * @return a new {@link HashCode} instance
     */
    @Nonnull
    private HashCode createHashCode(long hashCode) {
        return new LongHashCode(hashCode);
    }
}
