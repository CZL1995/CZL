package com.caozhiliang.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.caozhiliang.httpdata.FinalData;
import com.caozhiliang.main.R;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.net.URLEncoder;


public class Order_addres_tianjia extends Activity {
    private Button bc;
    private TextView tv1;
    private EditText shouhuotext;
    private EditText sjtext;
    private EditText shengf;
    private EditText chengs;
    private EditText xianc;
    private EditText dztext;
    private EditText yztext;
    private String mshouhuotext;
    private String bianh;
    private String msjtext;
    private String mchengs;
    private String mshengf;
    private String mxianc;
    private String mdztext;
    private String myztext;
    private String path = FinalData.FUWU_PATH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mine_person_ad_dizhi);
        SharedPreferences sp = getApplication().getSharedPreferences("haha", MODE_PRIVATE);
        bianh = sp.getString("bianh", "");
        initview();
        initlistener();

    }

    private void initlistener() {
        bc.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("anxia");
                get(v);
            }
        });

        //返回按钮
        tv1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent();
                intent.setClass(Order_addres_tianjia.this, Order_person_addres.class);
                startActivity(intent);
                Order_addres_tianjia.this.finish();
            }
        });
    }

    private void initview() {
        bc = (Button) findViewById(R.id.btn12);
        tv1 = (TextView) findViewById(R.id.tv1);
        shouhuotext = (EditText) findViewById(R.id.shouhuotext);
        sjtext = (EditText) findViewById(R.id.sjtext);
        shengf = (EditText) findViewById(R.id.shengf);
        chengs = (EditText) findViewById(R.id.chengs);
        xianc = (EditText) findViewById(R.id.xianc);
        dztext = (EditText) findViewById(R.id.dztext);
        yztext = (EditText) findViewById(R.id.yztext);
    }

    public void get(View v) {
        mshouhuotext = shouhuotext.getText().toString();
        msjtext = sjtext.getText().toString();
        mchengs = chengs.getText().toString();
        mshengf = shengf.getText().toString();
        mxianc = xianc.getText().toString();
        mdztext = dztext.getText().toString();
        myztext = yztext.getText().toString();

        try {
            mshouhuotext = URLEncoder.encode(mshouhuotext, "UTF-8");
            mshouhuotext = URLEncoder.encode(mshouhuotext, "UTF-8");
            msjtext = URLEncoder.encode(msjtext, "UTF-8");
            msjtext = URLEncoder.encode(msjtext, "UTF-8");
            mchengs = URLEncoder.encode(mchengs, "UTF-8");
            mchengs = URLEncoder.encode(mchengs, "UTF-8");
            mshengf = URLEncoder.encode(mshengf, "UTF-8");
            mshengf = URLEncoder.encode(mshengf, "UTF-8");
            mxianc = URLEncoder.encode(mxianc, "UTF-8");
            mxianc = URLEncoder.encode(mxianc, "UTF-8");
            mdztext = URLEncoder.encode(mdztext, "UTF-8");
            mdztext = URLEncoder.encode(mdztext, "UTF-8");
            myztext = URLEncoder.encode(myztext, "UTF-8");
            myztext = URLEncoder.encode(myztext, "UTF-8");


        } catch (Exception e) {
        }
        RequestParams params = new RequestParams(path + "/AddressServlet?pan=tian&bianh=" + bianh +
                "&name=" + mshouhuotext + "&phone=" + msjtext + "&shengf=" + mshengf + "&chengs=" +
                mchengs +
                "&xianc=" + mxianc + "&xiangq=" +mdztext+
                "&youbian=" + myztext);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println(result);
                if (result.equals("添加成功")) {
                    Intent intent = new Intent(Order_addres_tianjia.this,
                            Order_person_addres.class);
                    Order_addres_tianjia.this.startActivity(intent);
                    Order_addres_tianjia.this.finish();
                } else {
                    System.out.println("返回值错误");
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

                System.out.println("cuowu");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }

}
