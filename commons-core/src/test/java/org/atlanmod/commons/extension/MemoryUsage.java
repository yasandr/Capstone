/*
 * Copyright (c) 2017 Atlanmod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package org.atlanmod.commons.extension;

import org.atlanmod.commons.primitive.Strings;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
final class MemoryUsage {

    /**
     * The total amount of memory in the Java virtual machine, in bytes.
     */
    @Nonnegative
    private final long total;

    /**
     * The amount of free memory in the Java Virtual Machine, in bytes.
     */
    @Nonnegative
    private final long free;

    /**
     * Constructs a new {@code MemoryUsage}.
     */
    private MemoryUsage(Runtime runtime) {
        this.total = runtime.totalMemory();
        this.free = runtime.freeMemory();
    }

    /**
     * Creates the current memory usage from the default {@link Runtime}.
     *
     * @return a new memory usage
     */
    @Nonnull
    public static MemoryUsage now() {
        return new MemoryUsage(Runtime.getRuntime());
    }

    /**
     * Converts the {@code bytes} value to another unit (kilobytes, megabytes,...).
     */
    private static long convertUnit(long bytes) {
        return bytes / 1024 / 1024;
    }

    /**
     * Returns the total amount of memory in the Java virtual machine, in bytes.
     */
    @Nonnegative
    public long total() {
        return total;
    }

    /**
     * Returns the amount of free memory in the Java Virtual Machine, in bytes.
     */
    @Nonnegative
    public long free() {
        return free;
    }

    /**
     * Returns the amount of used memory in the Java Virtual Machine, in bytes.
     */
    @Nonnegative
    public long used() {
        return total() - free();
    }

    /**
     * A difference between two instances of {@link MemoryUsage}.
     */
    @ParametersAreNonnullByDefault
    public static final class Diff {

        @Nonnull
        private final MemoryUsage start;

        @Nonnull
        private final MemoryUsage end;

        /**
         * Creates a new {@code Diff}.
         */
        private Diff(MemoryUsage start, MemoryUsage end) {
            this.start = start;
            this.end = end;
        }

        /**
         * Creates a {@code Diff} reprensenting the difference between two memory usages.
         */
        @Nonnull
        public static Diff between(MemoryUsage start, MemoryUsage end) {
            return new Diff(start, end);
        }

        /**
         * Returns the difference of the {@link MemoryUsage#total()}, in bytes.
         */
        public long total() {
            return end.total() - start.total();
        }

        /**
         * Returns the difference of the {@link MemoryUsage#free()}, in bytes.
         */
        public long free() {
            return end.free() - start.free();
        }

        /**
         * Returns the difference of the {@link MemoryUsage#used()}, in bytes.
         */
        public long used() {
            return end.used() - start.used();
        }

        @Override
        public String toString() {
            final long usedDiff = convertUnit(used());
            final long totalDiff = convertUnit(total());

            return String.format("%s / %s%s MB",
                    usedDiff < 0 ? "<0" : usedDiff,
                    convertUnit(end.total()),
                    totalDiff == 0 ? Strings.EMPTY : ("(" + (totalDiff > 0 ? "+" + totalDiff : totalDiff) + ")"));
        }
    }
}
