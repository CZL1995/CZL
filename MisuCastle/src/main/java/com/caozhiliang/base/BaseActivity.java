package com.caozhiliang.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.xutils.x;

/**
 * author CZL.
 * time 2016/3/21 13:26
 */
public class BaseActivity extends AppCompatActivity{

    public final String URL="http://192.168.0.102:8080";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
    }
}
