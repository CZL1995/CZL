package com.caozhiliang.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author CZL
 * @time 2016-04-20 8:52
 */
public class DIY extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view=View.inflate(getContext(),R.layout.diy,null);
        return view;
    }
}
