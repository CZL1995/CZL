package com.caozhiliang.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.caozhiliang.base.BaseActivity;
import com.caozhiliang.httpdata.ImageData;
import com.caozhiliang.httpdata.TradeBean;
import com.caozhiliang.main.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

/**
 * @author CZL
 * @time 2016-04-14 15:17
 */
public class TradeDetailStore extends BaseActivity {


    int id;
    private TradeBean tradedata;
    private List<ImageData> imagedata;
    private ViewPager iv_homepage_viewpager;
    private TextView trade_name;
    private TextView trade_store_name;
    private TextView trade_location;
    private TextView trade_prices1;
    private TextView trade_prices2;
    private RelativeLayout rl;
    private TextView tv_trade;
    private Button bt_buy;
    private ImageView imageView;
    private ImageView iv_back;
    int length;
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {

            //让viewPager 滑动到下一页
            iv_homepage_viewpager.setCurrentItem(iv_homepage_viewpager.getCurrentItem() + 1);
            handler.sendEmptyMessageDelayed(0, 2000);

        }

        ;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trade_detail);
        initview();
        id = getIntent().getIntExtra("id", 0);
        GetTradeData();
        GetTradeImageData();
        initlistener();
    }

    private void initlistener() {
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TradeDetailStore.this.finish();

            }
        });
        rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(TradeDetailStore.this, StoreDetailsTrade.class);
                intent.putExtra("id", tradedata.getStoreNumber());
                startActivity(intent);
            }
        });
        bt_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sp = getApplication().getSharedPreferences("haha",
                        MODE_PRIVATE);
                SharedPreferences.Editor edt = sp.edit();
                edt.putString("imageaddress", imagedata.get(0).getImageaddress());
                edt.putString("tradepricess", tradedata.getPrice1());
                edt.putString("tradename", tradedata.getStorename());
                String number = String.valueOf(tradedata.getNumber());
                edt.putString("tradenumber", number);
                edt.commit();
                Intent intent = new Intent();
                intent.setClass(TradeDetailStore.this, Order.class);
                startActivity(intent);
            }
        });

    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            TradeDetailStore.this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void initview() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        rl = (RelativeLayout) findViewById(R.id.rl);
        bt_buy = (Button) findViewById(R.id.bt_buy);
        iv_homepage_viewpager = (ViewPager) findViewById(R.id.iv_homepage_viewpager);
        trade_name = (TextView) findViewById(R.id.trade_name);
        trade_store_name = (TextView) findViewById(R.id.trade_store_name);
        trade_location = (TextView) findViewById(R.id.trade_location);
        trade_prices1 = (TextView) findViewById(R.id.trade_prices1);
        trade_prices2 = (TextView) findViewById(R.id.trade_prices2);
        tv_trade = (TextView) findViewById(R.id.tv_trade);
    }


    private void GetTradeData() {
        RequestParams tradeParams = new RequestParams(URL + "/TradeServlet?number=" + id);
        x.http().get(tradeParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gs = new Gson();
                tradedata = gs.fromJson(result, TradeBean.class);
                System.out.println(tradedata);
                trade_name.setText(tradedata.getStorename());
                trade_store_name.setText(tradedata.getStorenamezheng());
                trade_location.setText(tradedata.getAddress());
                trade_prices1.setText("¥"+tradedata.getPrice1());
                trade_prices2.setText("¥"+tradedata.getPrice2());
                trade_prices2.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                tv_trade.setText(tradedata.getJianjie());
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

    private void GetTradeImageData() {
        RequestParams tradeimageParams = new RequestParams(URL + "/ImageTradeServlet?number=" + id);

        x.http().get(tradeimageParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gs = new Gson();
                imagedata = gs.fromJson(result, new TypeToken<List<ImageData>>() {
                }.getType());
                length = imagedata.size();
                System.out.println(imagedata);
                System.out.println(length);
                iv_homepage_viewpager.setAdapter(new toViewpager());
                if (length >= 3) {
                    handler.sendEmptyMessageDelayed(0, 2000);
                }

                System.out.println(imagedata.size());


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

    class toViewpager extends PagerAdapter {


        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            imageView = new ImageView(getApplicationContext());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageResource(R.mipmap.loge);

            x.image().bind(imageView, imagedata.get(position % length).getImageaddress());
            container.addView(imageView);
            return imageView;
        }
    }

}
