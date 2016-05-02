package com.caozhiliang.mystore;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.caozhiliang.main.MainActivity;
import com.caozhiliang.main.R;

/**
 * Author:CZL
 * time:2016/3/11  8:31
 */
public class Setting extends Activity {
    private TextView tv_setting;
    private SharedPreferences mPref;
    private TextView do_business;
    private CheckSwitchButton do_business_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPref = getSharedPreferences("config", MODE_PRIVATE);
        setContentView(R.layout.setting);
        initView();
        initOnclick();
        boolean judge = mPref.getBoolean("check", false);
        if (judge) {
            do_business.setText("营业");
            do_business_button.setChecked(true);
        } else {
            do_business.setText("不营业");
            do_business_button.setChecked(false);
        }


    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();

        Intent intent = new Intent();
        intent.setClass(Setting.this, MainActivity.class);
        setResult(4, intent);
        finish();
    }

    private void initOnclick() {
        tv_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Setting.this, MainActivity.class);
                setResult(4, intent);
                finish();
            }
        });

        do_business_button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
//    Toast.makeText(Setting.this, "开", Toast.LENGTH_SHORT).show();
                    do_business.setText("营业");
                    mPref.edit().putBoolean("check", true).commit();
                } else {
                    do_business.setText("不营业");
//Toast.makeText(Setting.this, "关", Toast.LENGTH_SHORT).show();
                    mPref.edit().putBoolean("check", false).commit();
                }
            }
        });


    }

    private void initView() {
        do_business = (TextView) findViewById(R.id.do_business);
        tv_setting = (TextView) findViewById(R.id.tv_setting);
        do_business_button = (CheckSwitchButton) findViewById(R.id.do_business_button);
    }
}
