package com.caozhiliang.mystore;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.caozhiliang.main.MainActivity;
import com.caozhiliang.main.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:CZL
 * time:2016/3/11  8:31
 */
public class Store extends Activity {
    private TextView tv_store;
    private RadioButton store_details;
    private RadioButton store_evaluate;
    private RadioGroup rg_group;


    private ViewPager viewPager;//页卡内容
    private List<View> views;// Tab页面列表
    private View view1, view2;//各个页卡
    private Button cs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mystore);
        rg_group = (RadioGroup) findViewById(R.id.rg_group);
        store_details = (RadioButton) findViewById(R.id.store_details);
        store_evaluate = (RadioButton) findViewById(R.id.store_evaluate);
        InitViewPager();
        initView();
        initOnclick();


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
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position==0){
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
                Intent intent = new Intent();
                intent.setClass(Store.this, MainActivity.class);
                setResult(1, intent);
                finish();
            }
        });


//        store_details.setOnClickListener(new MyOnClickListener(0));
//        store_evaluate.setOnClickListener(new MyOnClickListener(1));
        cs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Store.this, "wwe", Toast.LENGTH_SHORT).show();
            }
        });

    }


//    private class MyOnClickListener implements View.OnClickListener {
//        private int index = 0;
//
//        public MyOnClickListener(int i) {
//            index = i;
//
//
//        }
//
//        public void onClick(View v) {
//
//            viewPager.setCurrentItem(index);
//
//
//        }
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

    private void initView() {
        tv_store = (TextView) findViewById(R.id.tv_store);

        cs = (Button) view2.findViewById(R.id.cs);
    }


}
