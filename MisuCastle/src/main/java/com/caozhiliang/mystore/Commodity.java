package com.caozhiliang.mystore;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.caozhiliang.main.MainActivity;
import com.caozhiliang.main.R;

/**
 * Author:CZL
 * time:2016/3/11  8:31
 */
public class Commodity extends Activity {

    private TextView tv_commodity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.commodity);
        initView();
        initOnclik();
    }

    private void initView() {
        tv_commodity = (TextView)findViewById(R.id.tv_commodity);


    }

    private void initOnclik() {
        tv_commodity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(Commodity.this,MainActivity.class);
                setResult(2,intent);
                finish();
            }
        });

    }
}
