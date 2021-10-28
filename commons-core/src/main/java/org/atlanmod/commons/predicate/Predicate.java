/*
 * Copyright (c) 2021 Atlanmod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package org.atlanmod.commons.predicate;

/**
 *
 * @author sunye
 * @since 1.1.0
 */
public class Predicate {
    final PredicateContext context;


    public Predicate(PredicateContext context) {
        this.context = context;
    }
}
