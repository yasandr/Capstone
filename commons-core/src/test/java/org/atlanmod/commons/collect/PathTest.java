package org.atlanmod.commons.collect;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class PathTest {

    @Test
    void testInstanceCreation() {
        Path<String> path_address = Path.of("FOO", "F", "ISI", "ARPA", "ARPA");
        Path<Integer> path_integers = Path.of(1, 2, 3, 4, 5);

        assertThat(path_address).isNotNull();
        assertThat(path_integers).isNotNull();
    }

    @Test
    void testEquals() {
        Path<String> atlanmod = Path.of("org", "atlanmod", "commons", "collect");
        Path<String> atlanmod_other = Path.of("org", "atlanmod", "commons", "collect");

        assertThat(atlanmod).isEqualTo(atlanmod_other);
    }

    @Test
    void testSize() {
        Path<String> address = Path.of("FOO", "F", "ISI", "ARPA", "ARPA");
        Path<String> single_address = Path.of("FOO");

        assertThat(address.size()).isEqualTo(5);
        assertThat(single_address.size()).isEqualTo(1);
    }

    @Test
    void testFirst() {
        Path<String> address = Path.of("FOO", "F", "ISI", "ARPA", "ARPA");

        assertThat(address.head()).isEqualTo("FOO");
    }

    @Test
    void testLast() {
        Path<String> address = Path.of("FOO", "F", "ISI", "ARPA", "ARPA");

        assertThat(address.last()).isEqualTo("ARPA");
    }

    @Test
    void testHashCode() {
        Path<String> address = Path.of("FOO", "F", "ISI", "ARPA", "ARPA");
        Path<String> inverted = Path.of("ARPA", "ARPA", "ISI", "F", "FOO");


        assertThat(address.hashCode()).isNotEqualTo(inverted.hashCode());
    }

    @Test
    void testToString() {
        Path<String> address = Path.of("FOO", "F", "ISI", "ARPA", "ARPA");

        assertThat(address.toString()).contains("FOO")
                .contains("F")
                .contains("ISI")
                .contains("ARPA");
    }

    @Test
    void testTail() {
        Path<String> address = Path.of("FOO", "F", "ISI", "ARPA", "ARPA");
        Path<String> expected = Path.of("F", "ISI", "ARPA", "ARPA");

        assertThat(address.tail()).isEqualTo(expected);
    }
}