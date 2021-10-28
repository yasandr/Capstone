/*
 * Copyright (c) 2017 Atlanmod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package org.atlanmod.commons;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * An object that can be copied to another.
 *
 * @param <T> the type of the copied object
 */
@ParametersAreNonnullByDefault
@FunctionalInterface
public interface Copiable<T> {

    /**
     * Copies all the contents from this object to the {@code target}.
     *
     * @param target the object where to store the copied content
     */
    void copyTo(T target);
}
