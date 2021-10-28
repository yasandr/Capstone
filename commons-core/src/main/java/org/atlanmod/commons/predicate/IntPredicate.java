/*
 * Copyright (c) 2021 Atlanmod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package org.atlanmod.commons.predicate;

/**
 * @author sunye
 * @since 1.1.0
 */
public class IntPredicate extends Predicate {

    private static final String PATTERN = "\nExpecting value (%d) to be %s (%d)";
    private final int value;

    public IntPredicate(PredicateContext context, int value) {
        super(context);
        this.value = value;
    }

    public IntPredicate isZero() {
        if (value != 0) {
            context.send(PATTERN, value, "exactly", 0);
        }
        return this;
    }

    public IntPredicate isGreaterThan(int other) {
        if (value <= other) {
            context.send(PATTERN, value, "greater than", other);
        }
        return this;
    }

    public IntPredicate isGreaterThanOrEqualTo(int other) {
        if (value < other) {
            context.send(PATTERN, value, "greater than or equal to", other);
        }
        return this;
    }

    public IntPredicate isLessThan(int other) {
        if (value >= other) {
            context.send(PATTERN, value, "less than", other);
        }
        return this;
    }

    public IntPredicate isLessThanOrEqualTo(int other) {
        if (value > other) {
            context.send(PATTERN, value, "less than or equal to", other);
        }
        return this;
    }

    public IntPredicate isBetween(int first, int last) {
        if ((value < first || value > last)) {
            context.send(PATTERN + " and (%d)", value, "between", first, last);
        }
        return this;
    }

    public IntPredicate isEqualTo(int other) {
        if (value != other) {
            context.send(PATTERN, value, "equal to", other);
        }
        return this;
    }

    public IntPredicate isDifferentFrom(int other) {
        if (value == other) {
            context.send(PATTERN, value, "different from", other);
        }
        return this;
    }

}
