package org.atlanmod.commons.mutable;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MutableIntegerTest {

    @Test
    void set() {
        int expected = 99;
        MutableInteger value = new MutableInteger(44);
        value.set(expected);

        assertThat(value.get()).isEqualTo(expected);
    }

    @Test
    void get() {
        int expected = 99;
        MutableInteger value = new MutableInteger(expected);

        assertThat(value.get()).isEqualTo(expected);
    }

    @Test
    void getValue() {
        Integer expected = 99;
        MutableInteger value = new MutableInteger(expected);

        assertThat(value.getValue()).isEqualTo(expected);
    }

    @Test
    void setValue() {
        Integer expected = 99;
        MutableInteger value = new MutableInteger(42);
        value.setValue(expected);

        assertThat(value.getValue()).isEqualTo(expected);
    }

    @Test
    void compareTo() {
        MutableInteger int40 = new MutableInteger(40);
        MutableInteger int50 = new MutableInteger(50);

        assertThat(int50).isGreaterThan(int40);
        assertThat(int40).isLessThan(int50);
        assertThat(int40).isEqualByComparingTo(new MutableInteger(40));
    }

    @Test
    void intValue() {
        int expected = 99;
        MutableInteger value = new MutableInteger(expected);

        assertThat(value.intValue()).isEqualTo(expected);
    }

    @Test
    void longValue() {
        long expected = 99L;
        MutableInteger value = new MutableInteger(99);

        assertThat(value.longValue()).isEqualTo(expected);
    }

    @Test
    void floatValue() {
        int expected = 99;
        MutableInteger value = new MutableInteger(expected);

        assertThat(value.floatValue()).isEqualTo((float) expected);
    }

    @Test
    void doubleValue() {
        double expected = 99.0;
        MutableInteger value = new MutableInteger(99);

        assertThat(value.doubleValue()).isEqualTo(expected);
    }

    @Test
    void incrementAndGet() {
        MutableInteger value = new MutableInteger(0);

        assertThat(value.incrementAndGet()).isEqualTo(1);
    }

    @Test
    void getAndIncrement() {
        int expected = 99;
        MutableInteger value = new MutableInteger(expected);

        assertThat(value.getAndIncrement()).isEqualTo(expected);
        assertThat(value).isEqualTo(new MutableInteger(++expected));

    }

    @Test
    void increment() {
        int expected = 99;
        MutableInteger value = new MutableInteger(expected);
        value.increment();

        assertThat(value).isEqualTo(new MutableInteger(++expected));
    }

    @Test
    void decrementAndGet() {
        MutableInteger value = new MutableInteger(0);

        assertThat(value.decrementAndGet()).isEqualTo(-1);
    }

    @Test
    void getAndDecrement() {
        int expected = 99;
        MutableInteger value = new MutableInteger(expected);

        assertThat(value.getAndDecrement()).isEqualTo(expected);
        assertThat(value).isEqualTo(new MutableInteger(--expected));

    }

    @Test
    void decrement() {
        int expected = 99;
        MutableInteger value = new MutableInteger(expected);
        value.decrement();

        assertThat(value).isEqualTo(new MutableInteger(--expected));
    }
}