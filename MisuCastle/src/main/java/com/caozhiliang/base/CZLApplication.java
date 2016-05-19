package com.caozhiliang.base;

import android.app.Application;
import android.util.Log;

import com.facebook.drawee.backends.pipeline.Fresco;

import org.xutils.x;

import cn.jpush.android.api.JPushInterface;

public class CZLApplication extends Application {

    private static final String TAG = "JPush";
    @Override
    public void onCreate() {
        Log.d(TAG, "[CZLApplication] onCreate");

        super.onCreate();
        x.Ext.init(this);
        Fresco.initialize(getApplicationContext());
        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志

        JPushInterface.init(this);
    }
}
