package com.caozhiliang.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.caozhiliang.base.BaseActivity;
import com.caozhiliang.main.R;

import org.xutils.common.Callback;
import org.xutils.common.util.DensityUtil;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

/**
 * @author CZL
 * @time 2016-04-17 20:55
 */
public class Order extends BaseActivity {
    String name;
    String image;
    String prices;
    String tradenumber;
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
        name = getIntent().getStringExtra("tradename");
        image = getIntent().getStringExtra("imageaddress");
        prices = getIntent().getStringExtra("tradepricess");
        tradenumber = getIntent().getStringExtra("tradenumber");
        System.out.println(tradenumber);
        SharedPreferences sp = getApplication().getSharedPreferences("haha", MODE_PRIVATE);
        bianh = sp.getString("bianh", "");
        System.out.println(bianh);
        address111 = sp.getString("addressnumber","");
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
                RequestParams requestParams = new RequestParams(URL +
                        "/OrderServlet?pan=tian&&number=" + tradenumber + "&&count=" + i +
                        "&&usernumber=" + bianh + "&&zongjia=" + String.valueOf(pricess1 * i) +
                        "&&liuyan=" + ed_notes.getText().toString() + "&&addressnumber=" + address111);
                x.http().get(requestParams, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        if (result.equals("订单添加成功")) {
                            Toast.makeText(Order.this, "asda", Toast.LENGTH_SHORT).show();

                        }

                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        System.out.println("asd");

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
        tv_buypeople.setText(addressname);
        tv_buyaddress.setText(addressdetales);
    }

    private void initView() {
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
