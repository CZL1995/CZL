package com.caozhiliang.mystore;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
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
import com.caozhiliang.fragment.Evaluate;
import com.caozhiliang.httpdata.FinalData;
import com.caozhiliang.httpdata.ImageData;
import com.caozhiliang.httpdata.StoreBean;
import com.caozhiliang.httpdata.TradeBean;
import com.caozhiliang.main.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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
    private EditText tv_name;
    private EditText tv3;
    private EditText tv4;
    private TextView tv2;
    private TextView tv_finish;
    private TextView tv_mydetailsas;
    private ImageView iv_store;
    private RatingBar room_ratingbar;
    private RadioButton store_details;
    private RadioButton store_evaluate;
    private RadioGroup rg_group;
    private List<ImageData> imagedata;
    int length;
    private TimePicker datePickerStart;
    private TimePicker datePickerEnd;
    private ViewPager viewPager;//页卡内容
    private List<View> views;// Tab页面列表
    private View view1, view2;//各个页卡
    private String bianh;
    private String phone;
    private String detail;
    private String starehour;
    private String endhour;
    private String stareminute;
    private String endminute;
    private String locationa;
    private String locationb;
    private ListView lv_store_evaluate;
    private List<TradeBean> tradedata;

    private String mname;
    private String mlocation;
    private String mphone;
    private String mtime;
    private String mfuwu;
    private String mdetail;
    private String picture;
    listadapte mlistadapte;
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
        picture = sp.getString("mimageaa", "");
        System.out.println("aaaaa" + picture);
        rg_group = (RadioGroup) findViewById(R.id.rg_group);
        store_details = (RadioButton) findViewById(R.id.store_details);
        store_evaluate = (RadioButton) findViewById(R.id.store_evaluate);
        getLocation();
        InitViewPager();
        initView();
        getStoreData();
        getStorePictureData();
        getTradeData();
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
        tv_name = (EditText) findViewById(R.id.tv_name);
        tv_rank = (TextView) findViewById(R.id.tv_rank);
        pia = (TextView) findViewById(R.id.pia);
        tv_finish = (TextView)findViewById(R.id.tv_finish);
        tv_location = (TextView) view1.findViewById(R.id.tv_location);
        ed_phone = (EditText) view1.findViewById(R.id.ed_phone);
        et_write = (EditText) view1.findViewById(R.id.et_write);
        tv3 = (EditText) view1.findViewById(R.id.tv3);
        tv2 = (TextView) view1.findViewById(R.id.tv2);
        tv_mydetailsas = (TextView) view1.findViewById(R.id.tv_mydetailsas);
        lv_store_evaluate = (ListView) view2.findViewById(R.id.lv_store_evaluate);
        room_ratingbar = (RatingBar) findViewById(R.id.room_ratingbar);
        iv_store = (ImageView) findViewById(R.id.iv_store);
        ed_phone.setEnabled(false);
        et_write.setEnabled(false);
        tv3.setEnabled(false);
        tv_name.setEnabled(false);
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

    private void getStorePictureData() {
        RequestParams storeParams = new RequestParams(URL + "/ImageStoreServlet?Storenumber=" +
                bianh);

        x.http().get(storeParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                Gson gs = new Gson();
                imagedata = gs.fromJson(result, new TypeToken<List<ImageData>>() {
                }.getType());
                length = imagedata.size();
                pia.setText(length + "张");
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            Intent intent = new Intent();
            intent.setClass(Store.this, StoreMainActivity.class);
            startActivity(intent);
            Store.this.finish();

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    private void initOnclick() {
        tv_store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Store.this, StoreMainActivity.class);
                startActivity(intent);
                Store.this.finish();
            }
        });
        pia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mContext, ImageMore.class);
                startActivity(intent);
                finish();
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
        tv_mydetailsas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ed_phone.setEnabled(true);
                et_write.setEnabled(true);
                ed_phone.setFocusable(true);
                et_write.setFocusable(true);
                tv3.setFocusable(true);
                tv3.setEnabled(true);
                tv_name.setEnabled(true);
                tv_name.setFocusable(true);
                tv_finish.setVisibility(View.VISIBLE);
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
                tv_finish.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ed_phone.setEnabled(false);
                        et_write.setEnabled(false);
                        tv3.setEnabled(false);
                        tv_name.setEnabled(false);


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
                                "/StoreServlet?pan=gai&&Storenumber=" + bianh +
                                "&&name=" + mname + "&&phone=" + mphone + "&&fuwu=" + mfuwu + "&&diqu=" +
                                locationb +
                                "&&time=" + mtime + "&&xiangq=" + mdetail +
                                "&&address=" + mlocation
                        );
                        x.http().get(requestParams, new Callback.CommonCallback<String>() {
                            @Override
                            public void onSuccess(String result) {
                                Toast.makeText(mContext, "修改成功", Toast.LENGTH_SHORT).show();
                                tv_finish.setVisibility(View.GONE);

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



                    }
                });
            }
        });
        lv_store_evaluate.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(Store.this, Evaluate.class);
                intent.putExtra("evaluatetradenumber", String.valueOf(tradedata.get(position)
                        .getNumber()));
                startActivity(intent);
            }
        });


    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//
//        if (ed_phone.isEnabled()) {
//            ed_phone.setEnabled(false);
//            et_write.setEnabled(false);
//            tv3.setEnabled(false);
//            tv_name.setEnabled(false);
//
//
//            mphone = ed_phone.getText().toString();
//            mdetail = et_write.getText().toString();
//            mname = tv_name.getText().toString();
//            mlocation = tv_location.getText().toString();
//            mtime = starehour + ":" + stareminute + "-"
//                    + endhour + ":" + endminute;
//            mfuwu = tv3.getText().toString();
//            try {
//                mphone = URLEncoder.encode(mphone, "UTF-8");
//                mphone = URLEncoder.encode(mphone, "UTF-8");
//                mdetail = URLEncoder.encode(mdetail, "UTF-8");
//                mdetail = URLEncoder.encode(mdetail, "UTF-8");
//                mname = URLEncoder.encode(mname, "UTF-8");
//                mname = URLEncoder.encode(mname, "UTF-8");
//                mlocation = URLEncoder.encode(mlocation, "UTF-8");
//                mlocation = URLEncoder.encode(mlocation, "UTF-8");
//                mtime = URLEncoder.encode(mtime, "UTF-8");
//                mtime = URLEncoder.encode(mtime, "UTF-8");
//                mfuwu = URLEncoder.encode(mfuwu, "UTF-8");
//                mfuwu = URLEncoder.encode(mfuwu, "UTF-8");
//                locationb = URLEncoder.encode(locationb, "UTF-8");
//                locationb = URLEncoder.encode(locationb, "UTF-8");
//            } catch (Exception e) {
//            }
//
//            RequestParams requestParams = new RequestParams(URL +
//                    "/StoreServlet?pan=gai&&Storenumber=" + bianh +
//                    "&&name=" + mname + "&&phone=" + mphone + "&&fuwu=" + mfuwu + "&&diqu=" +
//                    locationb +
//                    "&&time=" + mtime + "&&xiangq=" + mdetail +
//                    "&&address=" + mlocation
//            );
//            x.http().get(requestParams, new Callback.CommonCallback<String>() {
//                @Override
//                public void onSuccess(String result) {
//                    Toast.makeText(mContext, "修改成功", Toast.LENGTH_SHORT).show();
//                }
//
//                @Override
//                public void onError(Throwable ex, boolean isOnCallback) {
//                    Toast.makeText(mContext, "修改失败", Toast.LENGTH_SHORT).show();
//
//                }
//
//                @Override
//                public void onCancelled(CancelledException cex) {
//
//                }
//
//                @Override
//                public void onFinished() {
//
//                }
//            });
//
//
//        } else {
//            //            Toast.makeText(Store.this, "bbbb", Toast.LENGTH_SHORT).show();
//
//        }
//
//
//        return super.onTouchEvent(event);
//
//
//    }

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

    private void getTradeData() {
        RequestParams tradeParams = new RequestParams(URL + "/TradeServlet?Storenumber=" + bianh);
        x.http().get(tradeParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                gettradeData(result);

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                System.out.println("网址错误");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }

    private void gettradeData(String result) {
        Gson gs = new Gson();
        tradedata = gs.fromJson(result, new TypeToken<List<TradeBean>>() {
        }.getType());
        System.out.println(tradedata.get(0));
        mlistadapte = new listadapte();
        lv_store_evaluate.setAdapter(mlistadapte);
    }

    class listadapte extends BaseAdapter {
        @Override
        public int getCount() {
            return tradedata.size();
        }

        @Override
        public Object getItem(int position) {
            return tradedata.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            imageOptions1 = new ImageOptions.Builder()
                    .setImageScaleType(ImageView.ScaleType.FIT_XY)
                    .setRadius(DensityUtil.dip2px(5))
                    .setLoadingDrawableId(R.mipmap.loge)
                    .setPlaceholderScaleType(ImageView.ScaleType.FIT_XY)
                    .setFailureDrawableId(R.mipmap.loge)
                    .build();

            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(getApplication(), R.layout.trade_list_details, null);
                holder = new ViewHolder();
                holder.iv_image = (ImageView) convertView.findViewById(R.id.iv_image);
                holder.iv_detetails = (ImageView) convertView.findViewById(R.id.iv_detetails);
                holder.tv_prices2 = (TextView) convertView.findViewById(R.id.tv_prices2);
                holder.brief = (TextView) convertView.findViewById(R.id.brief);
                holder.tv_prices2.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                holder.tv_prices1 = (TextView) convertView.findViewById(R.id.tv_prices1);
                holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
                holder.iv_image.setScaleType(ImageView.ScaleType.FIT_XY);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();

            }
            holder.tv_prices2.setText(tradedata.get(position).getPrice2());
            holder.tv_prices1.setText(tradedata.get(position).getPrice1());
            holder.tv_name.setText(tradedata.get(position).getStorename());
            holder.brief.setText(tradedata.get(position).getJianjie());

            x.image().bind(holder.iv_image, tradedata.get(position).getImages(), imageOptions1);
            return convertView;
        }
    }

    static class ViewHolder {

        public TextView tv_prices2;
        public TextView tv_prices1;
        public TextView tv_name;
        public TextView brief;
        public ImageView iv_image;
        public ImageView iv_detetails;
    }
}
