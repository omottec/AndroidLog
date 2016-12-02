package com.github.omottec.android.log;

import android.util.Log;

public final class Logger {

    private static int sLevel = Log.VERBOSE;

    private Logger() { throw new RuntimeException(); }

    /**
     * Send a VERBOSE log message.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static int v(String tag, String msg) {
        if (sLevel > Log.VERBOSE) return 0;
        return Log.v(tag, msg);
    }

    /**
     * Send a VERBOSE log message and log the exception.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr An exception to log
     */
    public static int v(String tag, String msg, Throwable tr) {
        if (sLevel > Log.VERBOSE) return 0;
        return Log.v(tag, msg, tr);
    }

    /**
     * Send a DEBUG log message.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static int d(String tag, String msg) {
        if (sLevel > Log.DEBUG) return 0;
        return Log.d(tag, msg);
    }

    /**
     * Send a log message and log the exception.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr An exception to log
     */
    public static int d(String tag, String msg, Throwable tr) {
        if (sLevel > Log.DEBUG) return 0;
        return Log.d(tag, msg, tr);
    }

    /**
     * Send an INFO log message.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static int i(String tag, String msg) {
        if (sLevel > Log.INFO) return 0;
        return Log.i(tag, msg);
    }

    /**
     * Send a INFO log message and log the exception.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr An exception to log
     */
    public static int i(String tag, String msg, Throwable tr) {
        if (sLevel > Log.INFO) return 0;
        return Log.i(tag, msg, tr);
    }

    /**
     * Send a WARN log message.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static int w(String tag, String msg) {
        if (sLevel > Log.WARN) return 0;
        return Log.w(tag, msg);
    }

    /**
     * Send a WARN log message and log the exception.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr An exception to log
     */
    public static int w(String tag, String msg, Throwable tr) {
        if (sLevel > Log.WARN) return 0;
        return Log.w(tag, msg, tr);
    }

    /*
     * Send a WARN log message and log the exception.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param tr An exception to log
     */
    public static int w(String tag, Throwable tr) {
        if (sLevel > Log.WARN) return 0;
        return Log.w(tag, tr);
    }

    /**
     * Send an ERROR log message.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static int e(String tag, String msg) {
        if (sLevel > Log.ERROR) return 0;
        return Log.e(tag, msg);
    }

    /**
     * Send a ERROR log message and log the exception.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr An exception to log
     */
    public static int e(String tag, String msg, Throwable tr) {
        if (sLevel > Log.ERROR) return 0;
        return Log.e(tag, msg, tr);
    }

    public static void logClassAndMethod(Object object) {
        StringBuilder sb = new StringBuilder();
        sb.append(object.hashCode());
        StackTraceElement[] elements = new Throwable().fillInStackTrace().getStackTrace();
        if (elements != null && elements.length >= 2 && elements[1] != null)
            sb.append("|").append(elements[1].getMethodName());
        d(object.getClass().getSimpleName(), sb.toString());
    }

    public static void setLevel(int level) {
        sLevel = level;
    }
}
