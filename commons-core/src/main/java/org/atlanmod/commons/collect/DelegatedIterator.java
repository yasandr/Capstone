/*
 * Copyright (c) 2017 Atlanmod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package org.atlanmod.commons.collect;

import java.util.Iterator;
import java.util.function.Function;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import static org.atlanmod.commons.Guards.checkNotNull;

/**
 * An {@link Iterator} that iterates over another.
 *
 * @param <T> the type of elements returned by the delegated iterator
 * @param <R> the type of elements returned by this iterator
 */
@ParametersAreNonnullByDefault
public class DelegatedIterator<T, R> implements Iterator<R> {

    /**
     * The delegated iterator.
     */
    @Nonnull
    private final Iterator<T> delegate;

    /**
     * The function used to map elements from the delegated iterator.
     */
    @Nonnull
    private final Function<T, R> mappingFunction;

    /**
     * Constructs a new {@code DelegatedIterator}.
     *
     * @param delegate        the delegated iterator
     * @param mappingFunction the function used to map elements from the {@code delegate}
     */
    public DelegatedIterator(Iterator<T> delegate, Function<T, R> mappingFunction) {
        checkNotNull(delegate, "delegate");
        checkNotNull(mappingFunction, "mappingFunction");

        this.delegate = delegate;
        this.mappingFunction = mappingFunction;
    }

    @Override
    public boolean hasNext() {
        return delegate.hasNext();
    }

    @Override
    public R next() {
        return mappingFunction.apply(delegate.next());
    }

    @Override
    public void remove() {
        delegate.remove();
    }
}
