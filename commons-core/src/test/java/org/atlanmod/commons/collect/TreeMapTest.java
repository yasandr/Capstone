package org.atlanmod.commons.collect;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class TreeMapTest {
    private TreeMap<String, Integer> tree;

    @BeforeEach
    void init() {
        tree = new TreeMap<>();
        tree.put(Path.of("a", "b", "c"), 1);
        tree.put(Path.of("a", "b"), 2);
        tree.put(Path.of("a"), 3);
        tree.put(Path.of("b", "c", "d"), 4);
    }


    @Test
    void testPut() {

        assertThat(tree.get(Path.of("a", "b", "c"))).isEqualTo(Optional.of(1));
    }

    @Test
    void testToString() {
        String actual = tree.toString();

        assertThat(actual)
                .contains("a")
                .contains("b")
                .contains("c");
    }
}