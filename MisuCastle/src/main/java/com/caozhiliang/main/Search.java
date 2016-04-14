package com.caozhiliang.main;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.caozhiliang.base.BaseFragment;

/**
 * @author CZL
 * @time 2016-03-20 15:45
 */
public class Search extends BaseFragment {

    private View view;
    private RadioGroup rg_search;
    private RadioButton bt1;
    private RadioButton bt2;
    int st;
    Bundle mBundle1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        view = inflater.inflate(R.layout.search, null);

        initview();
        init();


        return view;
    }

    private void initview() {

        view = View.inflate(getContext(), R.layout.search, null);
        rg_search = (RadioGroup) view.findViewById(R.id.rg_search);
        bt1 = (RadioButton) view.findViewById(R.id.bt1);
        bt2 = (RadioButton) view.findViewById(R.id.bt2);

    }


    private void init() {

        rg_search.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.bt1:
                        FragmentManager fragmentmanger = getFragmentManager();
                        //                        拿到FragmentManger对象
                        //开始初始化Fragment
                        FragmentTransaction fragmentTS = fragmentmanger.beginTransaction();

                        StoreSearch store_search = new StoreSearch();
                        Bundle bundle1 = new Bundle();
                        store_search.setArguments(bundle1);
                        fragmentTS.replace(R.id.fl_search, store_search);
                        fragmentTS.commit();
                        break;
                    case R.id.bt2:
                        FragmentManager fragmentlocation = getFragmentManager();
                        //                        拿到FragmentManger对象
                        //开始初始化Fragment
                        FragmentTransaction fragmentTlocation = fragmentlocation
                                .beginTransaction();
                        CommoditySearch commodity_search = new CommoditySearch();
                        //                        Bundle bundle1 = new Bundle();
                        //                        bundle1.putInt("keyss", st);
                        //                        commodity_search.setArguments(bundle1);
                        fragmentTlocation.replace(R.id.fl_search, commodity_search);
                        fragmentTlocation.commit();
                        break;


                }


            }
        });
        bt1.performClick();

    }


}
