package com.caozhiliang.main;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.caozhiliang.base.BaseFragment;
import com.caozhiliang.httpdata.HomeViewpagerData;
import com.caozhiliang.view.RefreshListView;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.common.util.DensityUtil;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * @author CZL
 * @time 2016-03-20 15:45
 */
@ContentView(R.layout.home_page)
public class HomePage extends BaseFragment {

    //    @ViewInject(R.id.iv_homepage_viewpager)
    private ViewPager iv_homepage_viewpager;

    //    @ViewInject(R.id.tv_intro)
    private TextView tv_intro;

    //    @ViewInject(R.id.dot_layout)
    private LinearLayout dot_layout;

    //    @ViewInject(R.id.imageview1)
    private ImageView imageview1;

    //    @ViewInject(R.id.imageview2)
    private ImageView imageview2;

    //    @ViewInject(R.id.imageview3)
    private ImageView imageview3;

    @ViewInject(R.id.listview)
    private RefreshListView listView;

    //    HomeViewpagerData homedata = new HomeViewpagerData();
    //    JsonData a = new JsonData();
    HomeViewpagerData homedata = new HomeViewpagerData();
    ImageOptions imageOptions;
    ImageOptions imageOptions1;
    View heardview;

    private List<HomeViewpagerData.DataEntity.ViewpagersEntity> mviewpagers;
    private List<HomeViewpagerData.DataEntity.PicturesEntity> mpictures;
    private List<HomeViewpagerData.DataEntity.ListviewsEntity> mlistviews;


    /**
     * 判断是否自动滚动
     */


    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {

            //让viewPager 滑动到下一页
            iv_homepage_viewpager.setCurrentItem(iv_homepage_viewpager.getCurrentItem() + 1);
            handler.sendEmptyMessageDelayed(0, 2000);

        }

        ;
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        //一头布局加入listview

        initview();

        getServiceData();


        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void initview() {

        heardview = View.inflate(getContext(), R.layout.list_heardview, null);
        x.view().inject(getContext(), heardview);



        iv_homepage_viewpager = (ViewPager) heardview.findViewById(R.id.iv_homepage_viewpager);
        tv_intro = (TextView) heardview.findViewById(R.id.tv_intro);
        dot_layout = (LinearLayout) heardview.findViewById(R.id.dot_layout);
        imageview1 = (ImageView) heardview.findViewById(R.id.imageview1);
        imageview2 = (ImageView) heardview.findViewById(R.id.imageview2);
        imageview3 = (ImageView) heardview.findViewById(R.id.imageview3);

    }


    private void getServiceData() {
        RequestParams requestParams = new RequestParams(URL + "/HomeData.json");
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                //                Toast.makeText(getContext(), result, Toast.LENGTH_SHORT).show();


                listView.addHeaderView(heardview);

                getData(result);
                pictureview();
                initListstener();

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


    private void getData(String result) {
        Gson gs = new Gson();
        homedata = gs.fromJson(result, HomeViewpagerData.class);
        //                a=gs.fromJson(result,JsonData.class);
        System.out.println(homedata);

        mviewpagers = homedata.data.viewpagers;
        mpictures = homedata.data.pictures;
        mlistviews = homedata.data.listviews;
        //        System.out.println(mpictures);
        //        System.out.println("asd");
        //        System.out.println(mviewpagers.get(0).getWenzi());

        listView.setAdapter(new listviewadpter());

        initDots();

        iv_homepage_viewpager.setAdapter(new toViewpager());


        updateIntroAndDot();


        handler.sendEmptyMessageDelayed(0, 4000);
    }

    private void initDots() {
        for (int i = 0; i < mviewpagers.size(); i++) {
            View view = new View(getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(15, 15);
            if (i != 0) {
                params.leftMargin = 10;
            }
            view.setLayoutParams(params);
            view.setBackgroundResource(R.drawable.selector_dot);
            dot_layout.addView(view);
        }
    }

    private void updateIntroAndDot() {
        int currentPage = iv_homepage_viewpager.getCurrentItem() % mviewpagers.size();
        tv_intro.setText(mviewpagers.get(currentPage).getWenzi());

        for (int i = 0; i < dot_layout.getChildCount(); i++) {
            dot_layout.getChildAt(i).setEnabled(i == currentPage);
        }
    }


    private void initListstener() {
        iv_homepage_viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int
                    positionOffsetPixels) {


            }

            @Override
            public void onPageSelected(int position) {
                updateIntroAndDot();

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    public void pictureview() {
        imageOptions = new ImageOptions.Builder()
                .setImageScaleType(ImageView.ScaleType.FIT_XY)
                .setRadius(DensityUtil.dip2px(5))
                .setLoadingDrawableId(R.mipmap.loge)
                .setPlaceholderScaleType(ImageView.ScaleType.MATRIX)
                .setFailureDrawableId(R.mipmap.loge)
                .build();
        x.image().bind(imageview1, mpictures.get(0).getImageurl(), imageOptions);
        x.image().bind(imageview2, mpictures.get(1).getImageurl(), imageOptions);
        x.image().bind(imageview3, mpictures.get(2).getImageurl(), imageOptions);

        ScaleAnimation scale = new ScaleAnimation((float) 0.8, 1, (float) 0.8,
                1, Animation
                .RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        scale.setDuration(2000);
        //        scale.setFillAfter(true);
        scale.setRepeatCount(Integer.MAX_VALUE);
        scale.setRepeatMode(Animation.REVERSE);
        imageview1.startAnimation(scale);
        imageview1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "dian", Toast.LENGTH_SHORT).show();
            }
        });


        imageview2.startAnimation(scale);

        imageview3.startAnimation(scale);

    }


    class listviewadpter extends BaseAdapter {
        @Override
        public int getCount() {
            return mlistviews.size();
        }

        @Override
        public Object getItem(int position) {
            return mlistviews.get(position);
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
                    .setPlaceholderScaleType(ImageView.ScaleType.MATRIX)
                    .setFailureDrawableId(R.mipmap.loge)
                    .build();


            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(getContext(), R.layout.homepagerlistviewadapter, null);
                holder = new ViewHolder();
                holder.ivPic = (ImageView) convertView.findViewById(R.id.iv);
                //                holder.tvDate = (TextView) convertView.findViewById(R.id.tv);
                holder.ivPic.setScaleType(ImageView.ScaleType.FIT_XY);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();

            }

            //            holder.tvDate.setText(mlistviews.get(position).getWenzi());
            x.image().bind(holder.ivPic, mlistviews.get(position).getImageurl(), imageOptions1);
            return convertView;
        }
    }

    static class ViewHolder {

        public TextView tvDate;
        public ImageView ivPic;
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
            ImageView imageView = new ImageView(getContext());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageResource(R.mipmap.loge);


            x.image().bind(imageView, mviewpagers.get(position % mviewpagers.size()).getImageurl(),
                    imageOptions);
            container.addView(imageView);
            return imageView;
        }
    }
}
