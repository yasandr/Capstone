/*
 * Copyright (c) 2017 Atlanmod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package org.atlanmod.commons.concurrent;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.GuardedBy;

/**
 * A {@link ExecutorService} that runs each task in the thread that invokes {@code execute/submit}. This applies both to
 * individually submitted tasks and to collections of tasks submitted via {@code invokeAll} or {@code invokeAny}. In the
 * latter case, tasks will run serially on the calling thread. Tasks are run to completion before a {@code Future} is
 * returned to the caller (unless the executor has been shutdown).
 * <p>
 * Although all tasks are immediately executed in the thread that submitted the task, this {@code ExecutorService}
 * imposes a small locking overhead on each task submission in order to implement shutdown and termination behavior.
 * <p>
 * Class adapted from Guava 23.0
 */
@ParametersAreNonnullByDefault
class DirectExecutorService extends AbstractExecutorService {

    /**
     * Lock used whenever accessing the state variables (runningTasks, shutdown) of the executor.
     */
    @Nonnull
    private final Object lock = new Object();

    /**
     * Conceptually, these two variables describe the executor being in one of three states:
     * <ul>
     * <li>Active: shutdown == {@code false}</li>
     * <li>Shutdown: runningTasks > 0 and shutdown == {@code true}</li>
     * <li>Terminated: runningTasks == 0 and shutdown == {@code true}</li>
     * </ul>
     */
    @GuardedBy("lock")
    private int runningTasks = 0;

    @GuardedBy("lock")
    private boolean shutdown = false;

    @Override
    public void execute(Runnable command) {
        startTask();
        try {
            command.run();
        }
        finally {
            endTask();
        }
    }

    @Override
    public void shutdown() {
        synchronized (lock) {
            shutdown = true;
            if (runningTasks == 0) {
                lock.notifyAll();
            }
        }
    }

    @Nonnull
    @Override
    public List<Runnable> shutdownNow() {
        shutdown();
        return Collections.emptyList();
    }

    @Override
    public boolean isShutdown() {
        synchronized (lock) {
            return shutdown;
        }
    }

    @Override
    public boolean isTerminated() {
        synchronized (lock) {
            return shutdown && runningTasks == 0;
        }
    }

    @Override
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        long nanos = unit.toNanos(timeout);
        synchronized (lock) {
            while (true) {
                if (shutdown && runningTasks == 0) {
                    return true;
                }
                else if (nanos <= 0) {
                    return false;
                }
                else {
                    long now = System.nanoTime();
                    TimeUnit.NANOSECONDS.timedWait(lock, nanos);
                    nanos -= System.nanoTime() - now; // subtract the actual time we waited
                }
            }
        }
    }

    /**
     * Checks if the executor has been shut down and increments the running task count.
     *
     * @throws RejectedExecutionException if the executor has been previously shutdown
     */
    private void startTask() {
        synchronized (lock) {
            if (shutdown) {
                throw new RejectedExecutionException("Executor already shutdown");
            }
            runningTasks++;
        }
    }

    /**
     * Decrements the running task count.
     */
    private void endTask() {
        synchronized (lock) {
            int numRunning = --runningTasks;
            if (numRunning == 0) {
                lock.notifyAll();
            }
        }
    }
}
