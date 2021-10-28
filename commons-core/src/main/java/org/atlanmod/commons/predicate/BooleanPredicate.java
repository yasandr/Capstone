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
public class BooleanPredicate extends Predicate {

    private final boolean expression;
    private final String PATTERN = "\nExpecting value to be (%b) but was (%b)";

    public BooleanPredicate(PredicateContext context, boolean expression) {
        super(context);
        this.expression = expression;
    }

    public BooleanPredicate isTrue() {
        if ( !expression) {
            context.send(PATTERN, true, false);
        }
        return this;
    }

    public BooleanPredicate isFalse() {
        if (expression) {
            context.send(PATTERN, false, true);
        }
        return this;
    }
}
