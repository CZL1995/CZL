package com.caozhiliang.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.xutils.x;

/**
 * author CZL.
 * time 2016/3/21
 */
public class BaseFragment extends Fragment {
    private boolean injected = false;
    public final String URL="http://xingxinga123.imwork.net:14553/Fuwu1";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        injected = true;

        return x.view().inject(this, inflater, container);
    }

    @Override
    public void onViewCreated(View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(!injected) {
            x.view().inject(this,this.getView());
        }
    }
}
