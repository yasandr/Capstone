/*
 * Copyright (c) 2017 Atlanmod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package org.atlanmod.commons.service;

import org.atlanmod.commons.annotation.VisibleForReflection;
import org.atlanmod.commons.collect.MoreIterables;

import java.util.ServiceLoader;
import java.util.stream.Stream;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

/**
 * A {@link ServiceContext} able to retrieve registered services from a {@link ServiceLoader}.
 */
@VisibleForReflection
@ParametersAreNonnullByDefault
public class ServiceLoaderContext implements ServiceContext {

    @Nonnull
    @Override
    public <S> Stream<ServiceDefinition<S>> getServices(Class<S> type) {
        final ClassLoader classLoader = type.getClassLoader();
        final ServiceLoader<S> serviceLoader = ServiceLoader.load(type, classLoader);

        return MoreIterables.parallelStream(serviceLoader).map(DirectServiceDefinition::new);
    }

    /**
     * @param <S>
     */
    @ParametersAreNonnullByDefault
    private static class DirectServiceDefinition<S> implements ServiceDefinition<S> {

        /**
         * The service.
         */
        @Nonnull
        private final S service;

        /**
         * Constructs a new {@code DirectServiceDefinition}.
         *
         * @param service the service
         */
        public DirectServiceDefinition(S service) {
            this.service = service;
        }

        @Nonnull
        @Override
        @SuppressWarnings("unchecked")
        public Class<? extends S> type() {
            return (Class<? extends S>) service.getClass();
        }

        @Nonnull
        @Override
        public S get() {
            return service;
        }
    }
}
