package com.caozhiliang.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;

import com.caozhiliang.main.R;

import org.xutils.x;

/**
 * @author CZL
 * @time 2016-03-25 10:02
 */
public class RefreshListView extends ListView {
    private View mHeaderView;

    public RefreshListView(Context context) {
        super(context);
        adView();
    }


    public RefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        adView();

    }



    public RefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        adView();

    }
    public void adView(){
        mHeaderView = View.inflate(getContext(), R.layout.refresh_headview, null);
        x.view().inject(getContext(),mHeaderView);
        this.addHeaderView(mHeaderView);
        mHeaderView.measure(0, 0);
        int mHeaderViewHeight = mHeaderView.getMeasuredHeight();
        System.out.println(mHeaderViewHeight);
        mHeaderView.setPadding(0, -mHeaderViewHeight+220, 0, 0);


    }

}
