/*
 * Copyright (c) 2021 Atlanmod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package org.atlanmod.commons.predicate;

import java.util.Objects;

/**
 *
 * @author sunye
 * @since 1.1.0
 */
public class ObjectPredicate<Yourself extends ObjectPredicate, T> extends Predicate {
    private static final String PATTERN = "\nExpecting value (%s) to be %s (%s)";

    final T value;
    Yourself y;

    public ObjectPredicate(PredicateContext context, T value) {
        super(context);
        this.value = value;
    }

    public Yourself isNull() {
        if (!Objects.isNull(value)) {
            context.send(PATTERN,value, "null", "");
        }
        return me();
    }

    public Yourself isNotNull() {
        if (Objects.isNull(value)) {
            context.send(PATTERN, value, "non null", "");
        }
        return me();
    }

    public Yourself isEqualTo(T other) {
        if(!value.equals(other)) {
            context.send(PATTERN, value, "equal to", other);
        }
        return me();
    }

    public Yourself isDifferentFrom(T other) {
        if(value.equals(other)) {
            context.send(PATTERN, value, "different from", other);
        }
        return me();
    }

    public Yourself me() {
        return (Yourself) this;
    }
}
