package com.caozhiliang.mystore;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.caozhiliang.httpdata.FinalData;
import com.caozhiliang.httpdata.StoreBean;
import com.caozhiliang.main.MainActivity;
import com.caozhiliang.main.R;
import com.google.gson.Gson;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import org.xutils.common.Callback;
import org.xutils.common.util.DensityUtil;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

/**
 * Author:CZL
 * Email:1145340329@qq.com
 * Time: 2016/3/9
 */

public class StoreMainActivity extends SlidingFragmentActivity {
    private TextView store;
    private TextView setting;
    private TextView partner;
    private TextView commodity;
    private TextView account;
    private TextView tv_open;
    private TextView tv_signature;
    private ImageView iv_heardpicture;

    private RadioButton bt1;
    private RadioButton bt2;
    private RadioButton bt3;
    private ImageView order;
    private SharedPreferences mPref;
    private String bianh;
    private String picture;
    String path = FinalData.FUWU_PATH;
    ImageOptions imageOptions1;
    private StoreBean storedata;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.storeactivity_main);
        mPref = getSharedPreferences("config", MODE_PRIVATE);
        SharedPreferences sp = getSharedPreferences("haha", Context.MODE_PRIVATE);
        bianh = sp.getString("storesnumberss", "");
        picture = sp.getString("mimage", "");
        initMainView();

        initSlidingMenu();
        getStoreData();
        initButtonOnclick();
        boolean open = mPref.getBoolean("check", true);
        if (open) {
            tv_open.setText("营业中");
        } else {
            tv_open.setText("暂停营业");
        }

    }

    private void getStoreData() {
        RequestParams storeParams = new RequestParams(path + "/StoreServlet?Storenumber=" + bianh);

        x.http().get(storeParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                imageOptions1 = new ImageOptions.Builder()
                        .setImageScaleType(ImageView.ScaleType.FIT_XY)
                        .setRadius(DensityUtil.dip2px(5))
                        .setLoadingDrawableId(R.mipmap.loge)
                        .setPlaceholderScaleType(ImageView.ScaleType.FIT_XY)
                        .setFailureDrawableId(R.mipmap.loge)
                        .build();
                Gson gs = new Gson();
                storedata = gs.fromJson(result, StoreBean.class);
                if (picture.isEmpty()) {
                    x.image().bind(iv_heardpicture,storedata.getImages(),imageOptions1);

                } else {
                    x.image().bind(iv_heardpicture,picture,imageOptions1);

                }



                        tv_signature.setText(storedata.getName());
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


    private void initButtonOnclick() {
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                NewOrder newOrder = new NewOrder();
                Bundle bundle1 = new Bundle();
                bundle1.putString("zt", "weichuli");
                newOrder.setArguments(bundle1);
                ft.replace(R.id.fl_content, newOrder);
                ft.commit();
                //                setButton(v);
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                UntreatedOrder untreatedOrder = new UntreatedOrder();
                Bundle bundle1 = new Bundle();
                bundle1.putString("zt", "yichuli");

                untreatedOrder.setArguments(bundle1);
                ft.replace(R.id.fl_content, untreatedOrder);
                ft.commit();
                //                setButton(v);
            }
        });

        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                HandledOrder handledOrder = new HandledOrder();
                Bundle bundle1 = new Bundle();
                bundle1.putString("zt", "quanbu");
                handledOrder.setArguments(bundle1);
                ft.replace(R.id.fl_content, handledOrder);
                ft.commit();
                //                setButton(v);
            }
        });
        bt1.performClick();
    }

    private void initMainView() {
        bt1 = (RadioButton) findViewById(R.id.bt1);
        bt2 = (RadioButton) findViewById(R.id.bt2);
        bt3 = (RadioButton) findViewById(R.id.bt3);
        order = (ImageView) findViewById(R.id.order);
        //        TextView textView = new TextView(this);
    }

    private void initSlidingView() {
        iv_heardpicture = (ImageView) findViewById(R.id.iv_heardpicture);
        tv_signature = (TextView)findViewById(R.id.tv_signature);
        store = (TextView) findViewById(R.id.store);
        account = (TextView) findViewById(R.id.account);
        setting = (TextView) findViewById(R.id.setting);
        partner = (TextView) findViewById(R.id.ourpartner);
        commodity = (TextView) findViewById(R.id.commodity);
        tv_open = (TextView) findViewById(R.id.tv_open);
    }


    private void initSlidingMenu() {

        setBehindContentView(R.layout.left_menu);//设置侧边栏
        SlidingMenu slidingMenu = getSlidingMenu();//获取侧滑菜单对象
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);//设置全屏触摸
        slidingMenu.setMode(SlidingMenu.LEFT);//设置侧滑的左右
        slidingMenu.setBehindOffset(250);//设置侧滑剩余的空间
        initSlidingView();
        initSlidingOnClick();
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggle();
            }
        });


    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            Intent intent = new Intent();
            intent.setClass(StoreMainActivity.this, MainActivity.class);
            intent.putExtra("id", 1);
            startActivity(intent);
            StoreMainActivity.this.finish();

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void initSlidingOnClick() {

        store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //                Toast.makeText(MainActivity.this, "点击成功", Toast.LENGTH_SHORT)
                // .show();

                Intent intent = new Intent();
                intent.setClass(StoreMainActivity.this, Store.class);
                startActivityForResult(intent, 1);

            }
        });

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //                Toast.makeText(MainActivity.this, "点击成功", Toast.LENGTH_SHORT)
                // .show();

                Intent intent = new Intent();
                intent.setClass(StoreMainActivity.this, Account.class);
                startActivityForResult(intent, 2);

            }
        });
        commodity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(StoreMainActivity.this, Commodity.class);
                startActivityForResult(intent, 3);
                //                Toast.makeText(MainActivity.this, "点击成功", Toast.LENGTH_SHORT)
                // .show();
            }
        });
        partner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //                Toast.makeText(MainActivity.this, "点击成功", Toast.LENGTH_SHORT)
                // .show();


                Intent intent = new Intent();
                intent.setClass(StoreMainActivity.this, OurParter.class);
                startActivityForResult(intent, 4);
            }
        });
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //                Toast.makeText(MainActivity.this, "点击成功", Toast.LENGTH_SHORT)
                // .show();


                Intent intent = new Intent();
                intent.setClass(StoreMainActivity.this, Setting.class);
                startActivityForResult(intent, 5);
            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case 1:
                //               toggle();
                //               Toast.makeText(MainActivity.this, "回调了", Toast.LENGTH_SHORT)
                // .show();
                break;
            case 2:

                break;
            case 3:
                //               toggle();
                break;
            case 4:
                //               toggle();
                boolean open = mPref.getBoolean("check", true);
                if (open) {
                    tv_open.setText("营业中");
                } else {
                    tv_open.setText("暂停营业");
                }
                break;
            case 5:
                //               toggle();
                break;

        }


    }


}


