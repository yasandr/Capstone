/*
 * Copyright (c) 2017 Atlanmod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package org.atlanmod.commons.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Annotates a program element that exists but is still being experimented. These elements may be incomplete, and are
 * subject to incompatible changes, or even removal, in a future release.
 * <p>
 * <b>Note:</b> It is advisable not to call these elements, at this time.
 */
@Retention(SOURCE)
@Target({TYPE, METHOD})
public @interface Beta {
}
