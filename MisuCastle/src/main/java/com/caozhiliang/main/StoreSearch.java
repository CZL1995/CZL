package com.caozhiliang.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author CZL
 * @time 2016-04-06 22:19
 */
public class StoreSearch extends Fragment{
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        view=inflater.inflate(R.layout.store_search,null);

        return view;
    }
}
