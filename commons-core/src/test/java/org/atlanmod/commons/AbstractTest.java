/*
 * Copyright (c) 2017 Atlanmod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package org.atlanmod.commons;

import org.atlanmod.commons.extension.LoggingWithMemoryExtension;
import org.atlanmod.commons.extension.TimeoutExtension;

import org.junit.jupiter.api.extension.ExtendWith;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * An abstract test-case that manages the logger.
 */
@ExtendWith(LoggingWithMemoryExtension.class)
@ExtendWith(TimeoutExtension.class)
@ParametersAreNonnullByDefault
public abstract class AbstractTest {
}
