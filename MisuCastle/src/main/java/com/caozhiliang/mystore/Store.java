package com.caozhiliang.mystore;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.caozhiliang.httpdata.FinalData;
import com.caozhiliang.httpdata.StoreBean;
import com.caozhiliang.main.R;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.common.util.DensityUtil;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Author:CZL
 * time:2016/3/11  8:31
 */
public class Store extends Activity {
    private TextView tv_store;
    private TextView tv_rank;
    private TextView pia;
    private EditText ed_phone;
    private EditText et_write;
    private TextView tv_location;
    private TextView tv_name;
    private EditText tv3;
    private EditText tv4;
    private EditText tv2;
    private ImageView iv_store;
    private RatingBar room_ratingbar;
    private RadioButton store_details;
    private RadioButton store_evaluate;
    private RadioGroup rg_group;

    private TimePicker datePickerStart;
    private TimePicker datePickerEnd;
    private ViewPager viewPager;//页卡内容
    private List<View> views;// Tab页面列表
    private View view1, view2;//各个页卡
    private Button cs;
    private String bianh;
    private String phone;
    private String detail;
    private String starehour;
    private String endhour;
    private String stareminute;
    private String endminute;
    private String locationa;
    private String locationb;


    private String mname;
    private String mlocation;
    private String mphone;
    private String mtime;
    private String mfuwu;
    private String mdetail;
    private String picture;

    String path = FinalData.FUWU_PATH;
    ImageOptions imageOptions1;
    private StoreBean storedata;
    LocationClient mLocClient;
    View view;
    public final String URL = "http://119.29.148.150:8080/Fuwu1";
    Context mContext;
    public MyLocationListenner myListener = new MyLocationListenner();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mystore);
        mContext = this;
        SharedPreferences sp = getSharedPreferences("haha", Context.MODE_PRIVATE);
        bianh = sp.getString("storesnumberss", "");
        picture = sp.getString("mimage", "");
        System.out.println("aaaaa"+picture);
        rg_group = (RadioGroup) findViewById(R.id.rg_group);
        store_details = (RadioButton) findViewById(R.id.store_details);
        store_evaluate = (RadioButton) findViewById(R.id.store_evaluate);
        getLocation();
        InitViewPager();
        initView();
        getStoreData();


        initOnclick();


    }

    private void getLocation() {
        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);
        option.setCoorType("bd0911");
        option.setScanSpan(5000);
        option.disableCache(true);
        option.setIsNeedAddress(true);
        option.setPriority(LocationClientOption.GpsFirst);
        mLocClient.setLocOption(option);
        mLocClient.start();
    }


    private class MyLocationListenner implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location == null)
                return;
            StringBuffer sb = new StringBuffer(256);
            StringBuffer sba = new StringBuffer(256);
            if (location.getLocType() == BDLocation.TypeGpsLocation) {
                //				sb.append(location.getAddrStr());
                sb.append(location.getAddrStr());
                sba.append(location.getDistrict());
            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
                sb.append(location.getAddrStr());
                sba.append(location.getDistrict());
            }
            if (sb.toString() != null && sb.toString().length() > 0) {
                locationa = sb.toString();
                tv_location.setText(locationa);
            }
            if (sba.toString() != null && sba.toString().length() > 0) {
                locationb = sba.toString();
            }


        }

    }

    private void initView() {
        tv_store = (TextView) findViewById(R.id.tv_store);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_rank = (TextView) findViewById(R.id.tv_rank);
        pia = (TextView) findViewById(R.id.pia);
        tv_location = (TextView) view1.findViewById(R.id.tv_location);
        ed_phone = (EditText) view1.findViewById(R.id.ed_phone);
        et_write = (EditText) view1.findViewById(R.id.et_write);
        tv3 = (EditText) view1.findViewById(R.id.tv3);
        tv2 = (EditText) view1.findViewById(R.id.tv2);
        room_ratingbar = (RatingBar) findViewById(R.id.room_ratingbar);
        iv_store = (ImageView) findViewById(R.id.iv_store);
        cs = (Button) view2.findViewById(R.id.cs);
        ed_phone.setEnabled(false);
        et_write.setEnabled(false);
        tv3.setEnabled(false);
        tv2.setEnabled(false);
        view = View.inflate(this, R.layout.timeselect, null);
        datePickerStart = (TimePicker) view.findViewById(R.id.datePickerStart);
        datePickerEnd = (TimePicker) view.findViewById(R.id.datePickerEnd);
    }

    private void getStoreData() {
        RequestParams storeParams = new RequestParams(path + "/StoreServlet?Storenumber=" + bianh);

        x.http().get(storeParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                imageOptions1 = new ImageOptions.Builder()
                        .setImageScaleType(ImageView.ScaleType.FIT_XY)
                        .setRadius(DensityUtil.dip2px(10))
                        .setLoadingDrawableId(R.mipmap.loge)
                        .setPlaceholderScaleType(ImageView.ScaleType.FIT_XY)
                        .setFailureDrawableId(R.mipmap.loge)
                        .build();
                Gson gs = new Gson();
                storedata = gs.fromJson(result, StoreBean.class);
                if (picture.isEmpty()) {
                    x.image().bind(iv_store, storedata.getImages(), imageOptions1);

                } else {
                    x.image().bind(iv_store, picture, imageOptions1);

                }
                tv_name.setText(storedata.getName());
                tv2.setText("营业时间：" + storedata.getTime());
                tv3.setText("门店服务：" + storedata.getFuwu());
                tv_rank.setText(storedata.getXingpj());
                room_ratingbar.setRating(Float.parseFloat(storedata.getXingpj()));
                et_write.setText("门店介绍：" + storedata.getXiangq());
                ed_phone.setText(storedata.getPhone());

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

    private void InitViewPager() {
        viewPager = (ViewPager) findViewById(R.id.vPager);
        views = new ArrayList<View>();
        LayoutInflater inflater = getLayoutInflater();
        view1 = inflater.inflate(R.layout.mystore_details, null);
        view2 = inflater.inflate(R.layout.store_evaluate, null);
        views.add(view1);
        views.add(view2);
        viewPager.setAdapter(new MyViewPagerAdapter(views));
        viewPager.setCurrentItem(0);
        store_details.setChecked(true);
        store_evaluate.setChecked(false);
        rg_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.store_details:
                        // mViewPager.setCurrentItem(0);// 设置当前页面
                        viewPager.setCurrentItem(0, false);// 去掉切换页面的动画
                        break;
                    case R.id.store_evaluate:
                        viewPager.setCurrentItem(1, false);// 设置当前页面
                        break;
                    default:
                        break;
                }
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int
                    positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    store_details.setChecked(true);
                    store_evaluate.setChecked(false);
                    System.out.println("第一个页面");
                } else {
                    store_evaluate.setChecked(true);
                    store_details.setChecked(false);
                    System.out.println("第二个页面");

                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    private void initOnclick() {
        tv_store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        cs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Store.this, "wwe", Toast.LENGTH_SHORT).show();
            }
        });
        iv_store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setClass(mContext, MyStore_touxiang.class);
                startActivity(intent);
                finish();
            }
        });
        tv_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ed_phone.setEnabled(true);
                et_write.setEnabled(true);
                ed_phone.setFocusable(true);
                et_write.setFocusable(true);
                tv3.setFocusable(true);
                tv3.setEnabled(true);
                tv2.setEnabled(true);
                tv2.setFocusable(true);

                tv2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        datePickerEnd.setOnTimeChangedListener(new TimePicker
                                .OnTimeChangedListener() {
                            @Override
                            public void onTimeChanged(TimePicker endview, int hourOfDay, int
                                    minute) {
                                endhour = String.valueOf(hourOfDay);
                                endminute = String.valueOf(minute);

                            }
                        });
                        datePickerStart.setOnTimeChangedListener(new TimePicker
                                .OnTimeChangedListener() {
                            @Override
                            public void onTimeChanged(TimePicker endview, int hourOfDay, int
                                    minute) {
                                starehour = String.valueOf(hourOfDay);
                                stareminute = String.valueOf(minute);
                            }
                        });
                        new AlertDialog.Builder(mContext)
                                .setView(view)
                                .setTitle("设置营业时间")
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        ((ViewGroup) view.getParent()).removeView(view);
                                    }
                                })
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        tv2.setText("营业时间" + starehour + ":" + stareminute + "-"
                                                + endhour + ":" + endminute);
                                        ((ViewGroup) view.getParent()).removeView(view);


                                    }
                                })
                                .show();

                    }
                });

            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (ed_phone.isEnabled()) {
            Toast.makeText(Store.this, "aaaa", Toast.LENGTH_SHORT).show();
            ed_phone.setEnabled(false);
            et_write.setEnabled(false);
            tv3.setEnabled(false);
            tv2.setEnabled(false);


            mphone = ed_phone.getText().toString();
            mdetail = et_write.getText().toString();
            mname = tv_name.getText().toString();
            mlocation = tv_location.getText().toString();
            mtime = starehour + ":" + stareminute + "-"
                    + endhour + ":" + endminute;
            mfuwu = tv3.getText().toString();
            try {
                mphone = URLEncoder.encode(mphone, "UTF-8");
                mphone = URLEncoder.encode(mphone, "UTF-8");
                mdetail = URLEncoder.encode(mdetail, "UTF-8");
                mdetail = URLEncoder.encode(mdetail, "UTF-8");
                mname = URLEncoder.encode(mname, "UTF-8");
                mname = URLEncoder.encode(mname, "UTF-8");
                mlocation = URLEncoder.encode(mlocation, "UTF-8");
                mlocation = URLEncoder.encode(mlocation, "UTF-8");
                mtime = URLEncoder.encode(mtime, "UTF-8");
                mtime = URLEncoder.encode(mtime, "UTF-8");
                mfuwu = URLEncoder.encode(mfuwu, "UTF-8");
                mfuwu = URLEncoder.encode(mfuwu, "UTF-8");
                locationb = URLEncoder.encode(locationb, "UTF-8");
                locationb = URLEncoder.encode(locationb, "UTF-8");
            } catch (Exception e) {
            }

            RequestParams requestParams = new RequestParams(URL +
                    "/StoreServlet?pan=gai&&storenumber" + bianh +
                    "&&name=" + mname + "&&phone=" + mphone + "&&fuwu=" + mfuwu + "&&diqu=" +
                    locationb +
                    "&&time=" + mtime + "&&xiangq=" + mdetail +
                    "&&address=" + mlocation
            );
            x.http().get(requestParams, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    Toast.makeText(mContext, "修改成功", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    Toast.makeText(mContext, "修改失败", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onCancelled(CancelledException cex) {

                }

                @Override
                public void onFinished() {

                }
            });


        } else {
            //            Toast.makeText(Store.this, "bbbb", Toast.LENGTH_SHORT).show();

        }


        return super.onTouchEvent(event);


    }

    public class MyViewPagerAdapter extends PagerAdapter {
        private List<View> mListViews;

        public MyViewPagerAdapter(List<View> mListViews) {
            this.mListViews = mListViews;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mListViews.get(position));
        }


        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mListViews.get(position), 0);
            return mListViews.get(position);
        }

        @Override
        public int getCount() {
            return mListViews.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }
    }


}
