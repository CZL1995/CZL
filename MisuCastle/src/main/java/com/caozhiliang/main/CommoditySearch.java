package com.caozhiliang.main;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.caozhiliang.base.BaseFragment;
import com.caozhiliang.httpdata.HomeDataObject;
import com.caozhiliang.httpdata.TradeBean;
import com.caozhiliang.view.RefreshListView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.viewpagerindicator.TabPageIndicator;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

/**
 * @author CZL
 * @time 2016-04-06 22:19
 */
public class CommoditySearch extends BaseFragment {
    private View view;
    private ViewPager mViewPager;
    private TabPageIndicator mIndicator;
    private List<TradeBean> list;
    HomeDataObject homedata = new HomeDataObject();
    TradeBean mTradeBean = new TradeBean();
    private RefreshListView listview;
    private listviewadpter mlistadapter;

    private static final String[] CONTENT = new String[]{"价格", "人气", "销量", "距离"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        //使用ContextThemeWrapper通过目标Theme生成一个新的Context

        getServerData();
        Context ctxWithTheme = new ContextThemeWrapper(
                getActivity().getApplicationContext(),
                R.style.Theme_PageIndicatorDefaults);

        //通过生成的Context创建一个LayoutInflater
        LayoutInflater localLayoutInflater = inflater.cloneInContext(ctxWithTheme);

        //使用生成的LayoutInflater创建View
        ViewGroup rootView = (ViewGroup) localLayoutInflater.inflate(
                R.layout.commodity_search, null, false);
        mViewPager = (ViewPager) rootView.findViewById(R.id.pager);
        mIndicator = (TabPageIndicator) rootView.findViewById(R.id.indicator);
        mViewPager.setAdapter(new CommodityAdapter());
        mIndicator.setViewPager(mViewPager);


        return rootView;
    }

    public void getServerData() {
        RequestParams requestParams = new RequestParams(URL + "/TabServlet?first=Trade&&second=renqi&&third=1");
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                //                System.out.println(result);
                Gson gs = new Gson();
                list = gs.fromJson(result, new TypeToken<List<TradeBean>>() {
                }.getType());
                System.out.println(list.get(1).toString());
                System.out.println(list.toString());
                System.out.println(list.size());
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                System.out.println("错误");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });


    }


    class CommodityAdapter extends PagerAdapter {
        @Override
        public CharSequence getPageTitle(int position) {
            return CONTENT[position];
        }

        @Override
        public int getCount() {
            return CONTENT.length;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View mview = View.inflate(getContext(), R.layout.tradelist, null);
            listview = (RefreshListView) mview.findViewById(R.id.trade_listview);
            mlistadapter = new listviewadpter();
            listview.setAdapter(mlistadapter);
            container.addView(mview);
            return mview;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }


    class listviewadpter extends BaseAdapter {
        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {


            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(getContext(), R.layout.trade, null);
                holder = new ViewHolder();
                holder.iv_image = (ImageView) convertView.findViewById(R.id.iv_image);
                holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
                holder.tv_rank = (TextView) convertView.findViewById(R.id.tv_rank);
                holder.tv_brief = (TextView) convertView.findViewById(R.id.tv_brief);
                holder.tv_location = (TextView) convertView.findViewById(R.id.tv_location);
                holder.tv_per = (TextView) convertView.findViewById(R.id.tv_per);
                holder.tv_distance = (TextView) convertView.findViewById(R.id.tv_distance);
                holder.room_ratingbar = (RatingBar) convertView.findViewById(R.id.room_ratingbar);




                holder.iv_image.setScaleType(ImageView.ScaleType.FIT_XY);




                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();

            }
            return convertView;
        }

        class ViewHolder {

            public TextView tv_name;
            public TextView tv_rank;
            public TextView tv_brief;
            public TextView tv_location;
            public TextView tv_per;
            public TextView tv_distance;
             public RatingBar room_ratingbar;
            public ImageView iv_image;


        }
    }


}
