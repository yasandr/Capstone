/*
 * Copyright (c) 2017 Atlanmod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package org.atlanmod.commons.log;

import org.atlanmod.commons.function.TriConsumer;
import org.slf4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Predicate;

/**
 * An object used for identifying the severity of an event.
 * <p>
 * Levels are organized from most specific to least: <ul> <li>{@link #ERROR} (most specific, little data)</li>
 * <li>{@link #WARN}</li> <li>{@link #INFO}</li> <li>{@link #DEBUG}</li> <li>{@link #TRACE} (least specific, a lot of
 * data)</li> </ul>
 * <p>
 * Typically, configuring a level in a filter or on a {@link Logger} will cause logging events of that level and those
 * that are more specific to pass through the filter.
 */
@ParametersAreNonnullByDefault
public enum Level {

    /**
     * A fine-grained debug message, typically capturing the flow through the application.
     */
    TRACE(Logger::trace, Logger::isTraceEnabled),

    /**
     * A general debugging event.
     */
    DEBUG(org.slf4j.Logger::debug, org.slf4j.Logger::isDebugEnabled),

    /**
     * An event for informational purposes.
     */
    INFO(org.slf4j.Logger::info, org.slf4j.Logger::isInfoEnabled),

    /**
     * An event that might possible lead to an error.
     */
    WARN(org.slf4j.Logger::warn, org.slf4j.Logger::isWarnEnabled),

    /**
     * An error in the application, possibly recoverable.
     */
    ERROR(Logger::error, Logger::isErrorEnabled);

    /**
     * The logging function.
     */
    @Nonnull
    private final TriConsumer<Logger, String, Throwable> loggingFunction;

    /**
     * The predicate used to determine if this level is enabled for a {@link org.slf4j.Logger}.
     */
    @Nonnull
    private final Predicate<Logger> isEnabledPredicate;

    /**
     * Constructs a new {@code Level}.
     */
    Level(TriConsumer<Logger, String, Throwable> loggingFunction, Predicate<Logger> isEnabledPredicate) {
        this.loggingFunction = loggingFunction;
        this.isEnabledPredicate = isEnabledPredicate;
    }

    /**
     * Logs a {@code message} at this level using the {@code logger}, including the stack trace of the given {@link
     * Throwable} if present.
     *
     * @param logger  the logger where to send message
     * @param message the message to log
     * @param e       the exception to log, including its stack trace
     */
    void logWith(Logger logger, @Nullable String message, @Nullable Throwable e) {
        loggingFunction.accept(logger, message, e);
    }

    /**
     * Returns {@code true} if this level is enabled for the {@code logger}.
     *
     * @return {@code true} if this level is enabled for the {@code logger}
     */
    boolean isEnabledFor(Logger logger) {
        return isEnabledPredicate.test(logger);
    }
}
