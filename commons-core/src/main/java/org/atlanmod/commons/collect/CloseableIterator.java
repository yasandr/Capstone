/*
 * Copyright (c) 2017 Atlanmod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package org.atlanmod.commons.collect;

import java.io.Closeable;
import java.util.Iterator;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * A closeable {@link Iterator} that allows implementations to clean up any resources they need to keep open to iterate
 * over elements.
 *
 * @param <E> the type of elements returned by this iterator
 */
@ParametersAreNonnullByDefault
public interface CloseableIterator<E> extends Iterator<E>, Closeable {

    @Override
    void close();
}
