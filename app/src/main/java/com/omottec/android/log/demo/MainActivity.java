package com.omottec.android.log.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;

import com.github.omottec.android.log.LogActivity;
import com.github.omottec.android.log.demo.R;

/**
 * Created by qinbingbing on 11/18/16.
 */

public class MainActivity extends FragmentActivity implements View.OnClickListener {
    private TextView mTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_main);
        mTv = (TextView) findViewById(R.id.tv);
        mTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv:
                Intent intent = new Intent(this, LogActivity.class);
                startActivity(intent);
        }
    }
}
