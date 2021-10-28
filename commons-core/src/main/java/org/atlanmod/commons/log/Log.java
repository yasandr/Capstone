/*
 * Copyright (c) 2017 Atlanmod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package org.atlanmod.commons.log;

import org.atlanmod.commons.Lazy;
import org.atlanmod.commons.Throwables;
import org.atlanmod.commons.annotation.Static;
import org.atlanmod.commons.cache.Cache;
import org.atlanmod.commons.cache.CacheBuilder;
import org.atlanmod.commons.primitive.Strings;

import java.text.MessageFormat;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNullableByDefault;
import javax.annotation.concurrent.ThreadSafe;

import static org.atlanmod.commons.Guards.checkNotNull;

/**
 * The factory that creates {@link Logger} instances.
 * <p>
 * It also provides static methods for logging without declaring a specific instance. In this case, the {@link #root()
 * root logger} is used by default.
 */
@Static
@ThreadSafe
@ParametersAreNullableByDefault
public final class Log {

    /**
     * In-memory cache that holds loaded {@link Logger}s, identified by their name.
     */
    @Nonnull
    private static final Cache<String, Logger> LOGGERS = CacheBuilder.builder()
            .softValues()
            .build(AsyncLogger::new);

    /**
     * The cached root {@link Logger}.
     */
    @Nonnull
    private static final Lazy<Logger> ROOT = Lazy.with(() -> forName(Strings.EMPTY));

    private Log() {
        throw Throwables.notInstantiableClass(getClass());
    }

    /**
     * Returns the root {@link Logger}.
     *
     * @return the root {@link Logger}
     *
     * @see #forName(String)
     */
    @Nonnull
    public static Logger root() {
        return ROOT.get();
    }

    /**
     * Returns a {@link Logger} with the specified name.
     *
     * @param name the logger name
     *
     * @return the {@link Logger}
     */
    @Nonnull
    public static Logger forName(@Nonnull String name) {
        return LOGGERS.get(checkNotNull(name, "name"));
    }

    /**
     * Logs an object at the {@link Level#TRACE TRACE} level, using the root logger.
     *
     * @param obj the object to log
     *
     * @see #root()
     * @see Logger#trace(Object)
     */
    public static void trace(Object obj) {
        root().trace(obj);
    }

    /**
     * Logs a message at the {@link Level#TRACE TRACE} level, using the root logger.
     *
     * @param message the message to log
     *
     * @see #root()
     * @see Logger#trace(CharSequence)
     */
    public static void trace(CharSequence message) {
        root().trace(message);
    }

    /**
     * Logs a message with parameters at the {@link Level#TRACE TRACE} level, using the root logger.
     *
     * @param message the message to log; the format depends on the {@link MessageFormat}
     * @param params  parameters to the message
     *
     * @see #root()
     * @see Logger#trace(CharSequence, Object...)
     */
    public static void trace(CharSequence message, Object... params) {
        root().trace(message, params);
    }

    /**
     * Logs the stack trace of the given {@link Throwable} at the {@link Level#TRACE TRACE} level, using the root
     * logger.
     *
     * @param e the exception to log, including its stack trace
     *
     * @see #root()
     * @see Logger#trace(Throwable)
     */
    public static void trace(Throwable e) {
        root().trace(e);
    }

    /**
     * Logs a message at the {@link Level#TRACE TRACE} level including the stack trace of the given {@link Throwable},
     * using the root logger.
     *
     * @param e       the exception to log, including its stack trace
     * @param message the message to log
     *
     * @see #root()
     * @see Logger#trace(Throwable, CharSequence)
     */
    public static void trace(Throwable e, CharSequence message) {
        root().trace(e, message);
    }

    /**
     * Logs a message with parameters at the {@link Level#TRACE TRACE} level including the stack trace of the given
     * {@link Throwable}, using the root logger.
     *
     * @param e       the exception to log, including its stack trace
     * @param message the message to log; the format depends on the {@link MessageFormat}
     * @param params  parameters to the message
     *
     * @see #root()
     * @see Logger#trace(Throwable, CharSequence, Object...)
     */
    public static void trace(Throwable e, CharSequence message, Object... params) {
        root().trace(e, message, params);
    }

    /**
     * Logs an object at the {@link Level#DEBUG DEBUG} level, using the root logger.
     *
     * @param obj the object to log
     *
     * @see #root()
     * @see Logger#debug(Object)
     */
    public static void debug(Object obj) {
        root().debug(obj);
    }

    /**
     * Logs a message at the {@link Level#DEBUG DEBUG} level, using the root logger.
     *
     * @param message the message to log
     *
     * @see #root()
     * @see Logger#debug(CharSequence)
     */
    public static void debug(CharSequence message) {
        root().debug(message);
    }

    /**
     * Logs a message with parameters at the {@link Level#DEBUG DEBUG} level, using the root logger.
     *
     * @param message the message to log; the format depends on the {@link MessageFormat}
     * @param params  parameters to the message
     *
     * @see #root()
     * @see Logger#debug(CharSequence, Object...)
     */
    public static void debug(CharSequence message, Object... params) {
        root().debug(message, params);
    }

    /**
     * Logs the stack trace of the given {@link Throwable} at the {@link Level#DEBUG DEBUG} level, using the root
     * logger.
     *
     * @param e the exception to log, including its stack trace
     *
     * @see #root()
     * @see Logger#debug(Throwable)
     */
    public static void debug(Throwable e) {
        root().debug(e);
    }

    /**
     * Logs a message at the {@link Level#DEBUG DEBUG} level including the stack trace of the given {@link Throwable},
     * using the root logger.
     *
     * @param e       the exception to log, including its stack trace
     * @param message the message to log
     *
     * @see #root()
     * @see Logger#debug(Throwable, CharSequence)
     */
    public static void debug(Throwable e, CharSequence message) {
        root().debug(e, message);
    }

    /**
     * Logs a message with parameters at the {@link Level#DEBUG DEBUG} level including the stack trace of the given
     * {@link Throwable}, using the root logger.
     *
     * @param e       the exception to log, including its stack trace
     * @param message the message to log; the format depends on the {@link MessageFormat}
     * @param params  parameters to the message
     *
     * @see #root()
     * @see Logger#debug(Throwable, CharSequence, Object...)
     */
    public static void debug(Throwable e, CharSequence message, Object... params) {
        root().debug(e, message, params);
    }

    /**
     * Logs an object at the {@link Level#INFO INFO} level, using the root logger.
     *
     * @param obj the object to log
     *
     * @see #root()
     * @see Logger#info(Object)
     */
    public static void info(Object obj) {
        root().info(obj);
    }

    /**
     * Logs a message at the {@link Level#INFO INFO} level, using the root logger.
     *
     * @param message the message to log
     *
     * @see #root()
     * @see Logger#info(CharSequence)
     */
    public static void info(CharSequence message) {
        root().info(message);
    }

    /**
     * Logs a message with parameters at the {@link Level#INFO INFO} level, using the root logger.
     *
     * @param message the message to log; the format depends on the {@link MessageFormat}
     * @param params  parameters to the message
     *
     * @see #root()
     * @see Logger#info(CharSequence, Object...)
     */
    public static void info(CharSequence message, Object... params) {
        root().info(message, params);
    }

    /**
     * Logs the stack trace of the given {@link Throwable} at the {@link Level#INFO INFO} level, using the root logger.
     *
     * @param e the exception to log, including its stack trace
     *
     * @see #root()
     * @see Logger#info(Throwable)
     */
    public static void info(Throwable e) {
        root().info(e);
    }

    /**
     * Logs a message at the {@link Level#INFO INFO} level including the stack trace of the given {@link Throwable},
     * using the root logger.
     *
     * @param e       the exception to log, including its stack trace
     * @param message the message to log
     *
     * @see #root()
     * @see Logger#info(Throwable, CharSequence)
     */
    public static void info(Throwable e, CharSequence message) {
        root().info(e, message);
    }

    /**
     * Logs a message with parameters at the {@link Level#INFO INFO} level including the stack trace of the given {@link
     * Throwable}, using the root logger.
     *
     * @param e       the exception to log, including its stack trace
     * @param message the message to log; the format depends on the {@link MessageFormat}
     * @param params  parameters to the message
     *
     * @see #root()
     * @see Logger#info(Throwable, CharSequence, Object...)
     */
    public static void info(Throwable e, CharSequence message, Object... params) {
        root().info(e, message, params);
    }

    /**
     * Logs an object at the {@link Level#WARN WARN} level, using the root logger.
     *
     * @param obj the object to log
     *
     * @see #root()
     * @see Logger#warn(Object)
     */
    public static void warn(Object obj) {
        root().warn(obj);
    }

    /**
     * Logs a message at the {@link Level#WARN WARN} level, using the root logger.
     *
     * @param message the message to log
     *
     * @see #root()
     * @see Logger#warn(CharSequence)
     */
    public static void warn(CharSequence message) {
        root().warn(message);
    }

    /**
     * Logs a message with parameters at the {@link Level#WARN WARN} level, using the root logger.
     *
     * @param message the message to log; the format depends on the {@link MessageFormat}
     * @param params  parameters to the message
     *
     * @see #root()
     * @see Logger#warn(CharSequence, Object...)
     */
    public static void warn(CharSequence message, Object... params) {
        root().warn(message, params);
    }

    /**
     * Logs the stack trace of the given {@link Throwable} at the {@link Level#WARN WARN} level, using the root logger.
     *
     * @param e the exception to log, including its stack trace
     *
     * @see #root()
     * @see Logger#warn(Throwable)
     */
    public static void warn(Throwable e) {
        root().warn(e);
    }

    /**
     * Logs a message at the {@link Level#WARN WARN} level including the stack trace of the given {@link Throwable},
     * using the root logger.
     *
     * @param e       the exception to log, including its stack trace
     * @param message the message to log
     *
     * @see #root()
     * @see Logger#warn(Throwable, CharSequence)
     */
    public static void warn(Throwable e, CharSequence message) {
        root().warn(e, message);
    }

    /**
     * Logs a message with parameters at the {@link Level#WARN WARN} level including the stack trace of the given {@link
     * Throwable}, using the root logger.
     *
     * @param e       the exception to log, including its stack trace
     * @param message the message to log; the format depends on the {@link MessageFormat}
     * @param params  parameters to the message
     *
     * @see #root()
     * @see Logger#warn(Throwable, CharSequence, Object...)
     */
    public static void warn(Throwable e, CharSequence message, Object... params) {
        root().warn(e, message, params);
    }

    /**
     * Logs an object at the {@link Level#ERROR ERROR} level, using the root logger.
     *
     * @param obj the object to log
     *
     * @see #root()
     * @see Logger#error(Object)
     */
    public static void error(Object obj) {
        root().error(obj);
    }

    /**
     * Logs a message at the {@link Level#ERROR ERROR} level, using the root logger.
     *
     * @param message the message to log
     *
     * @see #root()
     * @see Logger#error(CharSequence)
     */
    public static void error(CharSequence message) {
        root().error(message);
    }

    /**
     * Logs a message with parameters at the {@link Level#ERROR ERROR} level, using the root logger.
     *
     * @param message the message to log; the format depends on the {@link MessageFormat}
     * @param params  parameters to the message
     *
     * @see #root()
     * @see Logger#error(CharSequence, Object...)
     */
    public static void error(CharSequence message, Object... params) {
        root().error(message, params);
    }

    /**
     * Logs the stack trace of the given {@link Throwable} at the {@link Level#ERROR ERROR} level, using the root
     * logger.
     *
     * @param e the exception to log, including its stack trace
     *
     * @see #root()
     * @see Logger#error(Throwable)
     */
    public static void error(Throwable e) {
        root().error(e);
    }

    /**
     * Logs a message at the {@link Level#ERROR ERROR} level including the stack trace of the given {@link Throwable},
     * using the root logger.
     *
     * @param e       the exception to log, including its stack trace
     * @param message the message to log
     *
     * @see #root()
     * @see Logger#error(Throwable, CharSequence)
     */
    public static void error(Throwable e, CharSequence message) {
        root().error(e, message);
    }

    /**
     * Logs a message with parameters at the {@link Level#ERROR ERROR} level including the stack trace of the given
     * {@link Throwable}, using the root logger.
     *
     * @param e       the exception to log, including its stack trace
     * @param message the message to log; the format depends on the {@link MessageFormat}
     * @param params  parameters to the message
     *
     * @see #root()
     * @see Logger#error(Throwable, CharSequence, Object...)
     */
    public static void error(Throwable e, CharSequence message, Object... params) {
        root().error(e, message, params);
    }

    /**
     * Logs an object at the given {@code level}, using the root logger.
     *
     * @param level the logging level
     * @param obj   the object to log
     *
     * @see #root()
     * @see Logger#log(Level, Object)
     */
    public static void log(@Nonnull Level level, Object obj) {
        root().log(level, obj);
    }

    /**
     * Logs a message at the given {@code level}, using the root logger.
     *
     * @param level   the logging level
     * @param message the message to log
     *
     * @see #root()
     * @see Logger#log(Level, CharSequence)
     */
    public static void log(@Nonnull Level level, CharSequence message) {
        root().log(level, message);
    }

    /**
     * Logs a message with parameters at the given {@code level}, using the root logger.
     *
     * @param level   the logging level
     * @param message the message to log; the format depends on the {@link MessageFormat}
     * @param params  parameters to the message
     *
     * @see #root()
     * @see Logger#log(Level, CharSequence, Object...)
     */
    public static void log(@Nonnull Level level, CharSequence message, Object... params) {
        root().log(level, message, params);
    }

    /**
     * Logs the stack trace of the given {@link Throwable} at the given {@code level}, using the root logger.
     *
     * @param level the logging level
     * @param e     the exception to log, including its stack trace
     *
     * @see #root()
     * @see Logger#log(Level, Throwable)
     */
    public static void log(@Nonnull Level level, Throwable e) {
        root().log(level, e);
    }

    /**
     * Logs a message at the given {@code level} including the stack trace of the given {@link Throwable}, using the
     * root logger.
     *
     * @param level   the logging level
     * @param e       the exception to log, including its stack trace
     * @param message the message to log
     *
     * @see #root()
     * @see Logger#log(Level, Throwable, CharSequence)
     */
    public static void log(@Nonnull Level level, Throwable e, CharSequence message) {
        root().log(level, e, message);
    }

    /**
     * Logs a message with parameters at the given {@code level} including the stack trace of the given {@link
     * Throwable}, using the root logger.
     *
     * @param level   the logging level
     * @param e       the exception to log, including its stack trace
     * @param message the message to log; the format depends on the {@link MessageFormat}
     * @param params  parameters to the message
     *
     * @see #root()
     * @see Logger#log(Level, Throwable, CharSequence, Object...)
     */
    public static void log(@Nonnull Level level, Throwable e, CharSequence message, Object... params) {
        root().log(level, e, message, params);
    }
}
