package com.github.omottec.android.log;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.omottec.android.log.toolbox.AesCipher;

import java.io.File;

/**
 * Created by qinbingbing on 11/16/16.
 */

public class LogActivity extends FragmentActivity implements View.OnClickListener {
    public static final String TAG = "LogActivity";
    private TextView mLogOnTv;
    private TextView mLogOffTv;
    private TextView mLogTv;
    private EditText mEt;
    private Button mDecryptBtn;
    private CatLogger mCatLogger = CatLogger.getLogger(null);
    private FileLogger mPlainFileLogger;
    private FileLogger mCipherFileLogger;
    private int mClickCount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_log);
        mLogOnTv = (TextView) findViewById(R.id.log_on_tv);
        mLogOffTv = (TextView) findViewById(R.id.log_off_tv);
        mLogTv = (TextView) findViewById(R.id.log_tv);
        mEt = (EditText) findViewById(R.id.et);
        mDecryptBtn = (Button) findViewById(R.id.decrypt_btn);
        mLogOnTv.setOnClickListener(this);
        mLogOffTv.setOnClickListener(this);
        mLogTv.setOnClickListener(this);
        mDecryptBtn.setOnClickListener(this);
        mPlainFileLogger = FileLogger.getLogger(this);
        mCipherFileLogger = FileLogger.getLogger(this, null, null, true);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (R.id.log_on_tv == id) {
//            Logger.setLevel(Logger.VERBOSE);
            AbstractLogger.setLogLevel(Log.VERBOSE);
            Toast.makeText(this, "log on", Toast.LENGTH_SHORT).show();
        } else if (R.id.log_off_tv == id) {
//            Logger.setLevel(Logger.NONE);
            AbstractLogger.setLogLevel(Integer.MAX_VALUE);
            Toast.makeText(this, "log off", Toast.LENGTH_LONG).show();
        } else if (R.id.log_tv == id) {
//            Logger.d(TAG, "click log");
            mCatLogger.d("click log " + mClickCount);
            mPlainFileLogger.d("click log " + mClickCount);
            mCipherFileLogger.d("click log " + mClickCount);
            mClickCount++;
        } else if (R.id.decrypt_btn == id) {
            String fileName = mEt.getText().toString();
            AesCipher.decrypt(new File(getApplicationContext().getFilesDir(), fileName));
            mEt.setText("");
        }
    }
}
