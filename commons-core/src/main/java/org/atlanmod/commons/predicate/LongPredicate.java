/*
 * Copyright (c) 2021 Naomod.
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
public class LongPredicate extends Predicate {

    private static final String PATTERN = "\nExpecting value (%d) to be %s (%d)";
    private final long value;

    public LongPredicate(PredicateContext context, long value) {
        super(context);
        this.value = value;
    }

    public LongPredicate isZero() {
        if (value != 0) {
            context.send(PATTERN, value, "exactly", 0);
        }
        return this;
    }

    public LongPredicate isGreaterThan(long other) {
        if (value <= other) {
            context.send(PATTERN, value, "greater than", other);
        }
        return this;
    }

    public LongPredicate isGreaterThanOrEqualTo(long other) {
        if (value < other) {
            context.send(PATTERN, value, "greater than or equal to", other);
        }
        return this;
    }

    public LongPredicate isLessThan(long other) {
        if (value >= other) {
            context.send(PATTERN, value, "less than", other);
        }
        return this;
    }

    public LongPredicate isLessThanOrEqualTo(long other) {
        if (value > other) {
            context.send(PATTERN, value, "less than or equal to", other);
        }
        return this;
    }

    public LongPredicate isBetween(long first, long last) {
        if ((value < first || value > last)) {
            context.send(PATTERN + " and (%d)", value, "between", first, last);
        }
        return this;
    }

    public LongPredicate isEqualTo(long other) {
        if (value != other) {
            context.send(PATTERN, value, "equal to", other);
        }
        return this;
    }

    public LongPredicate isDifferentFrom(long other) {
        if (value == other) {
            context.send(PATTERN, value, "different from", other);
        }
        return this;
    }

}
