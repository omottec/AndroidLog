package com.github.omottec.android.log;

import android.util.Log;
/**
 * Created by qinbingbing on 11/21/16.
 */

public class CatLogger extends AbstractLogger {
    private static final int MAX_LOG_LENGTH = 4000;

    private CatLogger(String name) {
        super(name);
    }

    public static CatLogger getLogger(String name) {
        return new CatLogger(name);
    }

    @Override
    protected void log(int priority, String tag, String message, Throwable t) {
        if (message.length() < MAX_LOG_LENGTH) {
            if (priority == Log.ASSERT) {
                Log.wtf(tag, message);
            } else {
                Log.println(priority, tag, message);
            }
            return;
        }

        // Split by line, then ensure each line can fit into Log's maximum length.
        for (int i = 0, length = message.length(); i < length; i++) {
            int newline = message.indexOf('\n', i);
            newline = newline != -1 ? newline : length;
            do {
                int end = Math.min(newline, i + MAX_LOG_LENGTH);
                String part = message.substring(i, end);
                if (priority == Log.ASSERT) {
                    Log.wtf(tag, part);
                } else {
                    Log.println(priority, tag, part);
                }
                i = end;
            } while (i < newline);
        }
    }
}
