/*
 * Copyright (c) 2017 Atlanmod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package org.atlanmod.commons.log;

import java.text.MessageFormat;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNullableByDefault;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;

/**
 * An object that provides logging operations, filtered by {@link Level}s.
 * <p>
 * Implementations of this interface are expected to be thread-safe, and can be safely accessed by multiple concurrent
 * threads.
 */
@FunctionalInterface
@ThreadSafe
@Immutable
@ParametersAreNullableByDefault
public interface Logger {

    /**
     * A throwable representing a logging without exception.
     * <p>
     * <b>WARNING:</b> This field is present to ease the redirection of the methods of this interface; don't use it
     * elsewhere.
     */
    @Nullable
    Throwable NO_EXCEPTION = null;

    /**
     * A string representing a logging without message.
     * <p>
     * <b>WARNING:</b> This field is present to ease the redirection of the methods of this interface; don't use it
     * elsewhere.
     */
    @Nullable
    CharSequence NO_MESSAGE = null;

    /**
     * An empty array representing logging without parameters.
     * <p>
     * <b>WARNING:</b> This field is present to ease the redirection of the methods of this interface; don't use it
     * elsewhere.
     */
    @Nonnull
    Object[] NO_PARAMS = new Object[0];

    /**
     * Logs an object at the {@link Level#TRACE TRACE} level.
     *
     * @param obj the object to log
     */
    default void trace(Object obj) {
        trace(String.valueOf(obj));
    }

    /**
     * Logs a message at the {@link Level#TRACE TRACE} level.
     *
     * @param message the message to log
     */
    default void trace(CharSequence message) {
        trace(NO_EXCEPTION, message, NO_PARAMS);
    }

    /**
     * Logs a message with parameters at the {@link Level#TRACE TRACE} level.
     *
     * @param message the message to log; the format depends on the {@link MessageFormat}
     * @param params  parameters to the message
     */
    default void trace(CharSequence message, Object... params) {
        trace(NO_EXCEPTION, message, params);
    }

    /**
     * Logs the stack trace of the given {@link Throwable} at the {@link Level#TRACE TRACE} level.
     *
     * @param e the exception to log, including its stack trace
     */
    default void trace(Throwable e) {
        trace(e, NO_MESSAGE, NO_PARAMS);
    }

    /**
     * Logs a message at the {@link Level#TRACE TRACE} level including the stack trace of the given {@link Throwable}.
     *
     * @param e       the exception to log, including its stack trace
     * @param message the message to log
     */
    default void trace(Throwable e, CharSequence message) {
        trace(e, message, NO_PARAMS);
    }

    /**
     * Logs a message with parameters at the {@link Level#TRACE TRACE} level including the stack trace of the given
     * {@link Throwable}.
     *
     * @param e       the exception to log, including its stack trace
     * @param message the message to log; the format depends on the {@link MessageFormat}
     * @param params  parameters to the message
     */
    default void trace(Throwable e, CharSequence message, Object... params) {
        log(Level.TRACE, e, message, params);
    }

    /**
     * Logs an object at the {@link Level#DEBUG DEBUG} level.
     *
     * @param obj the object to log
     */
    default void debug(Object obj) {
        debug(String.valueOf(obj));
    }

    /**
     * Logs a message at the {@link Level#DEBUG DEBUG} level.
     *
     * @param message the message to log
     */
    default void debug(CharSequence message) {
        debug(NO_EXCEPTION, message, NO_PARAMS);
    }

    /**
     * Logs a message with parameters at the {@link Level#DEBUG DEBUG} level.
     *
     * @param message the message to log; the format depends on the {@link MessageFormat}
     * @param params  parameters to the message
     */
    default void debug(CharSequence message, Object... params) {
        debug(NO_EXCEPTION, message, params);
    }

    /**
     * Logs the stack trace of the given {@link Throwable} at the {@link Level#DEBUG DEBUG} level.
     *
     * @param e the exception to log, including its stack trace
     */
    default void debug(Throwable e) {
        debug(e, NO_MESSAGE, NO_PARAMS);
    }

    /**
     * Logs a message at the {@link Level#DEBUG DEBUG} level including the stack trace of the given {@link Throwable}.
     *
     * @param e       the exception to log, including its stack trace
     * @param message the message to log
     */
    default void debug(Throwable e, CharSequence message) {
        debug(e, message, NO_PARAMS);
    }

    /**
     * Logs a message with parameters at the {@link Level#DEBUG DEBUG} level including the stack trace of the given
     * {@link Throwable}.
     *
     * @param e       the exception to log, including its stack trace
     * @param message the message to log; the format depends on the {@link MessageFormat}
     * @param params  parameters to the message
     */
    default void debug(Throwable e, CharSequence message, Object... params) {
        log(Level.DEBUG, e, message, params);
    }

    /**
     * Logs an object at the {@link Level#INFO INFO} level.
     *
     * @param obj the object to log
     */
    default void info(Object obj) {
        info(String.valueOf(obj));
    }

    /**
     * Logs a message at the {@link Level#INFO INFO} level.
     *
     * @param message the message to log
     */
    default void info(CharSequence message) {
        info(NO_EXCEPTION, message, NO_PARAMS);
    }

    /**
     * Logs a message with parameters at the {@link Level#INFO INFO} level.
     *
     * @param message the message to log; the format depends on the {@link MessageFormat}
     * @param params  parameters to the message
     */
    default void info(CharSequence message, Object... params) {
        info(NO_EXCEPTION, message, params);
    }

    /**
     * Logs the stack trace of the given {@link Throwable} at the {@link Level#INFO INFO} level.
     *
     * @param e the exception to log, including its stack trace
     */
    default void info(Throwable e) {
        info(e, NO_MESSAGE, NO_PARAMS);
    }

    /**
     * Logs a message at the {@link Level#INFO INFO} level including the stack trace of the given {@link Throwable}.
     *
     * @param e       the exception to log, including its stack trace
     * @param message the message to log
     */
    default void info(Throwable e, CharSequence message) {
        info(e, message, NO_PARAMS);
    }

    /**
     * Logs a message with parameters at the {@link Level#INFO INFO} level including the stack trace of the given {@link
     * Throwable}.
     *
     * @param e       the exception to log, including its stack trace
     * @param message the message to log; the format depends on the {@link MessageFormat}
     * @param params  parameters to the message
     */
    default void info(Throwable e, CharSequence message, Object... params) {
        log(Level.INFO, e, message, params);
    }

    /**
     * Logs an object at the {@link Level#WARN WARN} level.
     *
     * @param obj the object to log
     */
    default void warn(Object obj) {
        warn(String.valueOf(obj));
    }

    /**
     * Logs a message at the {@link Level#WARN WARN} level.
     *
     * @param message the message to log
     */
    default void warn(CharSequence message) {
        warn(NO_EXCEPTION, message, NO_PARAMS);
    }

    /**
     * Logs a message with parameters at the {@link Level#WARN WARN} level.
     *
     * @param message the message to log; the format depends on the {@link MessageFormat}
     * @param params  parameters to the message
     */
    default void warn(CharSequence message, Object... params) {
        warn(NO_EXCEPTION, message, params);
    }

    /**
     * Logs the stack trace of the given {@link Throwable} at the {@link Level#WARN WARN} level.
     *
     * @param e the exception to log, including its stack trace
     */
    default void warn(Throwable e) {
        warn(e, NO_MESSAGE, NO_PARAMS);
    }

    /**
     * Logs a message at the {@link Level#WARN WARN} level including the stack trace of the given {@link Throwable}.
     *
     * @param e       the exception to log, including its stack trace
     * @param message the message to log
     */
    default void warn(Throwable e, CharSequence message) {
        warn(e, message, NO_PARAMS);
    }

    /**
     * Logs a message with parameters at the {@link Level#WARN WARN} level including the stack trace of the given {@link
     * Throwable}.
     *
     * @param e       the exception to log, including its stack trace
     * @param message the message to log; the format depends on the {@link MessageFormat}
     * @param params  parameters to the message
     */
    default void warn(Throwable e, CharSequence message, Object... params) {
        log(Level.WARN, e, message, params);
    }

    /**
     * Logs an object at the {@link Level#ERROR ERROR} level.
     *
     * @param obj the object to log
     */
    default void error(Object obj) {
        error(String.valueOf(obj));
    }

    /**
     * Logs a message at the {@link Level#ERROR ERROR} level.
     *
     * @param message the message to log
     */
    default void error(CharSequence message) {
        error(NO_EXCEPTION, message, NO_PARAMS);
    }

    /**
     * Logs a message with parameters at the {@link Level#ERROR ERROR} level.
     *
     * @param message the message to log; the format depends on the {@link MessageFormat}
     * @param params  parameters to the message
     */
    default void error(CharSequence message, Object... params) {
        error(NO_EXCEPTION, message, params);
    }

    /**
     * Logs the stack trace of the given {@link Throwable} at the {@link Level#ERROR ERROR} level.
     *
     * @param e the exception to log, including its stack trace
     */
    default void error(Throwable e) {
        error(e, NO_MESSAGE, NO_PARAMS);
    }

    /**
     * Logs a message at the {@link Level#ERROR ERROR} level including the stack trace of the given {@link Throwable}.
     *
     * @param e       the exception to log, including its stack trace
     * @param message the message to log
     */
    default void error(Throwable e, CharSequence message) {
        error(e, message, NO_PARAMS);
    }

    /**
     * Logs a message with parameters at the {@link Level#ERROR ERROR} level including the stack trace of the given
     * {@link Throwable}.
     *
     * @param e       the exception to log, including its stack trace
     * @param message the message to log; the format depends on the {@link MessageFormat}
     * @param params  parameters to the message
     */
    default void error(Throwable e, CharSequence message, Object... params) {
        log(Level.ERROR, e, message, params);
    }

    /**
     * Logs an object at the given {@code level}.
     *
     * @param level the logging level
     * @param obj   the object to log
     */
    default void log(@Nonnull Level level, Object obj) {
        log(level, String.valueOf(obj));
    }

    /**
     * Logs a message at the given {@code level}.
     *
     * @param level   the logging level
     * @param message the message to log
     */
    default void log(@Nonnull Level level, CharSequence message) {
        log(level, NO_EXCEPTION, message, NO_PARAMS);
    }

    /**
     * Logs a message with parameters at the given {@code level}.
     *
     * @param level   the logging level
     * @param message the message to log; the format depends on the {@link MessageFormat}
     * @param params  parameters to the message
     */
    default void log(@Nonnull Level level, CharSequence message, Object... params) {
        log(level, NO_EXCEPTION, message, params);
    }

    /**
     * Logs the stack trace of the given {@link Throwable} at the given {@code level}.
     *
     * @param level the logging level
     * @param e     the exception to log, including its stack trace
     */
    default void log(@Nonnull Level level, Throwable e) {
        log(level, e, NO_MESSAGE, NO_PARAMS);
    }

    /**
     * Logs a message at the given {@code level} including the stack trace of the given {@link Throwable}.
     *
     * @param level   the logging level
     * @param e       the exception to log, including its stack trace
     * @param message the message to log
     */
    default void log(@Nonnull Level level, Throwable e, CharSequence message) {
        log(level, e, message, NO_PARAMS);
    }

    /**
     * Logs a message with parameters at the given {@code level} including the stack trace of the given {@link
     * Throwable}.
     *
     * @param level   the logging level
     * @param e       the exception to log, including its stack trace
     * @param message the message to log; the format depends on the {@link MessageFormat}
     * @param params  parameters to the message
     */
    void log(@Nonnull Level level, Throwable e, CharSequence message, Object... params);
}
