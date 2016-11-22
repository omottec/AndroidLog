package com.github.omottec.android.log;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * Created by qinbingbing on 11/22/16.
 */

public class LogQueue {
    private BlockingQueue<LogRecord> mLogs = new PriorityBlockingQueue<>();

    /** Number of log dispatcher threads to start. */
    private static final int DEFAULT_LOG_THREAD_POOL_SIZE = 4;

    /** The log dispatchers. */
    private LogDispatcher[] mDispatchers;

    private LogWriter mLogWriter;

    public LogQueue(LogWriter writer) {
        this(writer, DEFAULT_LOG_THREAD_POOL_SIZE);
    }

    public LogQueue(LogWriter writer, int threadPoolSize) {
        mLogWriter = writer;
        mDispatchers = new LogDispatcher[threadPoolSize];
    }

    /**
     * Stops the log dispatchers.
     */
    public void stop() {
        for (int i = 0; i < mDispatchers.length; i++) {
            if (mDispatchers[i] != null) {
                mDispatchers[i].quit();
            }
        }
    }

    /**
     * Starts the dispatchers in this queue.
     */
    public void start() {
        stop();  // Make sure any currently running dispatchers are stopped.

        // Create log dispatchers (and corresponding threads) up to the pool size.
        LogDispatcher networkDispatcher;
        for (int i = 0; i < mDispatchers.length; i++) {
            networkDispatcher = new LogDispatcher(mLogs, null);
            mDispatchers[i] = networkDispatcher;
            networkDispatcher.start();
        }
    }

    public void addLog(LogRecord logRecord) {
        mLogs.add(logRecord);
    }
}