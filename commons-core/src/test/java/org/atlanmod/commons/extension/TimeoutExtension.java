/*
 * Copyright (c) 2017 Atlanmod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package org.atlanmod.commons.extension;

import org.atlanmod.commons.Timeout;
import org.atlanmod.commons.log.Log;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.OptionalLong;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import static org.atlanmod.commons.Guards.checkGreaterThanOrEqualTo;
import static java.util.Objects.nonNull;

/**
 * A JUnit {@link org.junit.jupiter.api.extension.Extension} that stops a test-case when its execution time exceeds a
 * defined timeout.
 *
 * @see Timeout
 */
@ParametersAreNonnullByDefault
public final class TimeoutExtension implements BeforeEachCallback, AfterEachCallback {

    /**
     * The current timer.
     */
    @Nullable
    private Timer timer;

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        getTimeout(context).ifPresent(timeout -> {
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    String msg = String.format("timeout exceeded: %d ms", timeout);

                    // TODO Replace logging: JUnit5 does not support yet the call interception
                    Log.error(msg);
                }
            }, timeout);
        });
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        if (nonNull(timer)) {
            timer.cancel();
            timer = null;
        }
    }

    /**
     * Retrieves the defined timeout from the given {@code context}.
     *
     * @param context the execution context
     *
     * @return the timeout in ms, wrapped in an {@link OptionalLong}
     */
    @Nonnull
    @Nonnegative
    private OptionalLong getTimeout(ExtensionContext context) {
        Timeout annotation = null;

        if (context.getRequiredTestMethod().isAnnotationPresent(Timeout.class)) {
            annotation = context.getRequiredTestMethod().getAnnotation(Timeout.class);
        }
        else if (context.getRequiredTestClass().isAnnotationPresent(Timeout.class)) {
            annotation = context.getRequiredTestClass().getAnnotation(Timeout.class);
        }

        if (nonNull(annotation)) {
            long timeout = annotation.timeout();
            TimeUnit unit = annotation.unit();
            checkGreaterThanOrEqualTo(timeout, 0L, "timeout (%d) must not be negative", timeout);
            return OptionalLong.of(unit.toMillis(timeout));
        }

        return OptionalLong.empty();
    }
}
