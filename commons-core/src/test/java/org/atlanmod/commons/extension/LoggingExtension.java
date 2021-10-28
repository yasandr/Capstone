/*
 * Copyright (c) 2017 Atlanmod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package org.atlanmod.commons.extension;

import org.atlanmod.commons.log.Log;
import org.atlanmod.commons.log.Logger;
import org.atlanmod.commons.primitive.Strings;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;
import org.opentest4j.TestAbortedException;
import org.opentest4j.TestSkippedException;

import java.util.Arrays;
import java.util.Optional;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import static java.util.Objects.nonNull;

/**
 * A JUnit {@link org.junit.jupiter.api.extension.Extension} that logs each test-case calls.
 */
@ParametersAreNonnullByDefault
public class LoggingExtension implements BeforeEachCallback, TestExecutionExceptionHandler, AfterEachCallback {

    /**
     * The special logger without timestamp.
     */
    @Nonnull
    protected static final Logger LOG = Log.forName("test");

    private static final String MSG_RUN = formatState("Running");
    private static final String MSG_PASS = formatState("PASS");
    private static final String MSG_SKIP = formatState("SKIP");
    private static final String MSG_ABORT = formatState("ABORT");
    private static final String MSG_FAIL = formatState("FAIL");
    private static final String MSG_ERROR = formatState("ERROR");

    /**
     * {@code true} if the current test case had an error (failed, skipped, aborted,...)
     */
    private boolean hasErrors;

    /**
     * Formats a state.
     *
     * @param state the state to format
     *
     * @return a formatted {@code state}
     */
    @Nonnull
    private static String formatState(String state) {
        return String.format("--- %s", state);
    }

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        hasErrors = false;

        LOG.info(Strings.EMPTY);
        onRunning(context);
    }

    @Override
    public void handleTestExecutionException(ExtensionContext context, Throwable e) throws Throwable {
        onAnyException(context, e);
        throw e;
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        context.getExecutionException().ifPresent(throwable -> onAnyException(context, throwable));

        if (!hasErrors) {
            onPass(context);
        }
        LOG.info(Strings.EMPTY);

        // Runs the GC after each test
        Runtime.getRuntime().gc();
    }

    /**
     * Handles any {@link Throwable} that occurs during a test-case.
     *
     * @param context the current extension context
     * @param e       the exception to handle
     *
     * @see #onFail(ExtensionContext, AssertionError)
     * @see #onSkip(ExtensionContext, TestSkippedException)
     * @see #onAbort(ExtensionContext, TestAbortedException)
     * @see #onError(ExtensionContext, Throwable)
     */
    private void onAnyException(ExtensionContext context, Throwable e) {
        if (hasErrors) { // Error has already been reported
            return;
        }
        hasErrors = true;

        if (e instanceof AssertionError) {
            onFail(context, (AssertionError) e);
        }
        else if (e instanceof TestSkippedException) {
            onSkip(context, (TestSkippedException) e);
        }
        else if (e instanceof TestAbortedException) {
            onAbort(context, (TestAbortedException) e);
        }
        else {
            onError(context, e);
        }
    }

    /**
     * Handles the start of a test-case.
     *
     * @param context the current extension context
     */
    private void onRunning(ExtensionContext context) {
        String methodName = context.getRequiredTestMethod().getName();

        String displayName = Optional.ofNullable(context.getDisplayName())
                .filter(n -> !n.startsWith(methodName))
                .map(n -> Strings.SPACE + n)
                .orElse(Strings.EMPTY);

        LOG.info("{0}: {1}{2}", MSG_RUN, methodName, displayName);
    }

    /**
     * Handles the success of a test-case.
     *
     * @param context the current extension context
     */
    private void onPass(ExtensionContext context) {
        LOG.info("{0}{1}", MSG_PASS, additionalDetails(context));
    }

    /**
     * Handles an {@link AssertionError}, when a test-case failed.
     *
     * @param context the current extension context
     * @param e       the exception to handle
     */
    private void onFail(ExtensionContext context, AssertionError e) {
        if (nonNull(e.getMessage())) {
            Arrays.stream(e.getMessage().split(Strings.LR))
                    .filter(s -> nonNull(s) && !s.isEmpty())
                    .forEach(LOG::warn);
        }

        LOG.warn("{0}{1}", MSG_FAIL, additionalDetails(context));
    }

    /**
     * Handles a {@link TestSkippedException}, when a test-case is skipped with {@link org.junit.jupiter.api.Disabled}.
     *
     * @param context the current extension context
     * @param e       the exception to handle
     */
    private void onSkip(@SuppressWarnings("unused") ExtensionContext context, TestSkippedException e) {
        if (nonNull(e.getMessage())) {
            final String explanation = e.getMessage();
            LOG.warn("{0}: {1}", MSG_SKIP, explanation);
        }
        else {
            LOG.warn("{0}", MSG_SKIP);
        }
    }

    /**
     * Handles a {@link TestAbortedException}, when a test-case is aborted with {@link
     * org.junit.jupiter.api.Assumptions}.
     *
     * @param context the current extension context
     * @param e       the exception to handle
     */
    private void onAbort(@SuppressWarnings("unused") ExtensionContext context, TestAbortedException e) {
        if (nonNull(e.getMessage())) {
            final String explanation = e.getMessage().replaceFirst("Assumption failed: ", "");
            LOG.warn("{0}: {1}", MSG_ABORT, explanation);
        }
        else {
            LOG.warn("{0}", MSG_ABORT);
        }
    }

    /**
     * Handles a {@link Throwable}, when an unexpected error occured during a test.
     *
     * @param context the current extension context
     * @param e       the exception to handle
     */
    private void onError(ExtensionContext context, Throwable e) {
        LOG.error(e, "{0}{1}", MSG_ERROR, additionalDetails(context));
    }

    @Nonnull
    protected String additionalDetails(ExtensionContext context) {
        return Strings.EMPTY;
    }
}
