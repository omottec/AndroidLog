package com.github.omottec.android.log;

import android.text.TextUtils;
import android.util.Log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by qinbingbing on 11/21/16.
 */

public abstract class AbstractLogger {
    private static final int CALL_STACK_INDEX = 3;
    private static final Pattern ANONYMOUS_CLASS = Pattern.compile("(\\$\\d+)+$");
    private static int sLevel = Integer.MAX_VALUE;

    private String mTag;

    AbstractLogger(String tag) {
        mTag = tag;
    }

    /** Log a verbose message with optional format args. */
    public void v(String message, Object... args) {
        prepareLog(Log.VERBOSE, null, null, message, args);
    }

    public void v(String tag, String message, Object... args) {
        prepareLog(Log.VERBOSE, tag, null, message, args);
    }

    /** Log a verbose exception and a message with optional format args. */
    public void v(Throwable t, String message, Object... args) {
        prepareLog(Log.VERBOSE, null, t, message, args);
    }

    public void v(String tag, Throwable t, String message, Object... args) {
        prepareLog(Log.VERBOSE, tag, t, message, args);
    }

    /** Log a verbose exception. */
    public void v(Throwable t) {
        prepareLog(Log.VERBOSE, null, t, null);
    }

    public void v(String tag, Throwable t) {
        prepareLog(Log.VERBOSE, tag, t, null);
    }

    /** Log a debug message with optional format args. */
    public void d(String message, Object... args) {
        prepareLog(Log.DEBUG, null, null, message, args);
    }

    public void d(String tag, String message, Object... args) {
        prepareLog(Log.DEBUG, tag, null, message, args);
    }

    /** Log a debug exception and a message with optional format args. */
    public void d(Throwable t, String message, Object... args) {
        prepareLog(Log.DEBUG, null, t, message, args);
    }

    public void d(String tag, Throwable t, String message, Object... args) {
        prepareLog(Log.DEBUG, tag, t, message, args);
    }

    /** Log a debug exception. */
    public void d(Throwable t) {
        prepareLog(Log.DEBUG, null, t, null);
    }

    public void d(String tag, Throwable t) {
        prepareLog(Log.DEBUG, tag, t, null);
    }

    /** Log an info message with optional format args. */
    public void i(String message, Object... args) {
        prepareLog(Log.INFO, null, null, message, args);
    }

    public void i(String tag, String message, Object... args) {
        prepareLog(Log.INFO, tag, null, message, args);
    }

    /** Log an info exception and a message with optional format args. */
    public void i(Throwable t, String message, Object... args) {
        prepareLog(Log.INFO, null, t, message, args);
    }

    public void i(String tag, Throwable t, String message, Object... args) {
        prepareLog(Log.INFO, tag, t, message, args);
    }

    /** Log an info exception. */
    public void i(Throwable t) {
        prepareLog(Log.INFO, null, t, null);
    }

    public void i(String tag, Throwable t) {
        prepareLog(Log.INFO, tag, t, null);
    }

    /** Log a warning message with optional format args. */
    public void w(String message, Object... args) {
        prepareLog(Log.WARN, null, null, message, args);
    }

    public void w(String tag, String message, Object... args) {
        prepareLog(Log.WARN, tag, null, message, args);
    }

    /** Log a warning exception and a message with optional format args. */
    public void w(Throwable t, String message, Object... args) {
        prepareLog(Log.WARN, null, t, message, args);
    }

    public void w(String tag, Throwable t, String message, Object... args) {
        prepareLog(Log.WARN, tag, t, message, args);
    }

    /** Log a warning exception. */
    public void w(Throwable t) {
        prepareLog(Log.WARN, null, t, null);
    }

    public void w(String tag, Throwable t) {
        prepareLog(Log.WARN, tag, t, null);
    }

    /** Log an error message with optional format args. */
    public void e(String message, Object... args) {
        prepareLog(Log.ERROR, null, null, message, args);
    }

    public void e(String tag, String message, Object... args) {
        prepareLog(Log.ERROR, tag, null, message, args);
    }

    /** Log an error exception and a message with optional format args. */
    public void e(Throwable t, String message, Object... args) {
        prepareLog(Log.ERROR, null, t, message, args);
    }

    public void e(String tag, Throwable t, String message, Object... args) {
        prepareLog(Log.ERROR, tag, t, message, args);
    }

    /** Log an error exception. */
    public void e(Throwable t) {
        prepareLog(Log.ERROR, null, t, null);
    }

    public void e(String tag, Throwable t) {
        prepareLog(Log.ERROR, tag, t, null);
    }

    /** Log an assert message with optional format args. */
    public void wtf(String message, Object... args) {
        prepareLog(Log.ASSERT, null, null, message, args);
    }

    public void wtf(String tag, String message, Object... args) {
        prepareLog(Log.ASSERT, tag, null, message, args);
    }

    /** Log an assert exception and a message with optional format args. */
    public void wtf(Throwable t, String message, Object... args) {
        prepareLog(Log.ASSERT, null, t, message, args);
    }

    public void wtf(String tag, Throwable t, String message, Object... args) {
        prepareLog(Log.ASSERT, tag, t, message, args);
    }

    /** Log an assert exception. */
    public void wtf(Throwable t) {
        prepareLog(Log.ASSERT, null, t, null);
    }

    public void wtf(String tag, Throwable t) {
        prepareLog(Log.ASSERT, tag, t, null);
    }

    private void prepareLog(int priority, String tag, Throwable t, String message, Object... args) {
        if (!isLoggable(tag, priority)) return;
        if (TextUtils.isEmpty(tag)) tag = getTag();
        String msg = getMessage(t, message, args);
        if (TextUtils.isEmpty(msg)) return;
        log(priority, tag, msg, t);
    }

    protected String getTag() {
        if (!TextUtils.isEmpty(mTag)) return mTag;
        // DO NOT switch this to Thread.getCurrentThread().getStackTrace(). The test will pass
        // because Robolectric runs them on the JVM but on Android the elements are different.
        StackTraceElement[] stackTrace = new Throwable().getStackTrace();
        if (stackTrace.length <= CALL_STACK_INDEX)
            return getClass().getSimpleName();
        return createStackElementTag(stackTrace[CALL_STACK_INDEX]);

    }

    /**
     * Extract the tag which should be used for the message from the {@code element}. By default
     * this will use the class name without any anonymous class suffixes (e.g., {@code Foo$1}
     * becomes {@code Foo}).
     * <p>
     * Note: This will not be called if a {@linkplain #tag(String) manual tag} was specified.
     */
    private String createStackElementTag(StackTraceElement element) {
        String tag = element.getClassName();
        Matcher m = ANONYMOUS_CLASS.matcher(tag);
        if (m.find()) {
            tag = m.replaceAll("");
        }
        return tag.substring(tag.lastIndexOf('.') + 1);
    }

    protected String getMessage(Throwable t, String message, Object... args) {
        if (TextUtils.isEmpty(message)) {
            if (t == null) return null;
            return getStackTraceString(t);
        } else {
            if (args != null && args.length > 0)
                message = String.format(message, args);
            if (t != null)
                message += "\n" + getStackTraceString(t);
            return message;
        }
    }

    private String getStackTraceString(Throwable t) {
        // Don't replace this with Log.getStackTraceString() - it hides
        // UnknownHostException, which is not what we want.
        StringWriter sw = new StringWriter(256);
        PrintWriter pw = new PrintWriter(sw, false);
        t.printStackTrace(pw);
        pw.flush();
        return sw.toString();
    }

    protected boolean isLoggable(String tag, int priority) {
        return priority >= sLevel;
    }

    public static void setLogLevel(int level) {
        sLevel = level;
    }

    /**
     * Write a log message to its destination. Called for all level-specific methods by default.
     *
     * @param priority Log level. See {@link Log} for constants.
     * @param tag Explicit or inferred tag. May be {@code null}.
     * @param message Formatted log message. May be {@code null}, but then {@code t} will not be.
     * @param t Accompanying exceptions. May be {@code null}, but then {@code message} will not be.
     */
    protected abstract void log(int priority, String tag, String message, Throwable t);
}
