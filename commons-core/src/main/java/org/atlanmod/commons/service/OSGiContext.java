/*
 * Copyright (c) 2017 Atlanmod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package org.atlanmod.commons.service;

import org.atlanmod.commons.Lazy;
import org.atlanmod.commons.annotation.VisibleForReflection;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

import java.util.stream.Stream;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

/**
 * A {@link ServiceContext} able to retrieve OSGi Declarative Services from a {@link BundleContext}.
 *
 * <b>NOTE:</b> This context is automatically loaded and configured when this bundle is starting under an OSGi
 * environment.
 */
@Component(immediate = true)
@VisibleForReflection
@ParametersAreNonnullByDefault
public class OSGiContext implements ServiceContext {

    /**
     * The current bundle context.
     */
    private BundleContext context;

    /**
     * Activates this context with the specified OSGi {@code context}.
     *
     * @param context the current bundle context
     */
    @Activate
    final void onInit(BundleContext context) {
        this.context = context;
        ServiceProvider.getInstance().setContext(this);
    }

    /**
     * Deactivates this context.
     */
    @Deactivate
    final void onDestroy() {
        this.context = null;
        ServiceProvider.getInstance().unloadContext();
    }

    @Nonnull
    @Override
    public <S> Stream<ServiceDefinition<S>> getServices(Class<S> type) {
        return getServices(type, null);
    }

    /**
     * Retrieves all registered services of the specified {@code type}.
     *
     * @param type   the type of services to look for
     * @param filter the filter to determine if a service should be returned
     *
     * @return a parallel stream of all registered services of the specified {@code type}
     */
    @Nonnull
    public <T> Stream<ServiceDefinition<T>> getServices(Class<T> type, @Nullable String filter) {
        try {
            return context.getServiceReferences(type, filter).parallelStream().map(OSGiServiceDefinition::new);
        }
        catch (InvalidSyntaxException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * @param <S>
     */
    @ParametersAreNonnullByDefault
    private class OSGiServiceDefinition<S> implements ServiceDefinition<S> {

        /**
         * The service reference.
         */
        @Nonnull
        private final ServiceReference<S> reference;

        /**
         * The on-demand service.
         */
        @Nonnull
        private final Lazy<S> service;

        /**
         * Constructs a new {@code OSGiServiceDefinition}.
         *
         * @param reference the service reference
         */
        public OSGiServiceDefinition(ServiceReference<S> reference) {
            this.reference = reference;
            this.service = Lazy.with(() -> context.getService(this.reference));
        }

        @Nonnull
        @Override
        @SuppressWarnings("unchecked")
        public Class<? extends S> type() {
            return (Class<? extends S>) get().getClass();
        }

        @Nonnull
        @Override
        public S get() {
            return service.get();
        }
    }
}
