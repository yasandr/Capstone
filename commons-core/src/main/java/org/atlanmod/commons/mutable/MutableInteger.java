/*
 * Copyright (c) 2021 Atlanmod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package org.atlanmod.commons.mutable;

import javax.annotation.Nonnull;

/**
 * A mutable int wrapper.
 */
public class MutableInteger extends Number implements Comparable<MutableInteger>, Mutable<Integer> {

    private int value;

    /**
     * Constructs a new MutableInteger
     */
    public MutableInteger() {
        super();
    }

    /**
     * Constructs a new MutableInteger and stores the specified value.
     *
     * @param newValue the initial value
     */
    public MutableInteger(int newValue) {
        this();
        this.value = newValue;
    }

    /**
     * Sets the value of the stored int
     *
     * @param newValue the new value to store
     */
    public void set(int newValue) {
        this.value = newValue;
    }

    /**
     * Gets the value of the stored int
     *
     * @return the stored int
     */
    public int get() {
        return this.value;
    }

    /**
     * Increments by 1 the stored value
     */
    public void increment() {
        this.value++;
    }

    /**
     * Increments by 1 the stored value and gets the new value
     *
     * @return the stored value, after it is incremented
     */
    public int incrementAndGet() {
       this.value++;
       return this.value;
    }

    /**
     * Increments by 1 the stored value and gets the previous stored value
     *
     * @return the store valued prior to the increment
     */
    public int getAndIncrement() {
        int oldValue = this.value;
        this.value++;
        return oldValue;
    }

    /**
     * Decrements by 1 the stored value
     */
    public void decrement() {
        this.value--;
    }

    /**
     * Decrements by 1 the stored value and gets the new value
     *
     * @return the stored value, after it is decremented
     */
    public int decrementAndGet() {
        this.value--;
        return this.value;
    }

    /**
     * Decrements by 1 the stored value and gets the previous stored value
     *
     * @return the store valued prior to the increment
     */
    public int getAndDecrement() {
        int oldValue = this.value;
        this.value--;
        return oldValue;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof MutableInteger)) return false;

        MutableInteger that = (MutableInteger) other;

        return value == that.value;
    }

    @Override
    public int hashCode() {
        return value;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }

    @Override
    public void setValue(Integer newValue) {
        this.value = newValue;
    }

    @Override
    public int compareTo(@Nonnull MutableInteger other) {
        return Integer.compare(value, other.value);
    }

    @Override
    public int intValue() {
        return value;
    }

    @Override
    public long longValue() {
        return value;
    }

    @Override
    public float floatValue() {
        return value;
    }

    @Override
    public double doubleValue() {
        return value;
    }

}


