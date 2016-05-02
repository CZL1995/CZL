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
public class Account extends Activity {
    private TextView tv_account;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.account);
        initView();
        initOnclik();

    }

    private void initOnclik() {
        tv_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(Account.this,MainActivity.class);
                setResult(3,intent);
                finish();
            }
        });


    }

    private void initView() {
        tv_account = (TextView)findViewById(R.id.tv_account);
    }



}
