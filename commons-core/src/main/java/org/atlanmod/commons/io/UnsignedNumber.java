/*
 * Copyright (c) 2021 Naomod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

/**
 *
 *  @author sunye
 *  @since 1.1.0
 */
package org.atlanmod.commons.io;

import java.nio.ByteBuffer;

public abstract class UnsignedNumber extends Number {

    public abstract byte[] toBytes();

    public void writeOn(ByteBuffer buffer) {
        buffer.put(toBytes());
    }
}
