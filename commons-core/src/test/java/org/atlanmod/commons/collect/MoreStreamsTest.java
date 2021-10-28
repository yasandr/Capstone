/*
 * Copyright (c) 2017 Atlanmod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package org.atlanmod.commons.collect;

import org.atlanmod.commons.AbstractTest;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.annotation.ParametersAreNonnullByDefault;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * A test-case that checks the behavior of {@link MoreStreams}.
 */
@ParametersAreNonnullByDefault
class MoreStreamsTest extends AbstractTest {

    @Test
    void indexOf() {
        List<Integer> list0 = Arrays.asList(1, 3, 2, 3, 1, 2, 1);

        assertThat(MoreStreams.indexOf(list0.stream(), 1)).isNotEmpty().contains(0);
        assertThat(MoreStreams.indexOf(list0.stream(), 2)).isNotEmpty().contains(2);
        assertThat(MoreStreams.indexOf(list0.stream(), 3)).isNotEmpty().contains(1);
        assertThat(MoreStreams.indexOf(list0.stream(), 0)).isEmpty();

        List<Integer> list1 = Collections.emptyList();
        assertThat(MoreStreams.indexOf(list1.stream(), 0)).isEmpty();
    }

    @Test
    void lastIndexOf() {
        List<Integer> list0 = Arrays.asList(1, 3, 2, 3, 1, 2, 1);

        assertThat(MoreStreams.lastIndexOf(list0.stream(), 1)).isNotEmpty().contains(6);
        assertThat(MoreStreams.lastIndexOf(list0.stream(), 2)).isNotEmpty().contains(5);
        assertThat(MoreStreams.lastIndexOf(list0.stream(), 3)).isNotEmpty().contains(3);
        assertThat(MoreStreams.lastIndexOf(list0.stream(), 0)).isEmpty();

        List<Integer> list1 = Collections.emptyList();
        assertThat(MoreStreams.lastIndexOf(list1.stream(), 0)).isEmpty();
    }

    @Test
    void size() {
        List<Integer> list0 = Arrays.asList(1, 3, 2, 3, 1, 2, 1);
        assertThat(MoreStreams.size(list0.stream())).isNotEmpty().contains(7);

        List<Integer> list1 = Collections.emptyList();
        assertThat(MoreStreams.size(list1.stream())).isEmpty();
    }
}