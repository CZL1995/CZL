package com.caozhiliang.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.caozhiliang.fragment.OrderFragment;
import com.viewpagerindicator.TabPageIndicator;


/**
 * @author CZL
 * @time 2016-04-20 13:49
 */
public class MyOrder extends FragmentActivity {
    private ViewPager mViewPager;
    private TabPageIndicator mIndicator;
    private TextView tv_order;


    private static final String[] CONTENT = new String[]{"全部", "待评价", "待付款", "待收货"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context ctxWithTheme = new ContextThemeWrapper(
                getApplication(),
                R.style.Theme_PageIndicatorDefaults);
        //通过生成的Context创建一个LayoutInflater
        LayoutInflater localLayoutInflater = getLayoutInflater().cloneInContext(ctxWithTheme);

        //使用生成的LayoutInflater创建View
        ViewGroup rootView = (ViewGroup) localLayoutInflater.inflate(
                R.layout.myorder, null);

        setContentView(rootView);
        tv_order = (TextView) findViewById(R.id.tv_order);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        tv_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), MainActivity.class);
                intent.putExtra("id", 1);
                startActivity(intent);
                MyOrder.this.finish();
            }
        });
        mViewPager.setAdapter(new CommodityAdapter(getSupportFragmentManager()));
        mIndicator = (TabPageIndicator) findViewById(R.id.indicator);
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
                    fragment = new OrderFragment();
                    bundle = new Bundle();
                    bundle.putString("arg", "quanbu");
                    fragment.setArguments(bundle);
                    break;
                case 1:
                    fragment = new OrderFragment();
                    bundle = new Bundle();
                    bundle.putString("arg", "pinjia");
                    fragment.setArguments(bundle);
                    break;
                case 2:
                    fragment = new OrderFragment();
                    bundle = new Bundle();
                    bundle.putString("arg", "fukuan");
                    fragment.setArguments(bundle);
                    break;
                case 3:
                    fragment = new OrderFragment();
                    bundle = new Bundle();
                    bundle.putString("arg", "shouhuo");
                    fragment.setArguments(bundle);
                    break;
            }
            return fragment;
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            Intent intent = new Intent();
            intent.setClass(getApplicationContext(), MainActivity.class);
            intent.putExtra("id", 1);
            startActivity(intent);
            MyOrder.this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
