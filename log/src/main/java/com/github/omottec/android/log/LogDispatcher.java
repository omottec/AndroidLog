package com.github.omottec.android.log;

import android.os.Process;

import com.github.omottec.android.log.toolbox.DefaultLogWriter;

import java.util.concurrent.BlockingQueue;

/**
 * Created by qinbingbing on 11/22/16.
 */

class LogDispatcher extends Thread {
    /** The queue of logs to write. */
    private final BlockingQueue<LogRecord> mQueue;

    private LogWriter mLogWriter;

    /** Used for telling us to die. */
    private volatile boolean mQuit = false;

    LogDispatcher(BlockingQueue<LogRecord> queue, LogWriter writer) {
        mQueue = queue;
        mLogWriter = writer != null ? writer : new DefaultLogWriter();
    }

    /**
     * Forces this writer to quit immediately.  If any logs are still in
     * the queue, they are not guaranteed to be processed.
     */
    public void quit() {
        mQuit = true;
        interrupt();
    }

    @Override
    public void run() {
        Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
        while (true) {
            LogRecord logRecord;
            try {
                // Take a logRecord from the queue.
                logRecord = mQueue.take();
            } catch (InterruptedException e) {
                // We may have been interrupted because it was time to quit.
                if (mQuit) {
                    return;
                }
                continue;
            }
            try {
                mLogWriter.writeLog(logRecord);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
