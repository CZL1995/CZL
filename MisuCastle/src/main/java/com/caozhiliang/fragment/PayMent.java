package com.caozhiliang.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.caozhiliang.base.BaseActivity;
import com.caozhiliang.main.R;

/**
 * @author CZL
 * @time 2016-04-18 13:43
 */
public class PayMent extends BaseActivity {

    private TextView tv_payment;
    private TextView tv_pay;
    private TextView text;
    private Button bu_pay;
    private CheckBox ib;
    private CheckBox ib1;

    String pri;
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {

            //让viewPager 滑动到下一页
            text.setVisibility(View.GONE);

        }

        ;
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment);
        initview();
        pri = getIntent().getStringExtra("pri");
        tv_payment.setText("支付金额："+pri);
        initlitenter();
    }

    private void initlitenter() {
        tv_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PayMent.this.finish();

            }
        });
        bu_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text.setVisibility(View.VISIBLE);
                handler.sendEmptyMessageDelayed(0, 2000);
            }
        });
        ib.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked) {
                    ib1.setChecked(false);
                }else{
                    ib1.setChecked(true);
                }

            }
        });
        ib1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    ib.setChecked(false);
                } else{
                    ib.setChecked(true);
                }

            }
        });

    }

    private void initview() {
        tv_payment = (TextView) findViewById(R.id.tv_payment);
        tv_pay = (TextView) findViewById(R.id.tv_pay);
        bu_pay = (Button) findViewById(R.id.bu_pay);
        text = (TextView) findViewById(R.id.text);
        ib = (CheckBox) findViewById(R.id.ib);
        ib1 = (CheckBox) findViewById(R.id.ib1);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            PayMent.this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
