package com.caozhiliang.location;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.caozhiliang.main.R;

import java.util.List;

/**
 * @author CZL
 * @time 2016-03-20 15:45
 */
public class Location extends Fragment {
    private View view;
    MapView mapView = null;
    private BaiduMap baiduMap;
    MyBaiduMapReceiver baiduMapReceiver;
    private LocationMode mCurrentMode;
    BitmapDescriptor mCurrentMarker;
    Button requestLocButton1;
    private double userLongitude;// 纬度
    private double userLatitude;// 经度
    // 覆盖物相关
    private BitmapDescriptor mMarker;
    private RelativeLayout mMarkerLy;

    ImageView iv;
    TextView distance;
    TextView name;
    TextView zan;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        initMapManager();

        view = View.inflate(getContext(), R.layout.location, null);
        requestLocButton1 = (Button) view.findViewById(R.id.button1);
        mapView = (MapView) view.findViewById(R.id.bmapView);
        init();
        mCurrentMode = LocationMode.NORMAL;
        requestLocButton1.setText("普通地图");
        mCurrentMarker = BitmapDescriptorFactory
                .fromResource(R.mipmap.eat_icon);

        addOverlays(Info.infos);


        View.OnClickListener btnClickListener = new View.OnClickListener() {
            public void onClick(View v) {
                switch (mCurrentMode) {
                    case NORMAL:
                        requestLocButton1.setText("卫星地图");


                        mCurrentMode = LocationMode.COMPASS;


                        baiduMap
                                .setMyLocationConfigeration(new MyLocationConfiguration(
                                        mCurrentMode, true, mCurrentMarker));
                        baiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
                        break;
                    case COMPASS:
                        requestLocButton1.setText("普通地图");
                        mCurrentMode = LocationMode.NORMAL;
                        baiduMap
                                .setMyLocationConfigeration(new MyLocationConfiguration(
                                        mCurrentMode, true, mCurrentMarker));
                        baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);

                        break;
                    default:
                        break;
                }
            }
        };
        requestLocButton1.setOnClickListener(btnClickListener);
        baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Bundle extraInfo = marker.getExtraInfo();
                Info info = (Info) extraInfo.getSerializable("info");

                iv.setImageResource(info.getImgId());
                distance.setText(info.getDistance());
                name.setText(info.getName());
                zan.setText(info.getZan() + "");

                InfoWindow infoWindow;
                TextView tv = new TextView(getContext());
                tv.setBackgroundResource(R.drawable.location_tips);
                tv.setPadding(30, 20, 30, 50);
                tv.setText(info.getName());
                tv.setTextColor(Color.parseColor("#ffffff"));

                final LatLng latLng = marker.getPosition();
                Point p = baiduMap.getProjection().toScreenLocation(latLng);
                p.y -= 47;
                LatLng ll = baiduMap.getProjection().fromScreenLocation(p);

                //                infoWindow = new InfoWindow(tv, ll,
                //                        new InfoWindow.OnInfoWindowClickListener()
                //                        {
                //                            @Override
                //                            public void onInfoWindowClick()
                //                            {
                //                                baiduMap.hideInfoWindow();
                //                            }
                //                        });
                //                baiduMap.showInfoWindow(infoWindow);
                mMarkerLy.setVisibility(View.VISIBLE);
                return true;
            }
        });
        baiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {

            @Override
            public boolean onMapPoiClick(MapPoi arg0) {
                return false;
            }

            @Override
            public void onMapClick(LatLng arg0) {
                mMarkerLy.setVisibility(View.GONE);
                baiduMap.hideInfoWindow();
            }
        });


        LocationData();


        return view;

    }

    /**
     * 添加覆盖物
     *
     * @param infos
     */
    private void addOverlays(List<Info> infos) {
        //        baiduMap.clear();
        LatLng latLng = null;
        Marker marker = null;
        OverlayOptions options;
        for (Info info : infos) {
            // 经纬度
            latLng = new LatLng(info.getLatitude(), info.getLongitude());
            // 图标
            options = new MarkerOptions().position(latLng).icon(mMarker)
                    .zIndex(5);
            marker = (Marker) baiduMap.addOverlay(options);
            Bundle arg0 = new Bundle();
            arg0.putSerializable("info", info);
            marker.setExtraInfo(arg0);
        }

        MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
        baiduMap.setMapStatus(msu);

    }


    private void init() {
        baiduMap = mapView.getMap();
        //设置缩放级别
        mMarker = BitmapDescriptorFactory.fromResource(R.mipmap.maker);
        mMarkerLy = (RelativeLayout) view.findViewById(R.id.id_maker_ly);

        iv = (ImageView) mMarkerLy
                .findViewById(R.id.id_info_img);
        distance = (TextView) mMarkerLy
                .findViewById(R.id.id_info_distance);
        name = (TextView) mMarkerLy
                .findViewById(R.id.id_info_name);
        zan = (TextView) mMarkerLy
                .findViewById(R.id.id_info_zan);

        MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.zoomTo(15);
        baiduMap.setMapStatus(mapStatusUpdate);
        //        baiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
        mapView.showZoomControls(false);
        mapView.showScaleControl(false);


    }

    boolean isFirstLoc = true; // 是否首次定位
    LocationClient mLocClient;
    public MyLocationListenner myListener = new MyLocationListenner();

    private void LocationData() {

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
                userLatitude = bdLocation.getLatitude();
                userLongitude = bdLocation.getLongitude();

                System.out.println(userLatitude);
                System.out.println(userLongitude);

                LatLng ll = new LatLng(userLatitude,
                        userLongitude);


                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll);
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
        // 关闭定位图层
        baiduMap.setMyLocationEnabled(false);
        mapView.onDestroy();
        mapView = null;
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
