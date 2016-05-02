package com.caozhiliang.mystore;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.caozhiliang.main.R;

/**
 * Created by ${CZL} on 2016/3/10.
 */
public class HandledOrder extends Fragment {

    private Context context;
    private View handorderview;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getActivity();
        handorderview = inflater.inflate(R.layout.handled_order, null);


        return handorderview;
    }
}
