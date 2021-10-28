/*
 * Copyright (c) 2017 Atlanmod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package org.atlanmod.commons.concurrent;

import org.atlanmod.commons.Throwables;
import org.atlanmod.commons.annotation.Static;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import static java.util.Objects.nonNull;
import static org.atlanmod.commons.Guards.checkNotNull;

/**
 * Static utility methods related to {@link Thread} instances.
 */
@Static
@ParametersAreNonnullByDefault
public final class MoreThreads {

    /**
     * The default thread factory, used to create {@link Thread} instances that are not attached to an {@link
     * ExecutorService}.
     *
     * @see #executeAtExit(Runnable)
     */
    @Nonnull
    private static final ThreadFactory DEFAULT_THREAD_FACTORY = newThreadFactory("shutdown-hook-manager");

    private MoreThreads() {
        throw Throwables.notInstantiableClass(getClass());
    }

    /**
     * Creates a new {@link ThreadFactory} that creates daemon threads.
     *
     * @return a new thread factory
     *
     * @see #newThreadFactory(String)
     */
    @Nonnull
    public static ThreadFactory newThreadFactory() {
        return newThreadFactory(null);
    }

    /**
     * Creates a new {@link ThreadFactory} that creates daemon threads with a defined prefixed name.
     *
     * @param prefix the prefix of the name of created threads; if {@code null} the default name will be used
     *
     * @return a new thread factory
     *
     * @see Executors#defaultThreadFactory()
     */
    @Nonnull
    public static ThreadFactory newThreadFactory(@Nullable String prefix) {
        final AtomicInteger threadCount = new AtomicInteger(1);

        return task -> {
            Thread thread = Executors.defaultThreadFactory().newThread(task);
            thread.setDaemon(true);

            if (nonNull(prefix)) {
                thread.setName(prefix + "-" + threadCount.getAndIncrement());
            }

            return thread;
        };
    }

    /**
     * Adds a shutdown hook that will execute the {@code task} when the application will exit.
     *
     * @param task the task to execute
     *
     * @throws NullPointerException if the {@code task} is {@code null}
     */
    public static void executeAtExit(Runnable task) {
        checkNotNull(task, "task");

        Runtime.getRuntime().addShutdownHook(DEFAULT_THREAD_FACTORY.newThread(task));
    }
}
