package com.caozhiliang.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.caozhiliang.base.BaseActivity;
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

import java.util.List;

/**
 * @author CZL
 * @time 2016-04-11 23:25
 */


public class StoreDetails extends BaseActivity {
    private ListView lv_store_details;
    private View headview;
    private View footview;
    private StoreBean storedata;
    private List<TradeBean> tradedata;
    private List<ImageData> imagedata;
    private ViewPager iv_homepage_viewpager;
    private TextView tv_name;
    private TextView tv_rank;
    private TextView tv_brief;
    private TextView tv2;
    private TextView tv3;
    private TextView tv4;
    private TextView phonenumber;
    private TextView tv_dianzan;
    private TextView location;
    private RatingBar store_ratingbar;
    private ImageView iv_dianzan;
    private ImageView iv_back;
    private ImageView imageView;
    private ImageView toleft;
    private ImageView iv_phone;
    int id;
    int length;
    int po;
    Bundle mBundle;
    ImageOptions imageOptions1;
    listadapte mlistadapte;
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
        setContentView(R.layout.store_details);

        initview();
        id = getIntent().getIntExtra("id", 0);
        getStorePictureData();
        getStoreData();
        getTradeData();
        initlistener();
    }

    private void initlistener() {
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StoreDetails.this.finish();
            }
        });


        lv_store_details.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position<(tradedata.size()+1)) {
                    Intent intent = new Intent();
                    intent.setClass(getApplicationContext(), TradeDetails.class);
                    intent.putExtra("trade", tradedata.get(position - 1).getNumber());
                    System.out.println(tradedata.get(position - 1).getNumber());
                    startActivity(intent);
                }

            }
        });
        iv_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:" + storedata.getPhone());
                intent.setData(data);

                if (ActivityCompat.checkSelfPermission(StoreDetails.this, Manifest.permission
                        .CALL_PHONE) !=
                        PackageManager.PERMISSION_GRANTED) {

                    return;
                }
                startActivity(intent);


            }
        });

    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            StoreDetails.this.finish();

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    private void initview() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        lv_store_details = (ListView) findViewById(R.id.lv_store_details);
        headview = View.inflate(getApplicationContext(), R.layout.store_details_heardview, null);
        iv_homepage_viewpager = (ViewPager) headview.findViewById(R.id.iv_homepage_viewpager);
        tv_name = (TextView) headview.findViewById(R.id.tv_name);
        phonenumber = (TextView)headview.findViewById(R.id.phonenumber);
        location = (TextView) headview.findViewById(R.id.location);
        tv_rank = (TextView) headview.findViewById(R.id.tv_rank);
        tv_dianzan = (TextView) headview.findViewById(R.id.tv_dianzan);
        iv_phone = (ImageView) headview.findViewById(R.id.iv_phone);
        toleft = (ImageView) headview.findViewById(R.id.toleft);
        iv_dianzan = (ImageView) headview.findViewById(R.id.iv_dianzan);
        store_ratingbar = (RatingBar) headview.findViewById(R.id.ratingbar_Small);
        lv_store_details.addHeaderView(headview);
        footview = View.inflate(getApplicationContext(), R.layout.store_details_footview, null);
        //        footview.setEnabled(false);
        tv2 = (TextView) footview.findViewById(R.id.tv2);
        tv3 = (TextView) footview.findViewById(R.id.tv3);
        tv4 = (TextView) footview.findViewById(R.id.tv4);
        lv_store_details.addFooterView(footview);


    }


    private void getTradeData() {
        RequestParams tradeParams = new RequestParams(URL + "/TradeServlet?Storenumber=" + id);
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
        lv_store_details.setAdapter(mlistadapte);
    }


    private void getStoreData() {
        RequestParams storeParams = new RequestParams(URL + "/StoreServlet?Storenumber=" + id);

        x.http().get(storeParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                getstoreData(result);

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

    private void getStorePictureData() {
        RequestParams storeParams = new RequestParams(URL + "/ImageStoreServlet?Storenumber=" + id);

        x.http().get(storeParams, new Callback.CommonCallback<String>() {
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

    private void getstoreData(String result) {
        Gson gs = new Gson();
        storedata = gs.fromJson(result, StoreBean.class);
        System.out.println(storedata);
        location.setText(storedata.getAddress());
        tv_name.setText(storedata.getName());
        tv_rank.setText(storedata.getXingpj());
        tv_dianzan.setText(storedata.getXfrenshu());
        phonenumber.setText("联系电话："+storedata.getPhone());
        tv2.setText("营业时间：" + storedata.getTime());
        tv3.setText("门店服务：" + storedata.getFuwu());
        tv4.setText("门店介绍：" + storedata.getXiangq());

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
