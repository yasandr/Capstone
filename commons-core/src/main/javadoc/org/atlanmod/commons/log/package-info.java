/*
 * Copyright (c) 2017 Atlanmod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

/**
 * Provides classes for logging information and events.
 * <p>
 * This package contains the classes related to logging service. The {@link org.atlanmod.commons.log.Log} class
 * defines a set of static methods to log information, warnings, or errors. It is possible to use {@link
 * java.text.MessageFormat} style in logged information.
 * <p>
 * Client code can use the logger to log application-level information if needed using the following code:
 * <pre>{@code
 * Log.trace("a trace message");
 * Log.debug("a debug message");
 * Log.info("an information message");
 * Log.warn("a warning message");
 * Log.error("an error message");
 * }</pre>
 */

package org.atlanmod.commons.log;