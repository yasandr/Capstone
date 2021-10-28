/*
 * Copyright (c) 2017 Atlanmod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package org.atlanmod.commons.io;

import org.atlanmod.commons.Throwables;
import org.atlanmod.commons.annotation.Static;
import org.atlanmod.commons.primitive.Strings;

import java.io.File;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import static org.atlanmod.commons.Guards.checkNotNull;

/**
 * Static utility methods related to {@link java.io.File}s.
 */
@Static
@ParametersAreNonnullByDefault
public final class MoreFiles {

    /**
     * The dot character.
     */
    private static final char DOT = '.';

    private MoreFiles() {
        throw Throwables.notInstantiableClass(getClass());
    }

    /**
     * Returns the file extension for the given {@code file}, or the empty string if the file has no extension. The
     * result does not include the '{@code .}'.
     *
     * @param file the file
     *
     * @return the file extension
     */
    @Nonnull
    public static String fileExtension(File file) {
        checkNotNull(file, "file");

        String fileName = file.getName();
        int dotIndex = fileName.lastIndexOf(DOT);
        return (dotIndex == -1) ? Strings.EMPTY : fileName.substring(dotIndex + 1);
    }

    /**
     * Returns the file extension for the given file name, or the empty string if the fullName has no extension. The
     * result does not include the '{@code .}'.
     *
     * @param fullName the file name
     *
     * @return the fullName extension
     */
    @Nonnull
    public static String fileExtension(String fullName) {
        checkNotNull(fullName, "fullName");

        return fileExtension(new File(fullName));
    }

    /**
     * Returns the {@code file} name without its file extension or path. This is similar to the {@code basename} unix
     * command. The result does not include the '{@code .}'.
     *
     * @param file the file
     *
     * @return the file name without its extension
     */
    @Nonnull
    public static String nameWithoutExtension(File file) {
        checkNotNull(file, "file");

        String fileName = file.getName();
        int dotIndex = fileName.lastIndexOf(DOT);
        return (dotIndex == -1) ? fileName : fileName.substring(0, dotIndex);
    }

    /**
     * Returns the file name without its fullName extension or path. This is similar to the {@code basename} unix
     * command. The result does not include the '{@code .}'.
     *
     * @param fullName the file name
     *
     * @return the fullName name without its extension
     */
    @Nonnull
    public static String nameWithoutExtension(String fullName) {
        checkNotNull(fullName, "fullName");

        return nameWithoutExtension(new File(fullName));
    }
}
