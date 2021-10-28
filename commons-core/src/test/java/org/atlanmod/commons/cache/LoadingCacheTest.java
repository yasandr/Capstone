/*
 * Copyright (c) 2017 Atlanmod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package org.atlanmod.commons.cache;

import org.atlanmod.commons.AbstractTest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.annotation.ParametersAreNonnullByDefault;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * A test-case that checks the behavior of {@link Cache} with a loading {@link java.util.function.Function}.
 */
@ParametersAreNonnullByDefault
class LoadingCacheTest extends AbstractTest {

    private Cache<Integer, String> cache;

    @BeforeEach
    void setUp() {
        cache = CacheBuilder.builder()
                .weakKeys()
                .softValues()
                .maximumSize(5)
                .recordStats()
                .build(key -> "Value" + key);

        assertThat(cache.size()).isEqualTo(0);
    }

    @AfterEach
    void tearDown() {
        cache.invalidateAll();
        cache.cleanUp();
    }

    @Test
    void testGetPut() {
        String value0 = "Value0";

        assertThat(cache.get(0)).isEqualTo(value0);
        assertThat(cache.size()).isEqualTo(1);
    }

    @Test
    void testGetWithFunction() {
        String prefix = "Value";
        String value0 = prefix + '0';

        assertThat(cache.get(0)).isEqualTo(value0);
        assertThat(cache.size()).isEqualTo(1);
    }

    @Test
    void testGetWithNullFunction() {
        String value0 = "Value0";

        assertThat(cache.get(0, key -> null)).isNull();

        cache.refresh(0);

        assertThat(cache.get(0)).isEqualTo(value0);
    }

    @Test
    void testGetPutAllKeys() {
        String value0 = "Value0";
        String value1 = "Value1";
        String value2 = "Value2";

        assertThat(cache.size()).isEqualTo(0);

        assertThat(cache.get(0)).isEqualTo(value0);
        assertThat(cache.get(1)).isEqualTo(value1);
        assertThat(cache.get(2)).isEqualTo(value2);

        assertThat(cache.size()).isEqualTo(3);

        Map<Integer, String> result = cache.getAll(IntStream.of(0, 2).boxed().collect(Collectors.toList()));

        assertThat(result.get(0)).isEqualTo(value0);
        assertThat(result.get(2)).isEqualTo(value2);
    }

    @Test
    void testInvalidate() {
        String value0 = "Value0";
        String value1 = "Value1";

        assertThat(cache.get(0)).isEqualTo(value0);
        assertThat(cache.get(1)).isEqualTo(value1);

        assertThat(cache.size()).isEqualTo(2);

        cache.invalidate(0);

        assertThat(cache.get(1)).isEqualTo(value1);

        assertThat(cache.size()).isEqualTo(1);
    }

    @Test
    void testInvalidateAllKeys() {
        String value0 = "Value0";
        String value1 = "Value1";
        String value2 = "Value2";

        assertThat(cache.get(0)).isEqualTo(value0);
        assertThat(cache.get(1)).isEqualTo(value1);
        assertThat(cache.get(2)).isEqualTo(value2);

        assertThat(cache.size()).isEqualTo(3);

        cache.invalidateAll(IntStream.of(0, 2).boxed().collect(Collectors.toList()));

        assertThat(cache.size()).isEqualTo(1);

        assertThat(cache.get(1)).isEqualTo(value1);
    }

    @Test
    void testInvalidateAll() {
        String value0 = "Value0";
        String value1 = "Value1";

        assertThat(cache.get(0)).isEqualTo(value0);
        assertThat(cache.get(1)).isEqualTo(value1);

        cache.invalidateAll();

        assertThat(cache.size()).isEqualTo(0);
    }

    @Test
    void asMap() throws Exception {
        String value0 = "Value0";
        String value1 = "Value1";
        String value2 = "Value2";

        Map<Integer, String> original = new HashMap<>();
        original.put(0, value0);
        original.put(1, value1);
        original.put(2, value2);

        cache.get(0);
        cache.get(1);
        cache.get(2);

        Map<Integer, String> result = cache.asMap();

        original.forEach((key, value) -> assertThat(result.get(key)).isEqualTo(value));
    }

    @Test
    void stats() throws Exception {
        CacheStats stats = cache.stats();
        assertThat(stats).isNotNull();
    }
}