package com.yahoo.gondola.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by patc on 11/16/15.
 */
public class Utils {
    final static Logger logger = LoggerFactory.getLogger(Utils.class);

    /**
     * First interrupts all the threads in the specified list and then waits until they have terminated.
     * If any have not terminated after a short delay (1000 ms), false is returned.
     */
    public static boolean stopThreads(List<Thread> threads) {
        boolean status = true;
        threads.forEach(t -> t.interrupt());
        for (Thread t : threads) {
            status = joinThread(t) && status;
        }
        return status;
    }

    public static boolean joinThread(Thread t) {
        boolean status = true;
        try {
            t.join(10000);
            if (t.isAlive()) {
                logger.error("Failed to stop thread " + t.getName());
                status = false;
            }
        } catch (InterruptedException e) {
            logger.error("Join thread " + t.getName() + " interrupted", e);
            status = false;
        }
        return status;
    }
}
