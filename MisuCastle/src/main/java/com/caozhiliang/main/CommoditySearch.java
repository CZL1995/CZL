package com.caozhiliang.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.viewpagerindicator.TabPageIndicator;

/**
 * @author CZL
 * @time 2016-04-06 22:19
 */
public class CommoditySearch extends Fragment {
    private View view;
    private ViewPager mViewPager;
    private TabPageIndicator mIndicator;
    private static final String[] CONTENT = new String[]{"价格", "人气", "销量", "距离"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        initview();
        return view;
    }

    private void initview() {
        view = View.inflate(getContext(), R.layout.commodity_search, null);
        mViewPager = (ViewPager) view.findViewById(R.id.pager);
        mIndicator = (TabPageIndicator) view.findViewById(R.id.indicator);
        mViewPager.setAdapter(new CommodityAdapter());
        mIndicator.setViewPager(mViewPager);

    }

    class CommodityAdapter extends PagerAdapter {
        @Override
        public CharSequence getPageTitle(int position) {
            return CONTENT[position ];
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
            TextView textView =new TextView(getContext());
            textView.setText(CONTENT[position]);
            container.addView(textView);
            return textView;
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }


}
