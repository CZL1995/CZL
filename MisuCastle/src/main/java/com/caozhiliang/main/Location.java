package com.caozhiliang.main;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.baidu.location.f;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;


/**
 * @author CZL
 * @time 2016-03-20 15:45
 */
public class Location extends Fragment {
    private View view;
    MapView mapView = null;
    private BaiduMap baiduMap;
    MyBaiduMapReceiver baiduMapReceiver;
    private FrameLayout frameLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initMapManager();

        view = View.inflate(getContext(), R.layout.location, null);
        mapView = (MapView) view.findViewById(R.id.bmapView);
        init();

//        getWindow().setFormat(PixelFormat.TRANSLUCENT);

        return view;

    }

    private void init() {
        baiduMap=mapView.getMap();
        MapStatusUpdate mapStatusUpdate= MapStatusUpdateFactory.zoomTo(14);
        baiduMap.setMapStatus(mapStatusUpdate);
        mapView.showZoomControls(false);
        mapView.showScaleControl(false);
    }

    private void initMapManager() {
        SDKInitializer.initialize(getContext().getApplicationContext());
        baiduMapReceiver=new MyBaiduMapReceiver();
        IntentFilter filter=new IntentFilter();
        filter.addAction(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR);//注册网络错误
        filter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR);
        getContext().registerReceiver(baiduMapReceiver,filter);

    }



    class MyBaiduMapReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String result=intent.getAction();
            if(result.equals(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR)){
                Toast.makeText(getContext(), "网络错误", Toast.LENGTH_SHORT).show();

            } else if(
                    result.equals(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR)
                    ){

            }

        }
    }


    @Override
    public void onDestroy() {
        getContext().unregisterReceiver(baiduMapReceiver);
        mapView.onDestroy();
        super.onDestroy();

    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();

    }

    @Override
    public void onPause() {
        mapView.onPause();
        super.onPause();

    }


}
