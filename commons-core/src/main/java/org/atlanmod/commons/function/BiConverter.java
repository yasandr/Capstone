/*
 * Copyright (c) 2017 Atlanmod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package org.atlanmod.commons.function;

import java.util.function.BiFunction;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import static org.atlanmod.commons.Guards.checkNotNull;

/**
 * A specialized {@link BiFunction} that converts an object of type {@code T} to another of type {@code R}, by using an
 * object of type {@code U}.
 * <p>
 * The reverse operation <b>may</b> be strict <i>inverse</i>, meaning that {@code converter.revert(converter.convert(a,
 * c), c).equals(a)} always {@code true}.
 *
 * @param <T> the type of the first argument to the converter
 * @param <U> the type of the second argument to the converter
 * @param <R> the type of the result of the converter
 *
 * @see BiFunction
 */
@ParametersAreNonnullByDefault
public interface BiConverter<T, U, R> extends BiFunction<T, U, R> {

    /**
     * Creates a bi-converter that always converts or reverses an object to itself.
     *
     * @param <T> the type of the converted instance and the result
     *
     * @return a new converter
     */
    @Nonnull
    static <T, U> BiConverter<T, U, T> identity() {
        return new BiConverter<>() {
            @Nonnull
            @Override
            public T convert(T t, U u) {
                return t;
            }

            @Nonnull
            @Override
            public T revert(T t, U u) {
                return t;
            }
        };
    }

    /**
     * Creates a bi-converter based on separate forward and backward functions.
     *
     * @param convertFunc the function used for {@link #convert(Object, Object)}
     * @param revertFunc  the function used for {@link #revert(Object, Object)}
     * @param <T>         the type of the first argument to the converter
     * @param <U>         the type of the second argument to the converter
     * @param <R>         the type of the result of the converter
     *
     * @return a new converter
     */
    @Nonnull
    static <T, U, R> BiConverter<T, U, R> from(BiFunction<? super T, ? super U, ? extends R> convertFunc, BiFunction<? super R, ? super U, ? extends T> revertFunc) {
        checkNotNull(convertFunc, "convertFunc");
        checkNotNull(revertFunc, "revertFunc");

        return new BiConverter<>() {
            @Nonnull
            @Override
            public R convert(T t, U u) {
                return convertFunc.apply(t, u);
            }

            @Nonnull
            @Override
            public T revert(R r, U u) {
                return revertFunc.apply(r, u);
            }
        };
    }

    @Override
    default R apply(T t, U u) {
        return convert(t, u);
    }

    /**
     * Returns a representation of {@code t} as an instance of type {@code R}, by using an object of type {@code U}.
     *
     * @param t the instance to convert
     * @param u the helper instance
     *
     * @return the converted instance
     */
    R convert(T t, U u);

    /**
     * Returns a representation of {@code r} as an instance of type {@code T}, by using an object of type {@code U}.
     *
     * @param r the instance to convert
     * @param u the helper instance
     *
     * @return the converted instance
     */
    T revert(R r, U u);

    /**
     * Returns the reversed converter of this converter.
     *
     * @return the reversed converter
     */
    @Nonnull
    default BiConverter<R, U, T> reverse() {
        return from(this::revert, this::convert);
    }
}
