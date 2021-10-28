/*
 * Copyright (c) 2017 Atlanmod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package org.atlanmod.commons.primitive;

import org.atlanmod.commons.Throwables;
import org.atlanmod.commons.annotation.Static;

import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import static java.util.Objects.isNull;
import static org.atlanmod.commons.Guards.checkEqualTo;
import static org.atlanmod.commons.Guards.checkNotNull;

/**
 * Static utility methods related to {@link String}.
 */
@Static
@ParametersAreNonnullByDefault
public final class Strings {

    /**
     * The empty string {@code ""}.
     */
    @Nonnull
    public static final String EMPTY = "";

    /**
     * A string for a space character.
     */
    @Nonnull
    public static final String SPACE = " ";

    /**
     * A string for a line separator character, dependencing on the system.
     *
     * @see System#lineSeparator()
     */
    @Nonnull
    public static final String LR = System.lineSeparator();

    /**
     * The predicate to test an hexadecimal string.
     */
    @Nonnull
    private static final Predicate<String> IS_BINARY = Pattern.compile("^[0-9a-f]+$", Pattern.CASE_INSENSITIVE).asPredicate();

    private Strings() {
        throw Throwables.notInstantiableClass(getClass());
    }

    /**
     * Returns {@code true} if the given string is null or is the empty string.
     *
     * @param s a string reference to check
     *
     * @return {@code true} if the string is {@code null} or is the empty string
     */
    public static boolean isNullOrEmpty(@Nullable String s) {
        return isNull(s) || s.isEmpty();
    }

    /**
     * Returns the given string if it is non-{@code null}; the empty string otherwise.
     *
     * @param s the string to test and possibly return
     *
     * @return {@code s} itself if it is non-null; {@link #EMPTY} if it is {@code null}
     */
    @Nonnull
    public static String nullToEmpty(@Nullable String s) {
        return isNull(s) ? EMPTY : s;
    }

    /**
     * Returns the given string if it is nonempty; {@code null} otherwise.
     *
     * @param s the string to test and possibly return
     *
     * @return {@code s} itself if it is nonempty; {@code null} if it is empty or {@code null}
     */
    @CheckForNull
    public static String emptyToNull(@Nullable String s) {
        return isNullOrEmpty(s) ? null : s;
    }

    /**
     * Encodes a {@link String} to a {@code byte} array.
     *
     * @param value the value to encode
     *
     * @return a {@code byte} array
     *
     * @throws NullPointerException     if the {@code value} is {@code null}
     * @throws IllegalArgumentException if {@link StandardCharsets#UTF_8} is not a supported encoding
     */
    @Nonnull
    public static byte[] toBytes(final String value) {
        checkNotNull(value, "value");

        try {
            return value.getBytes(StandardCharsets.UTF_8);
        }
        catch (UnsupportedCharsetException e) {
            throw Throwables.shouldNeverHappen(e);
        }
    }

    /**
     * Encodes a {@link String} to a {@code byte} array, where each {@code byte} is represented as a two-digit unsigned
     * hexadecimal number in lower case.
     *
     * @param value the value to encode
     *
     * @return a {@code byte} array
     *
     * @throws NullPointerException     if the {@code value} is {@code null}
     * @throws IllegalArgumentException if the {@link String#length() length} of the {@code value} is not even.
     * @see Bytes#toStringBinary(byte[])
     */
    @Nonnull
    public static byte[] toBytesBinary(final String value) {
        checkNotNull(value, "value");
        checkEqualTo(value.length() % 2, 0, "value.length (%d) must be pair", value.length());

        byte[] bytes = new byte[value.length() / 2];

        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) ((toHexDigit(value.charAt(i * 2)) << 4) + toHexDigit(value.charAt(i * 2 + 1)));
        }

        return bytes;
    }

    /**
     * Retrieves the digit of the given hexadecimal {@code char}.
     *
     * @param c the character to look for
     *
     * @return the digit
     *
     * @throws IllegalArgumentException if the {@code char} is not an hex digit
     */
    @Nonnegative
    private static int toHexDigit(char c) {
        if (c >= '0' && c <= '9') {
            return c - '0';
        }

        char lower = Character.toLowerCase(c);
        if (lower >= 'a' && lower <= 'f') {
            return lower - 'a' + 10;
        }

        throw new IllegalArgumentException(String.format("Unexpected hex digit: %c", c));
    }

    /**
     * Returns {@code true} if the {@code value} represents an hexadecimal value.
     *
     * @param value the string to test
     *
     * @return {@code true} if the {@code value} represents an hexadecimal value
     */
    public static boolean isBinary(final String value) {
        return !isNullOrEmpty(value) && IS_BINARY.test(value);
    }
}
