package com.caozhiliang.base;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.xutils.x;

/**
 * author CZL.
 * time 2016/3/21 13:26
 */
public class BaseActivity extends AppCompatActivity{

    public final String URL="http://119.29.148.150:8080/Fuwu1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
    }

}
