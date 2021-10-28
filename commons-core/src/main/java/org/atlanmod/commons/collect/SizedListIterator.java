/*
 * Copyright (c) 2017 Atlanmod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package org.atlanmod.commons.collect;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.function.IntFunction;

import javax.annotation.Nonnegative;
import javax.annotation.ParametersAreNonnullByDefault;

import static org.atlanmod.commons.Guards.checkNotNull;
import static org.atlanmod.commons.Guards.checkPositionIndex;

/**
 * A size-based {@link Iterator} that loads each value with a {@link IntFunction}.
 *
 * @param <E> the type of elements returned by this iterator
 */
@ParametersAreNonnullByDefault
public class SizedListIterator<E> implements ListIterator<E> {

    /**
     * The function used to retrieve the value at a specified index.
     */
    @Nonnegative
    private final IntFunction<E> mappingFunction;

    /**
     * The size of the iterator.
     */
    @Nonnegative
    private final int size;

    /**
     * The current position of this iterator.
     */
    @Nonnegative
    private int index;

    /**
     * Constructs a new {@code SizedIterator}.
     *
     * @param size            the size of the iterator
     * @param mappingFunction the function used to retrieve the value at a specified index
     */
    public SizedListIterator(@Nonnegative int size, IntFunction<E> mappingFunction) {
        this(size, mappingFunction, 0);
    }

    /**
     * Constructs a new {@code SizedIterator}.
     *
     * @param size            the size of the iterator
     * @param mappingFunction the function used to retrieve the value at a specified index
     * @param index           the starting index
     */
    public SizedListIterator(@Nonnegative int size, IntFunction<E> mappingFunction, int index) {
        checkNotNull(mappingFunction, "mappingFunction");
        checkPositionIndex(index, size);

        this.size = size;
        this.index = index;
        this.mappingFunction = mappingFunction;
    }

    @Override
    public boolean hasNext() {
        return index < size;
    }

    @Override
    public E next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }

        return mappingFunction.apply(index++);
    }

    @Override
    public boolean hasPrevious() {
        return index > 0;
    }

    @Override
    public E previous() {
        if (!hasPrevious()) {
            throw new NoSuchElementException();
        }

        return mappingFunction.apply(--index);
    }

    @Override
    public int nextIndex() {
        return index;
    }

    @Override
    public int previousIndex() {
        return index - 1;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("remove");
    }

    @Override
    public void set(E e) {
        throw new UnsupportedOperationException("set");
    }

    @Override
    public void add(E e) {
        throw new UnsupportedOperationException("add");
    }
}
