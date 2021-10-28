/*
 * Copyright (c) 2021 Atlanmod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */
package org.atlanmod.commons.collect;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TreeTest {

    private Tree<String> pathTree;

    @BeforeEach
    void init() {
        pathTree = new Tree<>();
        String[] paths = {
                "org.atlanmod.commons.Throwable",
                "org.atlanmod.commons.annotation.Static",
                "java.util.Objects",
                "java.util.Optional",
                "java.util.concurrent.atomic.AtomicInteger",
                "java.util.function.IntUnaryOperator",
                "java.util.function.LongUnaryOperator",
                "java.util.stream.Stream",
                "javax.annotation.Nonnull",
                "javax.annotation.ParametersAreNonnullByDefault",
                "fr.inria.old.stuff.Core"};
        for (String each : paths) {
            pathTree.add(each.split(Pattern.quote(".")));
        }
    }

    @Test
    void isEmpty() {
        Tree<String> empty = new Tree<>();

        assertThat(empty.isEmpty()).isTrue();
    }

    @Test
    void notEmpty() {
        Tree<String> tree = new Tree<>();
        tree.add("a");

        assertThat(tree.isEmpty()).isFalse();
    }

    @Test
    void testLeaves() {
        List<String> expected = List.of("Throwable", "Static", "Objects", "Optional", "AtomicInteger",
                "IntUnaryOperator", "LongUnaryOperator", "Stream", "Nonnull", "ParametersAreNonnullByDefault",
                "Core");

        assertThat(pathTree.leaves()).isEqualTo(expected);
    }

    @Test
    void testIterator() {
        Tree<String> tree = new Tree<>();
        tree.add("aaa", "bbb", "ccc");
        tree.add("aaa", "bbb", "ddd");
        tree.add("eee", "fff");
        tree.add("ggg");

        List<String> expected = List.of("aaa", "eee", "ggg", "bbb", "fff", "ccc", "ddd");
        List<String> actual = new LinkedList<>();
        for (String each: tree) {
            actual.add(each);
        }

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void emptyIteratorShouldRaiseException() {
        Tree<Integer> empty = new Tree<>();
        Iterator<Integer> it = empty.iterator();

        assertThatThrownBy(it::next).isInstanceOf(NoSuchElementException.class);
    }
}