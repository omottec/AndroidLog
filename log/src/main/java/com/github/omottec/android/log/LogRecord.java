package com.github.omottec.android.log;


import android.os.Process;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by qinbingbing on 11/22/16.
 */

public class LogRecord {
    private String dateTime;
    private int pid;
    private int tid;
    private String processName;
    private int priority;
    private String tag;
    private String msg;
    private String fileName;
    private boolean encrypted;

    public LogRecord(String dateTime, int pid, int tid, String processName, int priority, String tag, String msg, String fileName, boolean encrypted) {
        this.dateTime = dateTime;
        this.pid = pid;
        this.tid = tid;
        this.processName = processName;
        this.priority = priority;
        this.tag = tag;
        this.msg = msg;
        this.fileName = fileName;
        this.encrypted = encrypted;
    }

    @Override
    public String toString() {
        StringBuilder record = new StringBuilder();
        record
                .append(dateTime).append(" ")
                .append(pid).append("-").append(tid).append("/")
                .append(processName).append(" ")
                .append(toStrPriority(priority)).append("/")
                .append(tag).append(": ")
                .append(msg);
        return record.toString();
    }

    private String toStrPriority(int priority) {
        switch (priority) {
            case Log.VERBOSE:
                return "V";
            case Log.DEBUG:
            default:
                return "D";
            case Log.INFO:
                return "I";
            case Log.WARN:
                return "W";
            case Log.ERROR:
                return "E";
            case Log.ASSERT:
                return "A";
        }
    }

    public String getDateTime() {
        return dateTime;
    }

    public int getPid() {
        return pid;
    }

    public int getTid() {
        return tid;
    }

    public String getProcessName() {
        return processName;
    }

    public int getPriority() {
        return priority;
    }

    public String getTag() {
        return tag;
    }

    public String getMsg() {
        return msg;
    }

    public String getFileName() {
        return fileName;
    }

    public boolean isEncrypted() {
        return encrypted;
    }
}
