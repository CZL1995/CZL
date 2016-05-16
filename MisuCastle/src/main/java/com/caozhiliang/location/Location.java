package com.caozhiliang.location;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
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
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.CityInfo;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.caozhiliang.main.R;
import com.caozhiliang.overlayutil.OverlayManager;
import com.caozhiliang.overlayutil.PoiOverlay;

import java.util.ArrayList;
import java.util.List;

/**
 * @author CZL
 * @time 2016-03-20 15:45
 */
public class Location extends Fragment implements OnGetPoiSearchResultListener {
    private View view;
    MapView mapView = null;
    private BaiduMap baiduMap;
    MyBaiduMapReceiver baiduMapReceiver;
    private LocationMode mCurrentMode;
    BitmapDescriptor mCurrentMarker;
    Button requestLocButton1;
    private double userLongitude;// 纬度
    private double userLatitude;// 经度
    private LatLng ll;
    List<PoiInfo> pois;
    List<OverlayOptions> ops;
    private RecyclerView id_recyclerview_horizontal;

    // 覆盖物相关
    private BitmapDescriptor mMarker;
    private RelativeLayout maplist;
    private PoiSearch mPoiSearch = null;
    private final static String PARKING = "蛋糕";
    private Button parking;
    private String mark = "";
    ImageView iv;
    TextView distance;
    TextView name;
    TextView zan;
    private RecyclerViews mAdapter;
    boolean isFirstLoc = true; // 是否首次定位
    LocationClient mLocClient;
    public MyLocationListenner myListener = new MyLocationListenner();

    public Location() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        initMapManager();

        mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(this);
        view = View.inflate(getContext(), R.layout.location, null);

        requestLocButton1 = (Button) view.findViewById(R.id.button1);
        id_recyclerview_horizontal = (RecyclerView) view.findViewById(R.id.id_recyclerview_horizontal);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        id_recyclerview_horizontal.setLayoutManager(linearLayoutManager);
        parking = (Button) view.findViewById(R.id.parking);
        mapView = (MapView) view.findViewById(R.id.bmapView);
        init();


        mCurrentMode = LocationMode.NORMAL;
        requestLocButton1.setText("普通地图");
        LocationData();
        Overlays();
        setListener();
        return view;
    }

    public void setListener() {
        parking.setOnClickListener(setOnclicklistener);
    }


    private View.OnClickListener setOnclicklistener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            mark = PARKING;
            System.out.println(ll);
            System.out.println(mark);
            mPoiSearch.searchNearby(new PoiNearbySearchOption()
                    .keyword(mark).pageCapacity(15).radius(3000).location(ll));
        }

    };

    @Override
    public void onGetPoiDetailResult(PoiDetailResult result) {
        // TODO Auto-generated method stub

        if (result.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(getContext(), "抱歉，未找到结果", Toast.LENGTH_SHORT)
                    .show();
        } else {
            Toast.makeText(getContext(), result.getName() + ": " + result.getAddress(), Toast.LENGTH_SHORT)
                    .show();
            System.out.println(result.getName());
            System.out.println(result.getAddress());
            System.out.println(result.getDetailUrl());
            System.out.println(result.getPrice());
            System.out.println(result.getShopHours());
            System.out.println(result.getTelephone());
            System.out.println(result.getServiceRating());
            id_recyclerview_horizontal.setVisibility(View.VISIBLE);
            mAdapter = new RecyclerViews(getContext());
            id_recyclerview_horizontal.setAdapter(mAdapter);

            //            id_recyclerview_horizontal.set
        }

    }


    @Override
    public void onGetPoiResult(PoiResult result) {

        if (result == null
                || result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
            return;
        }
        if (result.error == SearchResult.ERRORNO.NO_ERROR) {
            baiduMap.clear();

            MyOm onverlay = new MyOm(baiduMap);
            baiduMap.setOnMarkerClickListener(onverlay);
            onverlay.setResult(result);
            onverlay.addToMap();
            onverlay.zoomToSpan();

            PoiOverlay overlay = new MyPoiOverlay(baiduMap);
            baiduMap.setOnMarkerClickListener(overlay);
            overlay.setData(result);
            overlay.addToMap();
            overlay.zoomToSpan();
            return;
        }
        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_KEYWORD) {

            String strInfo = "在";
            for (CityInfo cityInfo : result.getSuggestCityList()) {
                strInfo += cityInfo.city;
                strInfo += ",";
            }
            strInfo += "找到结果";
            Toast.makeText(getContext(), strInfo, Toast.LENGTH_LONG)
                    .show();
        }
    }

    private class MyPoiOverlay extends PoiOverlay {

        public MyPoiOverlay(BaiduMap mBaiduMap) {
            super(mBaiduMap);
        }


        @Override
        public boolean onPoiClick(int index) {
            super.onPoiClick(index);
            PoiInfo poi = getPoiResult().getAllPoi().get(index);
            mPoiSearch.searchPoiDetail((new PoiDetailSearchOption())
                    .poiUid(poi.uid));
            return true;
        }

    }

    private class MyOm extends OverlayManager {
        private PoiResult result;

        public void setResult(PoiResult result) {
            this.result = result;
        }

        /**
         * 通过一个BaiduMap 对象构造
         *
         * @param baiduMap
         */
        public MyOm(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public List<OverlayOptions> getOverlayOptions() {
            ops = new ArrayList<OverlayOptions>();
            pois = result.getAllPoi();
            System.out.println(pois);
            return ops;
        }

        @Override
        public boolean onMarkerClick(Marker marker) {
            onClick(marker.getZIndex());
            return true;
        }

        public boolean onClick(int index) {
            PoiInfo poi = result.getAllPoi().get(index);
            if (poi.hasCaterDetails) {
                mPoiSearch.searchPoiDetail(
                        (new PoiDetailSearchOption())
                                .poiUid(poi.uid));
            }
            return true;
        }

        @Override
        public boolean onPolylineClick(Polyline polyline) {
            return false;
        }
    }

    private void Overlays() {

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
        baiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {

            @Override
            public boolean onMapPoiClick(MapPoi arg0) {
                return false;
            }

            @Override
            public void onMapClick(LatLng arg0) {
                id_recyclerview_horizontal.setVisibility(View.GONE);
                baiduMap.hideInfoWindow();
            }
        });


    }

    private void init() {
        baiduMap = mapView.getMap();
        //设置缩放级别
        mMarker = BitmapDescriptorFactory.fromResource(R.mipmap.maker);
        MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.zoomTo(15);
        baiduMap.setMapStatus(mapStatusUpdate);
        //        baiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
        mapView.showZoomControls(false);
        mapView.showScaleControl(false);


    }


    class RecyclerViews extends RecyclerView.Adapter<RecyclerViews.ViewHolder> {

        private LayoutInflater mInflater;


        public RecyclerViews(Context context) {
            mInflater = LayoutInflater.from(context);

        }

        @Override
        public RecyclerViews.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = mInflater.inflate(R.layout.maplist,
                    parent, false);
            ViewHolder viewHolder = new ViewHolder(view);

            viewHolder.map_evaluate = (TextView) view.findViewById(R.id.map_evaluate);
            viewHolder.map_ratingbar = (RatingBar) view.findViewById(R.id.map_ratingbar);
            viewHolder.map_prices = (TextView) view.findViewById(R.id.map_prices);
            viewHolder.map_address = (TextView) view.findViewById(R.id.map_address);
            viewHolder.map_phone = (TextView) view.findViewById(R.id.map_phone);
            viewHolder.map_name = (TextView) view.findViewById(R.id.map_name);
            return viewHolder;

        }

        @Override
        public void onBindViewHolder(RecyclerViews.ViewHolder holder, int position) {

            holder.map_address.setText(pois.get(position).address);
            holder.map_name.setText(pois.get(position).name);
            holder.map_phone.setText(pois.get(position).phoneNum);
            //            holder.map_prices.setText(pois.get(position).);
            //            holder.map_evaluate.setText(pois.get(position).uid);

        }

        @Override
        public int getItemCount() {
            return pois.size();
        }


        public class ViewHolder extends RecyclerView.ViewHolder {
            public ViewHolder(View itemView) {
                super(itemView);
            }

            TextView map_evaluate;
            RatingBar map_ratingbar;
            TextView map_prices;
            TextView map_address;
            TextView map_phone;
            TextView map_name;

        }


    }


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

            userLatitude = bdLocation.getLatitude();
            System.out.println(userLatitude);
            userLongitude = bdLocation.getLongitude();
            ll = new LatLng(userLatitude,
                    userLongitude);
            baiduMap.setMyLocationData(locData);

            if (isFirstLoc) {
                isFirstLoc = false;

                TextOptions textOptions = new TextOptions();
                textOptions.fontColor(0x60FF0000)
                        .text("米苏城堡")
                        .position(ll)
                        .fontSize(48)
                        .typeface(Typeface.SERIF)
                        .rotate(30);
                baiduMap.addOverlay(textOptions);
                LatLng ll = new LatLng(bdLocation.getLatitude(),
                        bdLocation.getLongitude());
                MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
                baiduMap.animateMapStatus(u);

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
