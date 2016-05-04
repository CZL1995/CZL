package com.caozhiliang.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.caozhiliang.base.BaseActivity;
import com.caozhiliang.main.R;

import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.net.URLEncoder;

/**
 * @author CZL
 * @time 2016-04-17 20:55
 */
public class Order extends BaseActivity {
    String name;
    String image;
    String prices;
    String tradenumber;
    String liuyan;
    private ImageView iv_trade_image;
    private Button button_bug;
    private ImageButton iv_decrease;
    private ImageButton increase;
    private TextView tv_trade_name;
    private TextView number;
    private TextView total_prices;
    private TextView tv_buypeople;
    private TextView tv_buyaddress;
    private TextView tv_order;
    private TextView tv_account;
    private EditText ed_notes;
    private RelativeLayout rl1111;
    private TextView tradeprices;
    ImageOptions imageOptions1;
    int i = 1;
    int pricess1;
    String bianh;
    String address111;
    String addressname;
    String addressdetales;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order);
        SharedPreferences sp = getApplication().getSharedPreferences("haha", MODE_PRIVATE);
        bianh = sp.getString("bianh", "");
        System.out.println(bianh);
        address111 = sp.getString("addressnumber", "");
        name = sp.getString("tradename", "");
        prices = sp.getString("tradepricess", "");
        image = sp.getString("imageaddress", "");
        tradenumber = sp.getString("tradenumber", "");
        System.out.println(address111);
        addressname = sp.getString("addressname", "");
        addressdetales = sp.getString("addressdetails", "");

        initView();
        initData();
        initlistenter();
    }

    private void initlistenter() {
        try {
            pricess1 = Integer.valueOf(prices).intValue();

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        number.setText(String.valueOf(i));
        tv_account.setText("¥" + String.valueOf(pricess1 * i));
        total_prices.setText("¥" + String.valueOf(pricess1 * i));
        iv_decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i > 1) {
                    i--;
                    number.setText(String.valueOf(i));
                    tv_account.setText("¥" + String.valueOf(pricess1 * i));
                    total_prices.setText("¥" + String.valueOf(pricess1 * i));

                }

            }
        });
        increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i++;
                number.setText(String.valueOf(i));
                tv_account.setText("¥" + String.valueOf(pricess1 * i));
                total_prices.setText("¥" + String.valueOf(pricess1 * i));


            }
        });
        tv_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Order.this.finish();
            }
        });
        button_bug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                liuyan = ed_notes.getText().toString();

                try {
                    liuyan = URLEncoder.encode(liuyan, "UTF-8");
                    liuyan = URLEncoder.encode(liuyan, "UTF-8");
                } catch (Exception e) {
                }

                if (addressname == "") {
                    Toast.makeText(Order.this, "请输入收货地址", Toast.LENGTH_SHORT).show();
                } else {

                    Intent intent = new Intent();
                    intent.setClass(Order.this, PayMent.class);
                    intent.putExtra("pri", String.valueOf(pricess1 * i));
                    intent.putExtra("liu", liuyan);
                    intent.putExtra("i", String.valueOf(i));
                    startActivity(intent);
                }

            }
        });




        rl1111.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Order.this, Order_person_addres.class);
                startActivity(intent);
                Order.this.finish();
            }
        });
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            Order.this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void initData() {
        imageOptions1 = new ImageOptions.Builder()
                .setImageScaleType(ImageView.ScaleType.FIT_XY)
                .setRadius(DensityUtil.dip2px(5))
                .setLoadingDrawableId(R.mipmap.loge)
                .setPlaceholderScaleType(ImageView.ScaleType.FIT_XY)
                .setFailureDrawableId(R.mipmap.loge)
                .build();
        x.image().bind(iv_trade_image, image, imageOptions1);
        tv_trade_name.setText(name);
        tradeprices.setText("¥" + prices);
        tv_buypeople.setText("收货人：" + addressname);
        tv_buyaddress.setText("收货地址：" + addressdetales);
    }

    private void initView() {
        rl1111 = (RelativeLayout) findViewById(R.id.rl1111);
        button_bug = (Button) findViewById(R.id.button_bug);
        tv_order = (TextView) findViewById(R.id.tv_order);
        tv_buypeople = (TextView) findViewById(R.id.tv_buypeople);
        tv_buyaddress = (TextView) findViewById(R.id.tv_buyaddress);
        iv_decrease = (ImageButton) findViewById(R.id.iv_decrease);
        ed_notes = (EditText) findViewById(R.id.ed_notes);
        number = (TextView) findViewById(R.id.number);
        total_prices = (TextView) findViewById(R.id.total_prices);
        tv_account = (TextView) findViewById(R.id.tv_account);
        increase = (ImageButton) findViewById(R.id.increase);
        iv_trade_image = (ImageView) findViewById(R.id.iv_trade_image);
        tv_trade_name = (TextView) findViewById(R.id.tv_trade_name);
        tradeprices = (TextView) findViewById(R.id.tradeprices);
        ed_notes = (EditText) findViewById(R.id.ed_notes);
    }

}
