package com.caozhiliang.main;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
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
import com.caozhiliang.httpdata.HomeViewpagerData.DataEntity.ListviewsEntity;
import com.caozhiliang.httpdata.HomeViewpagerData.DataEntity.PicturesEntity;
import com.caozhiliang.httpdata.HomeViewpagerData.DataEntity.ViewpagersEntity;
import com.caozhiliang.view.RefreshListView;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.common.util.DensityUtil;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.List;

/**
 * @author CZL
 * @time 2016-03-20 15:45
 */
//@ContentView(R.layout.home_page)
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

    //    @ViewInject(R.id.listview)
    private RefreshListView listview;

    //    HomeViewpagerData homedata = new HomeViewpagerData();
    //    JsonData a = new JsonData();
    HomeViewpagerData homedata = new HomeViewpagerData();
    ImageOptions imageOptions;
    ImageOptions imageOptions1;
    View heardview;
    String datarusult;
    private String mMoreUrl;//跟多页面地址

    public List<ViewpagersEntity> mviewpagers;
    public List<PicturesEntity> mpictures;
    public List<ListviewsEntity> mlistviews;
    private listviewadpter mlistadapter;

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
    private Context context;
    private View neworderview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        //    头布局加入listview


        getServiceData();

        initview();


        return neworderview;
    }

    private void initview() {
        context = getContext();
        neworderview = View.inflate(getContext(), R.layout.home_page, null);
        heardview = View.inflate(getContext(), R.layout.list_heardview,
                null);
        listview = (RefreshListView) neworderview.findViewById(R.id.listview);
        listview.addHeaderView(heardview);

        iv_homepage_viewpager = (ViewPager) heardview.findViewById(R.id.iv_homepage_viewpager);
        tv_intro = (TextView) heardview.findViewById(R.id.tv_intro);
        dot_layout = (LinearLayout) heardview.findViewById(R.id.dot_layout);
        imageview1 = (ImageView) heardview.findViewById(R.id.imageview1);
        imageview2 = (ImageView) heardview.findViewById(R.id.imageview2);
        imageview3 = (ImageView) heardview.findViewById(R.id.imageview3);
        iv_homepage_viewpager.setAdapter(new toViewpager());

        initDots();
        handler.sendEmptyMessageDelayed(0, 2000);
        listview.setOnRefreshListener(new RefreshListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getServiceData();
                pictureview();
            }

            @Override
            public void onLoadMore() {
                if (mMoreUrl != null) {
                    getMoreServiceData();
                } else {
                    Toast.makeText(getContext(), "最后一页了", Toast.LENGTH_SHORT).show();
                    listview.onRefreshComplete(false);

                }
            }
        });


    }


    private void getServiceData() {

        RequestParams requestParams = new RequestParams(URL + "/HomeData.json");
        //        RequestParams requestParams = new RequestParams("http://xingxinga123.imwork" +
        //                ".net/Fuwu1/HomeServlet");

        x.http().get(requestParams, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                //                Toast.makeText(getContext(), result, Toast.LENGTH_SHORT).show();
                getData(result, false);
                listview.onRefreshComplete(true);
                pictureview();//多次使用，让其一直动


            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                listview.onRefreshComplete(false);
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });


    }

    private void getMoreServiceData() {

        RequestParams requestParams = new RequestParams(mMoreUrl);
        x.http().get(requestParams, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                //                Toast.makeText(getContext(), result, Toast.LENGTH_SHORT).show();
                getData(result, true);
                listview.onRefreshComplete(true);


            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                listview.onRefreshComplete(false);
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });


    }


    public void getData(String result, boolean isMore) {

        Gson gs = new Gson();

        homedata = gs.fromJson(result, HomeViewpagerData.class);
        //                a=gs.fromJson(result,JsonData.class);
      /*
      另一种解析的方式,

      List<HomeDataObject> home1;
        home1 = gs.fromJson(result, new TypeToken<List<HomeDataObject>>() {
        }.getType());
        HomeDataObject home11;
        home11=home1.get(0);//这里的到pan
        System.out.println(home11);

       List <HomeDataBean> list11;
        list11= home11.getDatas();//这里得到data
        System.out.println(list11);

        System.out.println(list11.get(0).getImageurl());
*/


        System.out.println(homedata);
        String more = homedata.data.more;
        if (!TextUtils.isEmpty(more)) {
            mMoreUrl = URL + more;
            System.out.println(mMoreUrl);
        } else {
            mMoreUrl = null;
        }

        if (!isMore) {
            mviewpagers = homedata.data.viewpagers;
            mlistviews = homedata.data.listviews;

            mpictures = homedata.data.pictures;

            pictureview();

            updateIntroAndDot();

            initListstener();

            mlistadapter = new listviewadpter();
            listview.setAdapter(mlistadapter);

        } else {//如果是加载下一页
            List<ListviewsEntity> mlistviews1 = homedata.data.listviews;
            mlistviews.addAll(mlistviews1);
            mlistadapter.notifyDataSetChanged();
        }


    }


    private void initDots() {
        for (int i = 0; i < 5; i++) {
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