/*
 * Copyright (c) 2017 Atlanmod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package org.atlanmod.commons.service;

import java.util.function.Supplier;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Represents a service definition.
 */
// TODO Can be replace by `ServiceLoader.Provider` with Java 9
@ParametersAreNonnullByDefault
public interface ServiceDefinition<T> extends Supplier<T> {

    /**
     * Returns the service type.
     *
     * @return the service type
     */
    @Nonnull
    Class<? extends T> type();

    /**
     * Returns an instance of the service.
     *
     * @return an instance of the service
     */
    @Nonnull
    @Override
    T get();
}
