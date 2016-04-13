package com.caozhiliang.fragment;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
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
    private RatingBar store_ratingbar;
    private ImageView imageView;
    int id;
    ImageOptions imageOptions1;
    listadapte mlistadapte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_details);
        getStorePictureData();

        initview();
        id = getIntent().getIntExtra("id", 0);
        System.out.println(id);

        getStoreData();
        getTradeData();
    }


    private void initview() {
        lv_store_details = (ListView) findViewById(R.id.lv_store_details);
        headview = View.inflate(getApplicationContext(), R.layout.store_details_heardview, null);
        iv_homepage_viewpager = (ViewPager) headview.findViewById(R.id.iv_homepage_viewpager);
        iv_homepage_viewpager.setAdapter(new toViewpager());

        tv_name = (TextView) headview.findViewById(R.id.tv_name);
        tv_rank = (TextView) headview.findViewById(R.id.tv_rank);
        store_ratingbar = (RatingBar) headview.findViewById(R.id.ratingbar_Small);
        lv_store_details.addHeaderView(headview);
        footview = View.inflate(getApplicationContext(), R.layout.store_details_footview, null);
        lv_store_details.addFooterView(footview);
        tv2 = (TextView) footview.findViewById(R.id.tv2);
        tv3 = (TextView) footview.findViewById(R.id.tv3);
        tv4 = (TextView) footview.findViewById(R.id.tv4);


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
                System.out.println(imagedata);
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
            x.image().bind(holder.iv_image, tradedata.get(position).getImages(), imageOptions1);
            return convertView;
        }
    }

    static class ViewHolder {

        public TextView tv_prices2;
        public TextView tv_prices1;
        public TextView tv_name;
        public ImageView iv_image;
        public ImageView iv_detetails;
    }
    class toViewpager extends PagerAdapter {


        @Override
        public int getCount() {
            return imagedata.size();
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
            x.image().bind(imageView, imagedata.get(position).getImageaddress(),
                    imageOptions1);
            container.addView(imageView);
            return imageView;
        }
    }



}
