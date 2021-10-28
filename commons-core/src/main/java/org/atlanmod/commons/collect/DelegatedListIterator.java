/*
 * Copyright (c) 2017 Atlanmod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package org.atlanmod.commons.collect;

import org.atlanmod.commons.function.Converter;

import java.util.ListIterator;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import static org.atlanmod.commons.Guards.checkNotNull;

/**
 * A {@link ListIterator} that iterates over another.
 *
 * @param <T> the type of elements returned by the delegated iterator
 * @param <R> the type of elements returned by this iterator
 */
@ParametersAreNonnullByDefault
public class DelegatedListIterator<T, R> implements ListIterator<R> {

    /**
     * The delegated iterator.
     */
    @Nonnull
    private final ListIterator<T> delegate;

    /**
     * The function used to map elements from the delegated iterator.
     */
    @Nonnull
    private final Converter<T, R> converter;

    /**
     * Constructs a new {@code DelegatedIterator}.
     *
     * @param delegate  the delegated list iterator
     * @param converter the function used to map elements from the {@code delegate}
     */
    public DelegatedListIterator(ListIterator<T> delegate, Converter<T, R> converter) {
        checkNotNull(delegate, "delegate");
        checkNotNull(converter, "converter");

        this.delegate = delegate;
        this.converter = converter;
    }

    @Override
    public boolean hasNext() {
        return delegate.hasNext();
    }

    @Override
    public R next() {
        return converter.convert(delegate.next());
    }

    @Override
    public boolean hasPrevious() {
        return delegate.hasPrevious();
    }

    @Override
    public R previous() {
        return converter.convert(delegate.previous());
    }

    @Override
    public int nextIndex() {
        return delegate.nextIndex();
    }

    @Override
    public int previousIndex() {
        return delegate.previousIndex();
    }

    @Override
    public void remove() {
        delegate.remove();
    }

    @Override
    public void set(R r) {
        delegate.set(converter.revert(r));
    }

    @Override
    public void add(R r) {
        delegate.add(converter.revert(r));
    }
}
