package com.caozhiliang.base;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;

import org.xutils.x;

public class CZLApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
    }
}
