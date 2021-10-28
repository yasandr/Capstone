/*
 * Copyright (c) 2017 Atlanmod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package org.atlanmod.commons.cache;

import org.atlanmod.commons.annotation.Builder;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.function.Function;
import java.util.function.ToIntBiFunction;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

/**
 * A builder of {@link Cache} instances.
 *
 * @param <K> the base key type for {@link Cache}s created by this builder
 * @param <V> the base value type for {@link Cache} created by this builder
 */
@Builder("builder")
public interface CacheBuilder<K, V> {

    /**
     * Returns an immutable empty {@link Cache} that does nothing.
     *
     * @param <K> the type of keys maintained by this cache
     * @param <V> the type of mapped values
     *
     * @return a new cache
     */
    @Nonnull
    static <K, V> Cache<K, V> empty() {
        return EmptyCache.getInstance();
    }

    /**
     * Creates a new {@code CacheBuilder} with default settings, including strong keys, strong values, and no automatic
     * eviction of any kind.
     *
     * @return a new builder
     */
    @Nonnull
    static CacheBuilder<Object, Object> builder() {
        return new CaffeineCacheBuilder<>();
    }

    /**
     * Enables the accumulation of {@link CacheStats} during the operation of the cache.
     * <p>
     * Without this {@link Cache#stats} will return zero for all statistics. Note that recording statistics requires
     * bookkeeping to be performed with each operation, and thus imposes a performance penalty on cache operation.
     *
     * @return this builder (for chaining)
     */
    @Nonnull
    CacheBuilder<K, V> recordStats();

    /**
     * Sets the minimum total size for the internal data structures.
     * <p>
     * Providing a large enough estimate at construction time avoids the need for expensive resizing operations later,
     * but setting this value unnecessarily high wastes memory.
     *
     * @param initialCapacity minimum total size for the internal data structures
     *
     * @return this builder instance
     *
     * @throws IllegalArgumentException if {@code initialCapacity} is negative
     * @throws IllegalStateException    if an initial capacity was already set
     */
    @Nonnull
    CacheBuilder<K, V> initialCapacity(@Nonnegative int initialCapacity);

    /**
     * Specifies the maximum number of entries the cache may contain.
     * <p>
     * Note that the cache <b>may evict an entry before this limit is exceeded or temporarily exceed the threshold while
     * evicting</b>. As the cache size grows close to the maximum, the cache evicts entries that are less likely to be
     * used again. For example, the cache may evict an entry because it hasn't been used recently or very often.
     * <p>
     * When {@code size} is zero, elements will be evicted immediately after being loaded into the cache. This can be
     * useful in testing, or to disable caching temporarily without a code change.
     * <p>
     * This feature cannot be used in conjunction with {@link #maximumWeight}.
     *
     * @param maximumSize the maximum size of the cache
     *
     * @return this builder (for chaining)
     *
     * @throws IllegalArgumentException if {@code maximumSize} is negative
     * @throws IllegalStateException    if a maximum size or weight was already set
     */
    @Nonnull
    CacheBuilder<K, V> maximumSize(@Nonnegative long maximumSize);

    /**
     * Specifies the maximum weight of entries the cache may contain.
     * <p>
     * Weight is determined using the {@link ToIntBiFunction} specified with {@code weigher}. Weights are measured and
     * recorded when entries are inserted into or updated in the cache, and are thus effectively static during the
     * lifetime of a cache entry.
     * <p>
     * Note that the cache <b>may evict an entry before this limit is exceeded or temporarily exceed the threshold while
     * evicting</b>. As the cache size grows close to the maximum, the cache evicts entries that are less likely to be
     * used again. For example, the cache may evict an entry because it hasn't been used recently or very often.
     * <p>
     * When {@code maximumWeight} is zero, elements will be evicted immediately after being loaded into cache. This can
     * be useful in testing, or to disable caching temporarily without a code change.
     * <p>
     * Note that weight is only used to determine whether the cache is over capacity; it has no effect on selecting
     * which entry should be evicted next.
     * <p>
     * This feature cannot be used in conjunction with {@link #maximumSize}.
     *
     * @param maximumWeight the maximum total weight of entries the cache may contain
     * @param weigher       the weigher to use in calculating the weight of cache entries
     *
     * @return this builder (for chaining)
     *
     * @throws IllegalArgumentException if {@code maximumWeight} is negative
     * @throws IllegalStateException    if a maximum weight or size was already set
     */
    <K1 extends K, V1 extends V> CacheBuilder<K, V> maximumWeight(@Nonnegative long maximumWeight, ToIntBiFunction<? super K1, ? extends V1> weigher);

    /**
     * Specifies that each key (not value) stored in the cache should be wrapped in a {@link WeakReference} (by default,
     * strong references are used).
     * <p>
     * <b>WARNING:</b> when this method is used, the resulting cache will use identity ({@code ==}) comparison to
     * determine equality of keys.
     *
     * @return this builder (for chaining)
     *
     * @throws IllegalStateException if the key strength was already set or the writer was set
     */
    @Nonnull
    CacheBuilder<K, V> weakKeys();

    /**
     * Specifies that each value (not key) stored in the cache should be wrapped in a {@link WeakReference} (by default,
     * strong references are used).
     * <p>
     * Weak values will be garbage collected once they are weakly reachable. This makes them a poor candidate for
     * caching; consider {@link #softValues} instead.
     * <p>
     * <b>WARNING:</b> when this method is used, the resulting cache will use identity ({@code ==}) comparison to
     * determine equality of values.
     * <p>
     * Entries with values that have been garbage collected may be counted in {@link Cache#size()}, but will never be
     * visible to read or write operations; such entries are cleaned up as part of the routine maintenance described in
     * the class javadoc.
     *
     * @return this builder instance
     *
     * @throws IllegalStateException if the value strength was already set
     */
    @Nonnull
    CacheBuilder<K, V> weakValues();

    /**
     * Specifies that each value (not key) stored in the cache should be wrapped in a {@link SoftReference} (by default,
     * strong references are used).
     * <p>
     * Softly-referenced objects will be garbage-collected in a <i>globally</i> least-recently-used manner, in response
     * to memory demand.
     * <p>
     * <b>WARNING:</b> when this method is used, the resulting cache will use identity ({@code ==}) comparison to
     * determine equality of values.
     *
     * @return this builder (for chaining)
     *
     * @throws IllegalStateException if the value strength was already set
     */
    @Nonnull
    CacheBuilder<K, V> softValues();

    /**
     * Builds a {@link Cache} which does not automatically load values when keys are requested.
     * <p>
     * Consider {@link #build(Function)} instead, if it is feasible to implement a {@code CacheLoader}.
     *
     * @param <K1> the key type of the cache
     * @param <V1> the value type of the cache
     *
     * @return a new cache
     */
    @Nonnull
    <K1 extends K, V1 extends V> Cache<K1, V1> build();

    /**
     * Builds a {@link Cache}, which either returns an already-loaded value for a given key or atomically computes or
     * retrieves it using the supplied {@link Function}. If another thread is currently loading the value for this key,
     * simply waits for that thread to finish and returns its loaded value. Note that multiple threads can concurrently
     * load values for distinct keys.
     *
     * @param mappingFunction the function used to obtain new values
     * @param <K1>            the key type of the loader
     * @param <V1>            the value type of the loader
     *
     * @return a new cache
     */
    @Nonnull
    <K1 extends K, V1 extends V> Cache<K1, V1> build(Function<? super K1, ? extends V1> mappingFunction);
}
