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
public class OurParter extends Activity {
    private TextView tv_parter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.ourparter);
        initView();
        initOnclick();
    }

    private void initOnclick() {
        tv_parter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(OurParter.this,MainActivity.class);
                setResult(5,intent);
                finish();
            }
        });
    }

    private void initView() {
        tv_parter = (TextView)findViewById(R.id.tv_parter);
    }
}
