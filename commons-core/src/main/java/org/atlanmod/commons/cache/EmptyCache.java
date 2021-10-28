/*
 * Copyright (c) 2017 Atlanmod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package org.atlanmod.commons.cache;

import org.atlanmod.commons.annotation.Singleton;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.Immutable;

/**
 * An immutable empty {@link Cache} that does nothing.
 *
 * @param <K> the type of keys maintained by this cache
 * @param <V> the type of mapped values
 */
@Immutable
@Singleton
@ParametersAreNonnullByDefault
class EmptyCache<K, V> implements Cache<K, V> {

    /**
     * The instance of the outer class.
     */
    private static final Cache<Object, Object> INSTANCE = new EmptyCache<>();

    /**
     * Returns the instance of this class.
     *
     * @return the instance of this class
     */
    @Nonnull
    @SuppressWarnings("unchecked")
    public static <K, V> Cache<K, V> getInstance() {
        return (Cache<K, V>) INSTANCE;
    }

    @Override
    public V get(K key) {
        return null;
    }

    @Override
    public V get(K key, Function<? super K, ? extends V> mappingFunction) {
        return mappingFunction.apply(key);
    }

    @Nonnull
    @Override
    public Map<K, V> getAll(Iterable<? extends K> keys) {
        return Collections.emptyMap();
    }

    @Override
    public void put(K key, V value) {
        // Do nothing
    }

    @Override
    public void putIfAbsent(K key, V value) {
        // Do nothing
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> map) {
        // Do nothing
    }

    @Override
    public void invalidate(K key) {
        // Do nothing
    }

    @Override
    public void invalidateAll(Iterable<? extends K> keys) {
        // Do nothing
    }

    @Override
    public void invalidateAll() {
        // Do nothing
    }

    @Override
    public boolean contains(K key) {
        return false;
    }

    @Nonnegative
    @Override
    public long size() {
        return 0;
    }

    @Override
    public void refresh(K key) {
        // Do nothing
    }

    @Override
    public void cleanUp() {
        // Do nothing
    }

    @Nonnull
    @Override
    public ConcurrentMap<K, V> asMap() {
        return new ConcurrentHashMap<>(0);
    }

    @Nonnull
    @Override
    public CacheStats stats() {
        return new CacheStats(0, 0, 0, 0, 0, 0);
    }
}
