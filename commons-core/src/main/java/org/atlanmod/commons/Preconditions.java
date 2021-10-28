/*
 * Copyright (c) 2017 Atlanmod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package org.atlanmod.commons;

import org.atlanmod.commons.annotation.Static;
import org.atlanmod.commons.predicate.*;

import javax.annotation.Nonnegative;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Collection;

/**
 * Static convenience methods that help a method or constructor check whether it was invoked correctly (whether its
 * <i>preconditions</i> have been met). These methods generally accept a {@code boolean} expression which is expected
 * to be {@code true} (or in the case of {@code checkNotNull}, an object reference which is expected to be non-null).
 * When {@code false} (or {@code null}) is passed instead, the {@code Preconditions} method throws an unchecked
 * exception, which helps the calling method communicate to <i>its</i> caller that <i>that</i> caller has made a
 * mistake.
 *
 * <b>Warning:</b> The goal of this class is to improve readability of code, but in some circumstances this may come at
 * a significant performance cost. Remember that parameter values for message construction must all be computed eagerly,
 * and autoboxing and varargs array creation may happen as well, even when the precondition check then succeeds (as it
 * should almost always do in production). In some circumstances these wasted CPU cycles and allocations can add up to a
 * real problem.
 */
@Static
@ParametersAreNonnullByDefault
// Preconditions ensure that these conditions are validated
public final class Preconditions {
    private final static PredicateContext CONTEXT = new PreconditionContext();

    private Preconditions() {
        throw Throwables.notInstantiableClass(getClass());
    }

    // region Deprecated Methods

    /**
     * @deprecated As of release 1.1, replaced by {@link Guards#checkArgument(boolean) }
     */
    @Deprecated
    public static void checkArgument(boolean expression) {
        Guards.checkArgument(expression);
    }

    /**
     * @deprecated As of release 1.1, replaced by {@link Guards#checkArgument(boolean, String)}
     */
    @Deprecated
    public static void checkArgument(boolean expression, String message) {
        Guards.checkArgument(expression, message);
    }

    /**
     * @deprecated As of release 1.1, replaced by {@link Guards#checkArgument(boolean, String, Object...)}
     */
    @Deprecated
    public static void checkArgument(boolean expression, String pattern, Object... args) {
        Guards.checkArgument(expression, pattern, args);
    }


    /**
     * @deprecated As of release 1.1, replaced by {@link Guards#checkState(boolean)}
     */
    @Deprecated
    public static void checkState(boolean expression) {
        Guards.checkState(expression);
    }

    /**
     * @deprecated As of release 1.1, replaced by {@link Guards#checkState(boolean, String)}
     */
    @Deprecated
    public static void checkState(boolean expression, String message) {
        Guards.checkState(expression, message);
    }

    /**
     * @deprecated As of release 1.1, replaced by {@link Guards#checkState(boolean, String, Object...)}
     */
    @Deprecated
    public static void checkState(boolean expression, String pattern, Object... args) {
        Guards.checkState(expression, pattern, args);
    }

    /**
     * @deprecated As of release 1.1, replaced by {@link Guards#checkNotNull(Object)}
     */
    @Deprecated
    public static <T> T checkNotNull(@Nullable T reference) {
        return Guards.checkNotNull(reference);
    }

    /**
     * @deprecated As of release 1.1, replaced by {@link Guards#checkNotNull(Object, String)}
     */
    @Deprecated
    public static <T> T checkNotNull(@Nullable T reference, String message) {
        return Guards.checkNotNull(reference, message);
    }

    /**
     * @deprecated As of release 1.1, replaced by {@link Guards#checkNotNull(Object, String, Object...)}
     */
    @Deprecated
    public static <T> T checkNotNull(@Nullable T reference, String pattern, Object... args) {
        return Guards.checkNotNull(reference, pattern, args);
    }

    /**
     * @deprecated As of release 1.1, replaced by {@link Guards#checkNotContainsNull(Collection)}
     */
    @Deprecated
    public static <C extends Collection<? extends T>, T> C checkNotContainsNull(C collection) {
        return Guards.checkNotContainsNull(collection);
    }

    /**
     * @deprecated As of release 1.1, replaced by {@link Guards#checkNotContainsNull(Collection, String)}
     */
    @Deprecated
    public static <C extends Collection<? extends T>, T> C checkNotContainsNull(C collection, String message) {
        return Guards.checkNotContainsNull(collection, message);
    }

    /**
     * @deprecated As of release 1.1, replaced by {@link Guards#checkNotContainsNull(Collection, String, Object...)}
     */
    @Deprecated
    public static <C extends Collection<? extends T>, T> C checkNotContainsNull(C collection, String pattern, Object... args) {
        return Guards.checkNotContainsNull(collection, pattern, args);
    }

    /**
     * @deprecated As of release 1.1, replaced by {@link Guards#checkInstanceOf(Object, Class)}
     */
    @Deprecated
    public static <T> T checkInstanceOf(T reference, Class<?> type) {
        return Guards.checkInstanceOf(reference, type);
    }

    /**
     * @deprecated As of release 1.1, replaced by {@link Guards#checkInstanceOf(Object, Class, String)}
     */
    @Deprecated
    public static <T> T checkInstanceOf(T reference, Class<?> type, String message) {
        return Guards.checkInstanceOf(reference, type, message);
    }

    /**
     * @deprecated As of release 1.1, replaced by {@link Guards#checkInstanceOf(Object, Class, String, Object...)}
     */
    @Deprecated
    public static <T> T checkInstanceOf(T reference, Class<?> type, String pattern, Object... args) {
        return Guards.checkInstanceOf(reference, type, pattern, args);
    }

    /**
     * @deprecated As of release 1.1, replaced by {@link Guards#checkEqualTo(Object, Object)}
     */
    @Deprecated
    public static <T> T checkEqualTo(@Nullable T value, @Nullable T other) {
        return Guards.checkEqualTo(value, other);
    }

    /**
     * @deprecated As of release 1.1, replaced by {@link Guards#checkEqualTo(Object, Object, String)}
     */
    @Deprecated
    public static <T> T checkEqualTo(@Nullable T value, @Nullable T other, String message) {
        return Guards.checkEqualTo(value, other, message);
    }

    /**
     * @deprecated As of release 1.1, replaced by {@link Guards#checkEqualTo(Object, Object, String, Object...)}
     */
    @Deprecated
    public static <T> T checkEqualTo(@Nullable T value, @Nullable T other, String pattern, Object... args) {
        return Guards.checkEqualTo(value, other, pattern, args);
    }

    /**
     * @deprecated As of release 1.1, replaced by {@link Guards#checkGreaterThan(Comparable, Comparable)}
     */
    @Deprecated
    public static <C extends Comparable<C>> C checkGreaterThan(C value, C lowerBoundExclusive) {
        return Guards.checkGreaterThan(value, lowerBoundExclusive);
    }

    /**
     * @deprecated As of release 1.1, replaced by {@link Guards#checkGreaterThan(Comparable, Comparable, String)}
     */
    @Deprecated
    public static <C extends Comparable<C>> C checkGreaterThan(C value, C lowerBoundExclusive, String message) {
        return Guards.checkGreaterThan(value, lowerBoundExclusive, message);
    }

    /**
     * @deprecated As of release 1.1, replaced by {@link Guards#checkGreaterThan(Comparable, Comparable, String)}
     */
    @Deprecated
    public static <C extends Comparable<C>> C checkGreaterThan(C value, C lowerBoundExclusive, String pattern, Object... args) {
        return Guards.checkGreaterThan(value, lowerBoundExclusive, pattern, args);
    }

    /**
     * @deprecated As of release 1.1, replaced by {@link Guards#checkGreaterThanOrEqualTo(Comparable, Comparable)}
     */
    @Deprecated
    public static <C extends Comparable<C>> C checkGreaterThanOrEqualTo(C value, C lowerBoundInclusive) {
        return Guards.checkGreaterThanOrEqualTo(value, lowerBoundInclusive);
    }

    /**
     * @deprecated As of release 1.1, replaced by {@link Guards#checkGreaterThanOrEqualTo(Comparable, Comparable, String)}
     */
    @Deprecated
    public static <C extends Comparable<C>> C checkGreaterThanOrEqualTo(C value, C lowerBoundInclusive, String message) {
        return Guards.checkGreaterThanOrEqualTo(value, lowerBoundInclusive, message);
    }

    /**
     * @deprecated As of release 1.1, replaced by {@link Guards#checkGreaterThanOrEqualTo(Comparable, Comparable, String, Object...)}
     */
    @Deprecated
    public static <C extends Comparable<C>> C checkGreaterThanOrEqualTo(C value, C lowerBoundInclusive, String pattern, Object... args) {
        return Guards.checkGreaterThanOrEqualTo(value, lowerBoundInclusive, pattern, args);
    }

    /**
     * @deprecated As of release 1.1, replaced by {@link Guards#checkLessThan(Comparable, Comparable)}
     */
    @Deprecated
    public static <C extends Comparable<C>> C checkLessThan(C value, C upperBoundExclusive) {
        return Guards.checkLessThan(value, upperBoundExclusive);
    }

    /**
     * @deprecated As of release 1.1, replaced by {@link Guards#checkLessThan(Comparable, Comparable, String)}
     */
    @Deprecated
    public static <C extends Comparable<C>> C checkLessThan(C value, C upperBoundExclusive, String message) {
        return Guards.checkLessThan(value, upperBoundExclusive, message);
    }

    /**
     * @deprecated As of release 1.1, replaced by {@link Guards#checkLessThan(Comparable, Comparable, String, Object...)}
     */
    @Deprecated
    public static <C extends Comparable<C>> C checkLessThan(C value, C upperBoundExclusive, String pattern, Object... args) {
        return Guards.checkLessThan(value, upperBoundExclusive, pattern, args);
    }

    /**
     * @deprecated As of release 1.1, replaced by {@link Guards#checkLessThanOrEqualTo(Comparable, Comparable)}
     */
    @Deprecated
    public static <C extends Comparable<C>> C checkLessThanOrEqualTo(C value, C upperBoundInclusive) {
        return Guards.checkLessThanOrEqualTo(value, upperBoundInclusive);
    }

    /**
     * @deprecated As of release 1.1, replaced by {@link Guards#checkLessThanOrEqualTo(Comparable, Comparable)}
     */
    @Deprecated
    public static <C extends Comparable<C>> C checkLessThanOrEqualTo(C value, C upperBoundInclusive, String message) {
        return Guards.checkLessThanOrEqualTo(value, upperBoundInclusive, message);
    }

    /**
     * @deprecated As of release 1.1, replaced by {@link Guards#checkLessThanOrEqualTo(Comparable, Comparable, String, Object...)}
     */
    @Deprecated
    public static <C extends Comparable<C>> C checkLessThanOrEqualTo(C value, C upperBoundInclusive, String pattern, Object... args) {
        return Guards.checkLessThanOrEqualTo(value, upperBoundInclusive, pattern, args);
    }

    /**
     * @deprecated As of release 1.1, replaced by {@link Guards#checkElementIndex(int, int)}
     */
    @Deprecated
    public static int checkElementIndex(@Nonnegative int index, @Nonnegative int size) {
        return Guards.checkElementIndex(index, size);
    }

    /**
     * @deprecated As of release 1.1, replaced by {@link Guards#checkPositionIndex(int, int)}
     */
    @Deprecated
    public static int checkPositionIndex(@Nonnegative int index, @Nonnegative int size) {
        return Guards.checkPositionIndex(index, size);
    }

    // endregion

    // region Preconditions

    public static IntPredicate requireThat(int expression) {
        return new IntPredicate(CONTEXT, expression);
    }

    public static LongPredicate requireThat(long expression) {
        return new LongPredicate(CONTEXT, expression);
    }

    public static BooleanPredicate requireThat(boolean expression) {
        return new BooleanPredicate(CONTEXT, expression);
    }

    public static StringPredicate requireThat(String expression) {
        return new StringPredicate(CONTEXT, expression);
    }

    public static ObjectPredicate<ObjectPredicate, Object> requireThat(Object expression) {
        return new ObjectPredicate(CONTEXT, expression);
    }
    // endregion

    static class PreconditionContext implements PredicateContext {
        private PreconditionContext() {
        }

        @Override
        public void send(String pattern, Object... args) {
            throw new PreconditionError(String.format(pattern, args));
        }
    }

    public static class PreconditionError extends AssertionError {
        public PreconditionError(String message) {
            super(message);
        }
    }
}
