/*
 * Copyright (c) 2021 Naomod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */
package org.atlanmod.commons;

import org.atlanmod.commons.annotation.Static;
import org.jetbrains.annotations.Contract;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Collection;
import java.util.Objects;

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
 *
 * @since 1.1.0
 */
@Static
@ParametersAreNonnullByDefault
public class Guards {

    /**
     * A string representing a logging without message.
     */
    @Nullable
    private static final String NO_MESSAGE = null;

    /**
     * An empty array representing logging without parameters.
     */
    @Nonnull
    private static final Object[] NO_PARAMS = new Object[0];

    private Guards() {
        throw Throwables.notInstantiableClass(getClass());
    }

    // region Argument

    /**
     * Ensures the truth of an expression involving one or more parameters to the calling method.
     *
     * @param expression a boolean expression
     *
     * @throws IllegalArgumentException if {@code expression} is false
     */
    @Contract(value = "false -> fail", pure = true)
    public static void checkArgument(boolean expression) {
        checkArgument(expression, NO_MESSAGE);
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to the calling method.
     *
     * @param expression a boolean expression
     * @param message    the exception message to use if the check fails
     *
     * @throws IllegalArgumentException if {@code expression} is false
     */
    @Contract(value = "false, _ -> fail", pure = true)
    public static void checkArgument(boolean expression, String message) {
        checkArgument(expression, message, NO_PARAMS);
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to the calling method.
     *
     * @param expression a boolean expression
     * @param pattern    a template for the exception message should the check fail
     * @param args       the arguments to be substituted into the message template
     *
     * @throws IllegalArgumentException if {@code expression} is false
     * @throws NullPointerException     if the check fails and either {@code pattern} or {@code args} is null
     */
    @Contract(value = "false, _, _ -> fail", pure = true)
    public static void checkArgument(boolean expression, String pattern, Object... args) {
        if (!expression) {
            throw new IllegalArgumentException(format(pattern, args));
        }
    }

    // endregion

    // region State

    /**
     * Ensures the truth of an expression involving the state of the calling instance, but not involving any parameters
     * to the calling method.
     *
     * @param expression a boolean expression
     *
     * @throws IllegalStateException if {@code expression} is false
     */
    @Contract(value = "false -> fail", pure = true)
    public static void checkState(boolean expression) {
        checkState(expression, NO_MESSAGE);
    }

    /**
     * Ensures the truth of an expression involving the state of the calling instance, but not involving any parameters
     * to the calling method.
     *
     * @param expression a boolean expression
     * @param message    the exception message to use if the check fails
     *
     * @throws IllegalStateException if {@code expression} is false
     */
    @Contract(value = "false, _ -> fail", pure = true)
    public static void checkState(boolean expression, String message) {
        checkState(expression, message, NO_PARAMS);
    }

    /**
     * Ensures the truth of an expression involving the state of the calling instance, but not involving any parameters
     * to the calling method.
     *
     * @param expression a boolean expression
     * @param pattern    a template for the exception message should the check fail
     * @param args       the arguments to be substituted into the message template
     *
     * @throws IllegalStateException if {@code expression} is false
     * @throws NullPointerException  if the check fails and either {@code pattern} or {@code args} is null
     */
    @Contract(value = "false, _, _ -> fail", pure = true)
    public static void checkState(boolean expression, String pattern, Object... args) {
        if (!expression) {
            throw new IllegalStateException(format(pattern, args));
        }
    }

    // endregion

    // region Nullity

    /**
     * Ensures that an object {@code reference} passed as a parameter to the calling method is not {@code null}.
     *
     * @param reference an object reference
     *
     * @return the non-null reference that was validated
     *
     * @throws NullPointerException if {@code reference} is null
     */
    @Nonnull
    @Contract(value = "null -> fail", pure = true)
    public static <T> T checkNotNull(@Nullable T reference) {
        return checkNotNull(reference, NO_MESSAGE);
    }

    /**
     * Ensures that an object {@code reference} passed as a parameter to the calling method is not {@code null}.
     *
     * @param reference an object reference
     * @param message   the exception message to use if the check fails
     *
     * @return the non-null reference that was validated
     *
     * @throws NullPointerException if {@code reference} is null
     */
    @Nonnull
    @Contract(value = "null, _ -> fail", pure = true)
    public static <T> T checkNotNull(@Nullable T reference, String message) {
        return checkNotNull(reference, message, NO_PARAMS);
    }

    /**
     * Ensures that an object {@code reference} passed as a parameter to the calling method is not {@code null}.
     *
     * @param reference an object reference
     * @param pattern   a template for the exception message should the check fail
     * @param args      the arguments to be substituted into the message template. Arguments are converted to strings
     *                  using {@link String#valueOf(Object)}.
     *
     * @return the non-null reference that was validated
     *
     * @throws NullPointerException if {@code reference} is null
     */
    @Nonnull
    @Contract(value = "null, _, _ -> fail", pure = true)
    public static <T> T checkNotNull(@Nullable T reference, String pattern, Object... args) {
        if (null == reference) {
            throw new NullPointerException(format(pattern, args));
        }
        return reference;
    }

    /**
     * Ensures that a {@code collection} passed as a parameter to the calling method is not {@code null} and does not
     * contains any {@code null} element.
     *
     * @param collection a collection
     *
     * @return the non-null collection that was validated
     *
     * @throws NullPointerException if {@code collection} is null or contains at least one {@code null} element
     */
    @Nonnull
    public static <C extends Collection<? extends T>, T> C checkNotContainsNull(C collection) {
        return checkNotContainsNull(collection, "the collection contains at least one null element");
    }

    /**
     * Ensures that a {@code collection} passed as a parameter to the calling method is not {@code null} and does not
     * contains any {@code null} element.
     *
     * @param collection a collection
     * @param message    the exception message to use if the check fails
     *
     * @return the non-null collection that was validated
     *
     * @throws NullPointerException if {@code collection} is null or contains at least one {@code null} element
     */
    @Nonnull
    public static <C extends Collection<? extends T>, T> C checkNotContainsNull(C collection, String message) {
        return checkNotContainsNull(collection, message, NO_PARAMS);
    }

    /**
     * Ensures that a {@code collection} passed as a parameter to the calling method is not {@code null} and does not
     * contains any {@code null} element.
     *
     * @param collection a collection
     * @param pattern    a template for the exception message should the check fail
     * @param args       the arguments to be substituted into the message template. Arguments are converted to strings
     *                   using {@link String#valueOf(Object)}.
     *
     * @return the non-null collection that was validated
     *
     * @throws NullPointerException if {@code collection} is null or contains at least one {@code null} element
     */
    @Nonnull
    public static <C extends Collection<? extends T>, T> C checkNotContainsNull(C collection, String pattern, Object... args) {
        checkNotNull(collection, "collection");

        if (collection.contains(null)) {
            throw new NullPointerException(format(pattern, args));
        }
        return collection;
    }

    // endregion

    // region Instance

    /**
     * Ensures that an object {@code reference} passed as a parameter to the calling method is instance of {@code
     * type}.
     *
     * @param reference an object reference
     * @param type      the expected type of {@code reference}
     *
     * @return the reference that was validated
     *
     * @throws IllegalArgumentException if {@code reference} is not instance of {@code type}
     */
    @Nonnull
    public static <T> T checkInstanceOf(T reference, Class<?> type) {
        return checkInstanceOf(reference, type, "object must be instance of '%s', but was '%s'", type.getName(), reference.getClass());
    }

    /**
     * Ensures that an object {@code reference} passed as a parameter to the calling method is instance of {@code
     * type}.
     *
     * @param reference an object reference
     * @param type      the expected type of {@code reference}
     * @param message   the exception message to use if the check fails
     *
     * @return the reference that was validated
     *
     * @throws IllegalArgumentException if {@code reference} is not instance of {@code type}
     */
    @Nonnull
    public static <T> T checkInstanceOf(T reference, Class<?> type, String message) {
        return checkInstanceOf(reference, type, message, NO_PARAMS);
    }

    /**
     * Ensures that an object {@code reference} passed as a parameter to the calling method is instance of {@code
     * type}.
     *
     * @param reference an object reference
     * @param type      the expected type of {@code reference}
     * @param pattern   a template for the exception message should the check fail
     * @param args      the arguments to be substituted into the message template. Arguments are converted to strings
     *                  using {@link String#valueOf(Object)}.
     *
     * @return the reference that was validated
     *
     * @throws IllegalArgumentException if {@code reference} is not instance of {@code type}
     */
    @Nonnull
    public static <T> T checkInstanceOf(T reference, Class<?> type, String pattern, Object... args) {
        checkNotNull(reference, "reference");
        checkNotNull(type, "type");

        if (!type.isInstance(reference)) {
            throw new IllegalArgumentException(format(pattern, args));
        }
        return reference;
    }

    // endregion

    // region Comparison

    /**
     * Ensures that a {@code value} passed as a parameter to the calling method is equal to {@code other}.
     *
     * @param value an object reference
     * @param other another object reference
     *
     * @return the value that was validated
     *
     * @throws IllegalArgumentException if {@code value} is not equal to {@code other}
     */
    public static <T> T checkEqualTo(@Nullable T value, @Nullable T other) {
        return checkEqualTo(value, other, "value (%s) must be equal to %s", value, other);
    }

    /**
     * Ensures that a {@code value} passed as a parameter to the calling method is equal to {@code other}.
     *
     * @param value   an object reference
     * @param other   another object reference
     * @param message the exception message to use if the check fails
     *
     * @return the value that was validated
     *
     * @throws IllegalArgumentException if {@code value} is not equal to {@code other}
     */
    public static <T> T checkEqualTo(@Nullable T value, @Nullable T other, String message) {
        return checkEqualTo(value, other, message, NO_PARAMS);
    }

    /**
     * Ensures that a {@code value} passed as a parameter to the calling method is equal to {@code other}.
     *
     * @param value   an object reference
     * @param other   another object reference
     * @param pattern a template for the exception message should the check fail
     * @param args    the arguments to be substituted into the message template. Arguments are converted to strings
     *                using {@link String#valueOf(Object)}.
     *
     * @return the value that was validated
     *
     * @throws IllegalArgumentException if {@code value} is not equal to {@code other}
     */
    public static <T> T checkEqualTo(@Nullable T value, @Nullable T other, String pattern, Object... args) {
        if (!Objects.equals(value, other)) {
            throw new IllegalArgumentException(format(pattern, args));
        }
        return value;
    }

    /**
     * Ensures that a {@code value} passed as a parameter to the calling method is strictly greater than {@code
     * lowerBoundExclusive}.
     *
     * @param value               a comparable
     * @param lowerBoundExclusive the lower bound (exclusive)
     *
     * @return the value that was validated
     *
     * @throws IllegalArgumentException if {@code value} is less than or equal to {@code lowerBoundExclusive}
     */
    @Nonnull
    public static <C extends Comparable<C>> C checkGreaterThan(C value, C lowerBoundExclusive) {
        return checkGreaterThan(value, lowerBoundExclusive, "value (%s) must be greater than %s", value, lowerBoundExclusive);
    }

    /**
     * Ensures that a {@code value} passed as a parameter to the calling method is strictly greater than {@code
     * lowerBoundExclusive}.
     *
     * @param value               a comparable
     * @param lowerBoundExclusive the lower bound (exclusive)
     * @param message             the exception message to use if the check fails
     *
     * @return the value that was validated
     *
     * @throws IllegalArgumentException if {@code value} is less than or equal to {@code lowerBoundExclusive}
     */
    @Nonnull
    public static <C extends Comparable<C>> C checkGreaterThan(C value, C lowerBoundExclusive, String message) {
        return checkGreaterThan(value, lowerBoundExclusive, message, NO_PARAMS);
    }

    /**
     * Ensures that a {@code value} passed as a parameter to the calling method is strictly greater than {@code
     * lowerBoundExclusive}.
     *
     * @param value               a comparable
     * @param lowerBoundExclusive the lower bound (exclusive)
     * @param pattern             a template for the exception message should the check fail
     * @param args                the arguments to be substituted into the message template. Arguments are converted to
     *                            strings using {@link String#valueOf(Object)}.
     *
     * @return the value that was validated
     *
     * @throws IllegalArgumentException if {@code value} is less than or equal to {@code lowerBoundExclusive}
     */
    @Nonnull
    public static <C extends Comparable<C>> C checkGreaterThan(C value, C lowerBoundExclusive, String pattern, Object... args) {
        checkNotNull(value, "value");
        checkNotNull(lowerBoundExclusive, "lowerBoundExclusive");

        if (value.compareTo(lowerBoundExclusive) <= 0) {
            throw new IllegalArgumentException(format(pattern, args));
        }
        return value;
    }

    /**
     * Ensures that a {@code value} passed as a parameter to the calling method is greater than or equal to {@code
     * lowerBoundInclusive}.
     *
     * @param value               a comparable
     * @param lowerBoundInclusive the lower bound (inclusive)
     *
     * @return the value that was validated
     *
     * @throws IllegalArgumentException if {@code value} is less than {@code lowerBoundInclusive}
     */
    @Nonnull
    public static <C extends Comparable<C>> C checkGreaterThanOrEqualTo(C value, C lowerBoundInclusive) {
        return checkGreaterThanOrEqualTo(value, lowerBoundInclusive, "value (%s) must not be less than %s", value, lowerBoundInclusive);
    }

    /**
     * Ensures that a {@code value} passed as a parameter to the calling method is greater than or equal to {@code
     * lowerBoundInclusive}.
     *
     * @param value               a comparable
     * @param lowerBoundInclusive the lower bound (inclusive)
     * @param message             the exception message to use if the check fails
     *
     * @return the value that was validated
     *
     * @throws IllegalArgumentException if {@code value} is less than {@code lowerBoundInclusive}
     */
    @Nonnull
    public static <C extends Comparable<C>> C checkGreaterThanOrEqualTo(C value, C lowerBoundInclusive, String message) {
        return checkGreaterThanOrEqualTo(value, lowerBoundInclusive, message, NO_PARAMS);
    }

    /**
     * Ensures that a {@code value} passed as a parameter to the calling method is greater than or equal to {@code
     * lowerBoundInclusive}.
     *
     * @param value               a comparable
     * @param lowerBoundInclusive the lower bound (inclusive)
     * @param pattern             a template for the exception message should the check fail
     * @param args                the arguments to be substituted into the message template. Arguments are converted to
     *                            strings using {@link String#valueOf(Object)}.
     *
     * @return the value that was validated
     *
     * @throws IllegalArgumentException if {@code value} is less than {@code lowerBoundInclusive}
     */
    @Nonnull
    public static <C extends Comparable<C>> C checkGreaterThanOrEqualTo(C value, C lowerBoundInclusive, String pattern, Object... args) {
        checkNotNull(value, "value");
        checkNotNull(lowerBoundInclusive, "lowerBoundInclusive");

        if (value.compareTo(lowerBoundInclusive) < 0) {
            throw new IllegalArgumentException(format(pattern, args));
        }
        return value;
    }

    /**
     * Ensures that a {@code value} passed as a parameter to the calling method is strictly less than {@code
     * upperBoundExclusive}.
     *
     * @param value               a comparable
     * @param upperBoundExclusive the upper bound (exclusive)
     *
     * @return the value that was validated
     *
     * @throws IllegalArgumentException if {@code value} is greater than or equal to {@code upperBoundExclusive}
     */
    @Nonnull
    public static <C extends Comparable<C>> C checkLessThan(C value, C upperBoundExclusive) {
        return checkLessThan(value, upperBoundExclusive, "value (%s) must be less than %s", value, upperBoundExclusive);
    }

    /**
     * Ensures that a {@code value} passed as a parameter to the calling method is strictly less than {@code
     * upperBoundExclusive}.
     *
     * @param value               a comparable
     * @param upperBoundExclusive the upper bound (exclusive)
     * @param message             the exception message to use if the check fails
     *
     * @return the value that was validated
     *
     * @throws IllegalArgumentException if {@code value} is greater than or equal to {@code upperBoundExclusive}
     */
    @Nonnull
    public static <C extends Comparable<C>> C checkLessThan(C value, C upperBoundExclusive, String message) {
        return checkLessThan(value, upperBoundExclusive, message, NO_PARAMS);
    }

    /**
     * Ensures that a {@code value} passed as a parameter to the calling method is strictly less than {@code
     * upperBoundExclusive}.
     *
     * @param value               a comparable
     * @param upperBoundExclusive the upper bound (exclusive)
     * @param pattern             a template for the exception message should the check fail
     * @param args                the arguments to be substituted into the message template. Arguments are converted to
     *                            strings using {@link String#valueOf(Object)}.
     *
     * @return the value that was validated
     *
     * @throws IllegalArgumentException if {@code value} is greater than or equal to {@code upperBoundExclusive}
     */
    @Nonnull
    public static <C extends Comparable<C>> C checkLessThan(C value, C upperBoundExclusive, String pattern, Object... args) {
        checkNotNull(value, "value");
        checkNotNull(upperBoundExclusive, "upperBoundExclusive");

        if (value.compareTo(upperBoundExclusive) >= 0) {
            throw new IllegalArgumentException(format(pattern, args));
        }
        return value;
    }

    /**
     * Ensures that a {@code value} passed as a parameter to the calling method is less than or equal to {@code
     * upperBoundInclusive}.
     *
     * @param value               a comparable
     * @param upperBoundInclusive the upper bound (inclusive)
     *
     * @return the value that was validated
     *
     * @throws IllegalArgumentException if {@code value} is greater than {@code upperBoundInclusive}
     */
    @Nonnull
    public static <C extends Comparable<C>> C checkLessThanOrEqualTo(C value, C upperBoundInclusive) {
        return checkLessThanOrEqualTo(value, upperBoundInclusive, "value (%s) must not be greater than %s", value, upperBoundInclusive);
    }

    /**
     * Ensures that a {@code value} passed as a parameter to the calling method is less than or equal to {@code
     * upperBoundInclusive}.
     *
     * @param value               a comparable
     * @param upperBoundInclusive the upper bound (inclusive)
     * @param message             the exception message to use if the check fails
     *
     * @return the value that was validated
     *
     * @throws IllegalArgumentException if {@code value} is greater than {@code upperBoundInclusive}
     */
    @Nonnull
    public static <C extends Comparable<C>> C checkLessThanOrEqualTo(C value, C upperBoundInclusive, String message) {
        return checkLessThanOrEqualTo(value, upperBoundInclusive, message, NO_PARAMS);
    }

    /**
     * Ensures that a {@code value} passed as a parameter to the calling method is less than or equal to {@code
     * upperBoundInclusive}.
     *
     * @param value               a comparable
     * @param upperBoundInclusive the upper bound (inclusive)
     * @param pattern             a template for the exception message should the check fail
     * @param args                the arguments to be substituted into the message template. Arguments are converted to
     *                            strings using {@link String#valueOf(Object)}.
     *
     * @return the value that was validated
     *
     * @throws IllegalArgumentException if {@code value} is greater than {@code upperBoundInclusive}
     */
    @Nonnull
    public static <C extends Comparable<C>> C checkLessThanOrEqualTo(C value, C upperBoundInclusive, String pattern, Object... args) {
        checkNotNull(value, "value");
        checkNotNull(upperBoundInclusive, "upperBoundInclusive");

        if (value.compareTo(upperBoundInclusive) > 0) {
            throw new IllegalArgumentException(format(pattern, args));
        }
        return value;
    }

    // endregion

    // region Array/Collection

    /**
     * Ensures that {@code index} specifies a valid <i>element</i> in an array, list or string of {@code size}. An
     * element index may range from zero, inclusive, to {@code size}, exclusive.
     *
     * @param index a user-supplied index identifying an element of an array, list or string
     * @param size  the size of that array, list or string
     *
     * @return the index that was validated
     *
     * @throws IndexOutOfBoundsException if {@code index} is negative or is not less than {@code size}
     * @throws IllegalArgumentException  if {@code size} is negative
     */
    @Nonnegative
    public static int checkElementIndex(@Nonnegative int index, @Nonnegative int size) {
        if (index < 0) {
            throw new IndexOutOfBoundsException(format("index (%d) must not be negative", index));
        }
        else if (size < 0) {
            throw new IllegalArgumentException(format("size (%d) must not be negative", size));
        }
        else if (index >= size) {
            throw new IndexOutOfBoundsException(format("index (%d) must be less than size (%d)", index, size));
        }
        return index;
    }

    /**
     * Ensures that {@code index} specifies a valid <i>position</i> in an array, list or string of {@code size}. A
     * position index may range from zero to {@code size}, inclusive.
     *
     * @param index a user-supplied index identifying a position in an array, list or string
     * @param size  the size of that array, list or string
     *
     * @return the index that was validated
     *
     * @throws IndexOutOfBoundsException if {@code index} is negative or is greater than {@code size}
     * @throws IllegalArgumentException  if {@code size} is negative
     */
    @Nonnegative
    public static int checkPositionIndex(@Nonnegative int index, @Nonnegative int size) {
        if (index < 0) {
            throw new IndexOutOfBoundsException(format("index (%d) must not be negative", index));
        }
        else if (size < 0) {
            throw new IllegalArgumentException(format("size (%d) must not be negative", size));
        }
        else if (index > size) {
            throw new IndexOutOfBoundsException(format("index (%d) must not be greater than size (%d)", index, size));
        }
        return index;
    }

    // endregion

    /**
     * Returns a formatted string using the specified format string and arguments.
     *
     * @param pattern a format string
     * @param args    the arguments referenced by the format specifiers in the format string
     *
     * @return a formatted string
     */
    @Nullable
    private static String format(String pattern, Object... args) {
        return null != pattern ? String.format(pattern, args) : null;
    }
}
