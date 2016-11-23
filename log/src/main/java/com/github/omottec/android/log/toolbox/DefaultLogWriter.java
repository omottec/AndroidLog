package com.github.omottec.android.log.toolbox;

import android.util.Log;

import com.github.omottec.android.log.LogRecord;
import com.github.omottec.android.log.LogWriter;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.channels.FileLock;

import javax.crypto.Cipher;

/**
 * Created by qinbingbing on 11/22/16.
 */

public class DefaultLogWriter implements LogWriter {
    public static final String TAG = "DefaultLogWriter";

    @Override
    public void writeLog(LogRecord logRecord) {
        if (logRecord.isEncrypted())
            cryptWrite(logRecord);
        else
            plainWrite(logRecord);
    }

    private void plainWrite(LogRecord logRecord) {
        PrintWriter pw = null;
        FileLock lock = null;
        try {
            FileOutputStream fos = new FileOutputStream(logRecord.getFilePath(), true);
            pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(fos)));
            lock = fos.getChannel().lock();
            pw.println(logRecord.toString());
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
            IoUtils.close(pw);
        }
    }

    private void cryptWrite(LogRecord logRecord) {
        ByteArrayInputStream bis = null;
        FileOutputStream fos = null;
        FileLock lock = null;
        try {
            String log = logRecord + "\n";
            bis = new ByteArrayInputStream(log.getBytes());
            fos = new FileOutputStream(logRecord.getFilePath(), true);
            lock = fos.getChannel().lock();
            AesCipher.crypt(bis, fos, Cipher.ENCRYPT_MODE);
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
            IoUtils.close(bis, fos);
        }
    }
}
