/*
 * Copyright (c) 2017 Atlanmod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package org.atlanmod.commons.service;

import java.util.stream.Stream;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

/**
 * A context able to retrieve registered services.
 */
@ParametersAreNonnullByDefault
public interface ServiceContext {

    /**
     * Retrieves all registered services of the specified {@code type}.
     *
     * @param type the type of services to look for
     *
     * @return a parallel stream of all registered services of the specified {@code type}
     */
    @Nonnull
    <T> Stream<ServiceDefinition<T>> getServices(Class<T> type);
}
