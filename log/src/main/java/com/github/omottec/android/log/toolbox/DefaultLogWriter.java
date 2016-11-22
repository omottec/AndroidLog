package com.github.omottec.android.log.toolbox;

import com.github.omottec.android.log.LogRecord;
import com.github.omottec.android.log.LogWriter;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.channels.FileLock;

/**
 * Created by qinbingbing on 11/22/16.
 */

public class DefaultLogWriter implements LogWriter {
    @Override
    public void writeLog(LogRecord logRecord) {
        PrintWriter pw = null;
        FileLock lock = null;
        try {
            FileOutputStream fos = new FileOutputStream(logRecord.getFileName());
            pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(fos)));
            lock = fos.getChannel().lock();
            if (!logRecord.isEncrypted()) {
                pw.println(logRecord.toString());
            } else {
                // TODO
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (lock != null)
                try {
                    lock.release();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            if (pw != null)
                pw.close();
        }
    }
}
