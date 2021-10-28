/*
 * Copyright (c) 2017 Atlanmod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package org.atlanmod.commons.io.serializer;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * An object that is responsible of {@link Object} to {@link String} encoding and decoding.
 *
 * @param <T> the type of (de)serialized objects
 */
@ParametersAreNonnullByDefault
public interface StringSerializer<T> extends Serializer<T, String> {
}
