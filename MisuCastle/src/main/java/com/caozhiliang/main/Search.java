package com.caozhiliang.main;

import android.os.Bundle;
import android.view.View;

import com.caozhiliang.base.BaseFragment;

import org.xutils.view.annotation.ContentView;

/**
 * @author CZL
 * @time 2016-03-20 15:45
 */
@ContentView(R.layout.search)
public class Search extends BaseFragment {
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        System.out.println("asddff");
    }
}
