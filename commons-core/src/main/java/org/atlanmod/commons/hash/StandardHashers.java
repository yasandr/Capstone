/*
 * Copyright (c) 2017 Atlanmod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package org.atlanmod.commons.hash;

import net.openhft.hashing.LongHashFunction;

import org.atlanmod.commons.Throwables;
import org.atlanmod.commons.annotation.Static;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Constants definitions for the default {@link Hasher} instances.
 */
@Static
@ParametersAreNonnullByDefault
public final class StandardHashers {

    /**
     * A {@link Hasher} that uses the {@code MD5} algorithm (128 bits).
     */
    @Nonnull
    public static final Hasher MD5 = new NativeHasher("MD5");

    /**
     * A {@link Hasher} that uses the {@code SHA-1} algorithm (160 bits).
     */
    @Nonnull
    public static final Hasher SHA1 = new NativeHasher("SHA-1");

    /**
     * A {@link Hasher} that uses the {@code SHA-256} algorithm (256 bits).
     */
    @Nonnull
    public static final Hasher SHA256 = new NativeHasher("SHA-256");

    /**
     * A {@link Hasher} that uses the {@code MurmurHash3 64-bit} algorithm (64 bits).
     *
     * @see <a href="https://github.com/aappleby/smhasher/blob/master/src/MurmurHash3.cpp">aappleby/smhasher/../MurmurHash3.cpp</a>
     */
    @Nonnull
    public static final Hasher MURMUR3 = new ZeroAllocationHasher(LongHashFunction.murmur_3());

    /**
     * A {@link Hasher} that uses the {@code xxHash} algorithm (64 bits).
     *
     * @see <a href="https://github.com/Cyan4973/xxHash">Cyan4973/xxHash</a>
     */
    @Nonnull
    public static final Hasher XX = new ZeroAllocationHasher(LongHashFunction.xx());

    /**
     * A {@link Hasher} that uses the {@code CityHash} algorithm (64 bits).
     *
     * @see <a href="https://github.com/google/cityhash">google/cityhash</a>
     */
    @Nonnull
    public static final Hasher CITY = new ZeroAllocationHasher(LongHashFunction.city_1_1());

    /**
     * A {@link Hasher} that uses the {@code FarmHash NA} algorithm (64 bits).
     *
     * @see <a href="https://github.com/google/farmhash">google/farmhash</a>
     */
    @Nonnull
    public static final Hasher FARM_NA = new ZeroAllocationHasher(LongHashFunction.farmNa());

    /**
     * A {@link Hasher} that uses the {@code FarmHash UO} algorithm (64 bits).
     *
     * @see <a href="https://github.com/google/farmhash">google/farmhash</a>
     */
    @Nonnull
    public static final Hasher FARM_UO = new ZeroAllocationHasher(LongHashFunction.farmUo());

    private StandardHashers() {
        throw Throwables.notInstantiableClass(getClass());
    }

    /**
     * Returns a {@link Hasher} with the specified {@code name}.
     *
     * @param name the name of the hasher
     *
     * @return the {@link Hasher}
     */
    @Nonnull
    public static Hasher forName(String name) {
        try {
            return (Hasher) StandardHashers.class.getDeclaredField(name).get(null);
        }
        catch (IllegalAccessException | NoSuchFieldException e) {
            final String possibleValues = Arrays.stream(StandardHashers.class.getDeclaredFields())
                    .filter(f -> f.getType().isAssignableFrom(Hasher.class))
                    .filter(f -> Modifier.isStatic(f.getModifiers()))
                    .map(Field::getName)
                    .collect(Collectors.joining(" | "));

            throw new IllegalArgumentException(String.format("Unable to retrieve a Hasher with name '%s'. Possible values: %s", name, possibleValues), e);
        }
    }
}
