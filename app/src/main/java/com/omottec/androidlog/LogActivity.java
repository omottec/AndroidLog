package com.omottec.androidlog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.omottec.android.log.Logger;

/**
 * Created by qinbingbing on 11/16/16.
 */

public class LogActivity extends FragmentActivity implements View.OnClickListener {
    public static final String TAG = "LogActivity";
    private TextView mLogOnTv;
    private TextView mLogOffTv;
    private TextView mLogTv;

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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.log_on_tv:
                Logger.setLevel(Logger.VERBOSE);
                Toast.makeText(this, "log on", Toast.LENGTH_SHORT).show();
                break;
            case R.id.log_off_tv:
                Logger.setLevel(Logger.NONE);
                Toast.makeText(this, "log off", Toast.LENGTH_LONG).show();
                break;
            case R.id.log_tv:
                Logger.d(TAG, "click log");
                break;
        }
    }
}
