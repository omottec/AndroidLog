package com.github.omottec.android.log;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by qinbingbing on 11/16/16.
 */

public class LogActivity extends FragmentActivity implements View.OnClickListener {
    public static final String TAG = "LogActivity";
    private TextView mLogOnTv;
    private TextView mLogOffTv;
    private TextView mLogTv;
    private CatLogger mCatLogger = CatLogger.getLogger(null);
    private FileLogger mFileLogger;
    private int mClickCount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_log);
        mLogOnTv = (TextView) findViewById(R.id.log_on_tv);
        mLogOffTv = (TextView) findViewById(R.id.log_off_tv);
        mLogTv = (TextView) findViewById(R.id.log_tv);
        mLogOnTv.setOnClickListener(this);
        mLogOffTv.setOnClickListener(this);
        mLogTv.setOnClickListener(this);
        mFileLogger = FileLogger.getLogger(this);
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
            mFileLogger.d("click log " + mClickCount);
            mClickCount++;
        }
    }
}
