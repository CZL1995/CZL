package com.caozhiliang.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.caozhiliang.base.BaseActivity;
import com.caozhiliang.main.MyOrder;
import com.caozhiliang.main.R;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.net.URLEncoder;

/**
 * @author CZL
 * @time 2016-04-18 13:43
 */
public class PayGaiMent extends BaseActivity {

    private TextView tv_payment;
    private TextView tv_pay;
    private TextView text;
    private Button bu_pay;
    private CheckBox ib;
    private CheckBox ib1;
    int pricess1;
    String bianh;
    String address111;
    String addressname;
    String addressdetales;
    String name;
    String image;
    String prices;
    String tradenumber;
    String liuyan;
    String zhuangtai;
    String zhuangtai1;
    AlertDialog.Builder Dialog;
    String pri;
    String i;
    String dan;
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
        i = getIntent().getStringExtra("i");
        dan = getIntent().getStringExtra("dan");
        tv_payment.setText("支付金额：" + pri);

        initlitenter();
    }

    private void initlitenter() {
        tv_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });
        bu_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text.setVisibility(View.VISIBLE);
                handler.sendEmptyMessageDelayed(0, 2000);
                zhuangtai = "等待发货";
                try {
                    zhuangtai = URLEncoder.encode(zhuangtai, "UTF-8");
                    zhuangtai = URLEncoder.encode(zhuangtai, "UTF-8");
                } catch (Exception e) {
                }
                RequestParams requestParams = new RequestParams(URL +
                        "/OrderServlet?pan=gai&&ordernumber=" + dan +
                        "&&zhuangtai=" + zhuangtai);
                x.http().get(requestParams, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Dialog = new AlertDialog.Builder(PayGaiMent.this);
                        Dialog.setMessage("请问是否去订单界面");
                        Dialog.setTitle("提示");
                        Dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                Intent intent = new Intent(PayGaiMent.this, MyOrder.class);
                                PayGaiMent.this.startActivity(intent);
                                PayGaiMent.this.finish();
                            }
                        });
                        Dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                PayGaiMent.this.finish();
                            }
                        });
                        Dialog.create().show();

                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {

                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });


            }
        });
        ib.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    ib1.setChecked(false);
                } else {
                    ib1.setChecked(true);
                }

            }
        });
        ib1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ib.setChecked(false);
                } else {
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
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
