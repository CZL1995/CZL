package com.caozhiliang.main;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.caozhiliang.base.BaseFragment;
import com.caozhiliang.fragment.MainFragmen;
import com.caozhiliang.httpdata.TradeBean;
import com.caozhiliang.view.RefreshListView;
import com.viewpagerindicator.TabPageIndicator;

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
    private RefreshListView listview;
    private View mview;
    private String pos;

    private static final String[] CONTENT = new String[]{"价格", "人气", "销量", "距离"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        //使用ContextThemeWrapper通过目标Theme生成一个新的Context
        Context ctxWithTheme = new ContextThemeWrapper(
                getActivity().getApplicationContext(),
                R.style.Theme_PageIndicatorDefaults);
        //通过生成的Context创建一个LayoutInflater
        LayoutInflater localLayoutInflater = inflater.cloneInContext(ctxWithTheme);

        //使用生成的LayoutInflater创建View
        ViewGroup rootView = (ViewGroup) localLayoutInflater.inflate(
                R.layout.commodity_search, null);
        mViewPager = (ViewPager) rootView.findViewById(R.id.pager);
        mViewPager.setAdapter(new CommodityAdapter(getChildFragmentManager()));
        mIndicator = (TabPageIndicator) rootView.findViewById(R.id.indicator);
        mIndicator.setViewPager(mViewPager);

        mIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int
                    positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        return rootView;
    }



    class CommodityAdapter extends FragmentPagerAdapter {

        public CommodityAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return CONTENT[position % CONTENT.length];
        }

        @Override
        public int getCount() {
            return CONTENT.length;
        }

        @Override
        public Fragment getItem(int position) {

            Fragment fragment = null;
            Bundle bundle = null;
            switch (position) {
                case 0:
                    fragment = new MainFragmen();
                    bundle = new Bundle();
                    bundle.putInt("arg", 0);
                    fragment.setArguments(bundle);
                    break;
                case 1:
                    fragment = new MainFragmen();
                    bundle = new Bundle();
                    bundle.putInt("arg", 1);
                    fragment.setArguments(bundle);
                    break;
                case 2:
                    fragment = new MainFragmen();
                    bundle = new Bundle();
                    bundle.putInt("arg", 2);
                    fragment.setArguments(bundle);
                    break;
                case 3:
                    fragment = new MainFragmen();
                    bundle = new Bundle();
                    bundle.putInt("arg", 3);
                    fragment.setArguments(bundle);
                    break;
            }
            return fragment;
        }
    }




}
