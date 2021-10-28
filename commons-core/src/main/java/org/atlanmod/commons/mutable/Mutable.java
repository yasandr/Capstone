/*
 * Copyright (c) 2021 Atlanmod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package org.atlanmod.commons.mutable;

/**
 * Provides mutable access to a store value.
 *
 * @param <T>
 */
public interface Mutable<T> {

    /**
     * Gets the value of this mutable.
     *
     * @return the store value
     */
    T getValue();

    /**
     * Sets the value of this mutable
     *
     * @param newValue the new value to store
     */
    void setValue(T newValue);
}
