package com.caozhiliang.main;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

/**
 * @author CZL
 * @time 2016-03-20 15:45
 */
public class Location extends Fragment {
    private View view;
    MapView mapView = null;
    private BaiduMap baiduMap;
    MyBaiduMapReceiver baiduMapReceiver;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        initMapManager();

        view = View.inflate(getContext(), R.layout.location, null);
        mapView = (MapView) view.findViewById(R.id.bmapView);
        init();

        LocationData();
        return view;

    }

    private void init() {
        baiduMap = mapView.getMap();
        //设置缩放级别
        MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.zoomTo(14);
        baiduMap.setMapStatus(mapStatusUpdate);
        mapView.showZoomControls(false);
        mapView.showScaleControl(false);

    }

    private LocationMode mCurrentMode;
    boolean isFirstLoc = true; // 是否首次定位
    LocationClient mLocClient;
    public MyLocationListenner myListener = new MyLocationListenner();

    private void LocationData() {
        mCurrentMode = LocationMode.NORMAL;
        //开启定位图层
        baiduMap.setMyLocationEnabled(true);
        //定位初始化
        mLocClient = new LocationClient(getContext());
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);
        option.setCoorType("bd0911");
        option.setScanSpan(1000);
        mLocClient.setLocOption(option);
        mLocClient.start();
    }

    public class MyLocationListenner implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if (bdLocation == null || mapView == null) {
                return;
            }
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(bdLocation.getRadius())
                            // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(bdLocation.getLatitude())
                    .longitude(bdLocation.getLongitude()).build();
            baiduMap.setMyLocationData(locData);
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(bdLocation.getLatitude(),
                        bdLocation.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(18.0f);
                baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }

        }

    }


    private void initMapManager() {
        SDKInitializer.initialize(getContext().getApplicationContext());
        baiduMapReceiver = new MyBaiduMapReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR);//注册网络错误
        filter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR);
        getContext().registerReceiver(baiduMapReceiver, filter);

    }


    class MyBaiduMapReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String result = intent.getAction();
            if (result.equals(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR)) {
                Toast.makeText(getContext(), "网络错误", Toast.LENGTH_SHORT).show();

            } else if (
                    result.equals(SDKInitializer
                            .SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR)
                    ) {

            }

        }
    }


    @Override
    public void onDestroy() {
        getContext().unregisterReceiver(baiduMapReceiver);
        mLocClient.stop();
        baiduMap.setMyLocationEnabled(false);


        mapView.onDestroy();
        mapView = null;

        super.onDestroy();

    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();

    }


}
