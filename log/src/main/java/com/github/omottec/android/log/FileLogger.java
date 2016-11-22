package com.github.omottec.android.log;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Process;
import android.text.TextUtils;

import com.github.omottec.android.log.toolbox.DefaultLogWriter;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by qinbingbing on 11/21/16.
 */

public class FileLogger extends AbstractLogger {
    private static LogQueue sLogQueue = new LogQueue(new DefaultLogWriter());
    static {
        sLogQueue.start();
    }
    private SimpleDateFormat mLogDateTime = new SimpleDateFormat("MM-dd HH:mm:ss.SSS");
    private boolean mEncrypted = false;
    private Context mAppContext;
    private String mFileName;


    private FileLogger(Context context, String tag, String fileName, boolean encrypted) {
        super(tag);
        mAppContext = context.getApplicationContext();
        mEncrypted = encrypted;
        if (!TextUtils.isEmpty(mFileName)) {
            mFileName = fileName;
        } else {
            fileName = new SimpleDateFormat("yyyyMMdd").format(new Date());
            mFileName = new File(mAppContext.getFilesDir(),
                    encrypted ? 'c' + fileName : fileName).getAbsolutePath();
        }
    }

    public static FileLogger getLogger(Context context, String tag, String fileName, boolean encrypted) {
        return new FileLogger(context, tag, fileName, encrypted);
    }

    public static FileLogger getLogger(Context context) {
        return new FileLogger(context, null, null, false);
    }

    @Override
    protected void log(int priority, String tag, String message, Throwable t) {
        LogRecord logRecord = new LogRecord(mLogDateTime.format(new Date()),
                Process.myPid(),
                Process.myTid(),
                getCurProcessName(),
                priority,
                tag,
                message,
                mFileName,
                mEncrypted);
        sLogQueue.addLog(logRecord);
    }

    private String getCurProcessName() {
        String currentProcessName = "";
        int pid = Process.myPid();
        ActivityManager manager = (ActivityManager) mAppContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = manager.getRunningAppProcesses();
        if (runningAppProcesses == null || runningAppProcesses.isEmpty()) return currentProcessName;
        for (ActivityManager.RunningAppProcessInfo processInfo : runningAppProcesses)
            if (processInfo.pid == pid) {
                currentProcessName = processInfo.processName;
                break;
            }
        return currentProcessName;
    }


}
