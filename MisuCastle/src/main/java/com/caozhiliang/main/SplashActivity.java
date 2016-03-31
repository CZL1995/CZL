package com.caozhiliang.main;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.caozhiliang.base.BaseActivity;
import com.caozhiliang.httpdata.JsonData;
import com.caozhiliang.tools.StreamUtils;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author CZL
 * @time 2016-03-14 22:12
 */
@ContentView(R.layout.splash_activity)
public class SplashActivity extends BaseActivity {


    @ViewInject(R.id.tv_versioncode_splash)
    private TextView tv_versioncode_splash;

    @ViewInject(R.id.pb_splash)
    private ProgressBar pb_splash;

    @ViewInject(R.id.tv_progress)
    private TextView tv_progress;

    @ViewInject(R.id.rl_splash)
    private RelativeLayout rl_splash;

    protected static final int CODE_UPDATE_DIALOG = 0;
    protected static final int CODE_URL_ERROR = 1;
    protected static final int CODE_NET_ERROR = 2;
    protected static final int CODE_ENTER_HOME = 3;// 进入主页面
    JsonData a = new JsonData();
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CODE_UPDATE_DIALOG:
                    System.out.println("更新框");
                    showDailgo();
                    break;
                case CODE_URL_ERROR:
                    Toast.makeText(SplashActivity.this, "URL错误", Toast.LENGTH_SHORT).show();
                    enterhome();
                    break;
                case CODE_NET_ERROR:
                    Toast.makeText(SplashActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
                    enterhome();
                    break;
                case CODE_ENTER_HOME:
                    System.out.println("最新版本");
                    enterhome();
                    break;
                default:
                    break;

            }


        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.Ext.init(getApplication());
        tv_versioncode_splash.setText("版本名称:" + getVersionname());
        starAnim();
        checkversion();



    }

    private void starAnim() {
        AnimationSet set = new AnimationSet(false);//设置动画集合
        //旋转动画
        RotateAnimation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(2000);
        rotate.setFillAfter(true);
        //缩放动画
        ScaleAnimation scale = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        scale.setDuration(2000);
        scale.setFillAfter(true);
        AlphaAnimation alpha = new AlphaAnimation(0, 1);
        alpha.setDuration(2000);
        alpha.setFillAfter(true);
        set.addAnimation(rotate);
        set.addAnimation(scale);
        set.addAnimation(alpha);
        rl_splash.startAnimation(set);

    }


    private void showDailgo() {


        new AlertDialog.Builder(this)
                .setTitle("最新版本：" + a.getVersionCode())
                .setMessage(a.getDescription())
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        download();

                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        enterhome();
                    }
                })
                .show();


    }

    private void download() {
        /*Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)
                验证是否有SD卡
                */
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            tv_progress.setVisibility(View.VISIBLE);
            //            System.out.println("有卡");
            //            String url = a.getDownloadUrl();
            //            System.out.println(url);
            RequestParams params = new RequestParams(a.getDownloadUrl());
            x.http().get(params, new Callback.ProgressCallback<File>() {


                @Override
                public void onSuccess(File result) {

                    System.out.println("下载成功");
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_DEFAULT);
                    intent.setDataAndType(Uri.fromFile(result), "application/vnd.android" +
                            ".package-archive");
                    startActivityForResult(intent, 0);

                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    Toast.makeText(SplashActivity.this, "下载失败", Toast.LENGTH_SHORT)
                            .show();
                    enterhome();
                }

                @Override
                public void onCancelled(CancelledException cex) {

                }

                @Override
                public void onFinished() {


                }

                @Override
                public void onWaiting() {

                }

                @Override
                public void onStarted() {

                }

                @Override
                public void onLoading(long total, long current, boolean
                        isDownloading) {

                    tv_progress.setText("下载进度：" + current * 100 / total + "%");


                }
            });


        } else {
            //            System.out.println("验证失败");
        }

    }

    private void enterhome() {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void checkversion() {
        final long startTime = System.currentTimeMillis();
        new Thread() {
            public void run() {
                //                Message msg=Message.obtain();
                Message msg = Message.obtain();
                HttpURLConnection httpurlconnection = null;

                try {
                    URL url = new URL(URL+"/update.json");
//                    URL url = new URL("http://xingxinga123.imwork.net:14553/Fuwu1/update.json");
                    try {
                        //打开http连接
                        httpurlconnection = (HttpURLConnection) url.openConnection();
                        //设置连接方法
                        httpurlconnection.setRequestMethod("GET");
                        //设置连接超时时间
                        httpurlconnection.setConnectTimeout(5000);
                        //设置响应时间
                        httpurlconnection.setReadTimeout(5000);
                        //连接服务器
                        int responsedcode = httpurlconnection.getResponseCode();
                        if (responsedcode == 200) {
                            //                            System.out.println("连接成功");
                            InputStream inputStream = httpurlconnection.getInputStream();
                            String result = StreamUtils.readFromStream(inputStream);
                            Gson gson = new Gson();
                            a = gson.fromJson(result, JsonData.class);
                            //                            System.out.println(a.getDescription());
                            // 说明 解析成功
                            if (a.getVersionCode() > versioncode()) {
                                msg.what = CODE_UPDATE_DIALOG;
                            } else {
                                msg.what = CODE_ENTER_HOME;
                            }

                        }
                    } catch (IOException e) {
                        msg.what = CODE_NET_ERROR;

                        e.printStackTrace();
                    }
                } catch (MalformedURLException e) {
                    msg.what = CODE_URL_ERROR;

                    e.printStackTrace();
                } finally {
                    long endTime = System.currentTimeMillis();
                    long timeUsed = endTime - startTime;// 访问网络花费的时间
                    if (timeUsed < 2000) {
                        // 强制休眠一段时间,保证闪屏页展示2秒钟
                        try {
                            Thread.sleep(2000 - timeUsed);

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    handler.sendMessage(msg);
                    if (httpurlconnection != null) {
                        httpurlconnection.disconnect();// 关闭网络连接
                    }
                }

            }
        }.start();

    }

    private void initview() {
//        rl_splash = (RelativeLayout) findViewById(R.id.rl_splash);
//        tv_versioncode_splash = (TextView) findViewById(R.id.tv_versioncode_splash);
//        pb_splash = (ProgressBar) findViewById(R.id.pb_splash);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        enterhome();
        super.onActivityResult(requestCode, resultCode, data);
    }

    private String getVersionname() {
        PackageManager packagemanager = getPackageManager();
        try {
            //获取包的信息
            PackageInfo packageinfo = packagemanager.getPackageInfo(getPackageName(), 0);
            String versionname = packageinfo.versionName;
            return versionname;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return "";

    }


    private int versioncode() {
        PackageManager packagemanger = getPackageManager();
        try {
            PackageInfo packageinfo = packagemanger.getPackageInfo(getPackageName(), 0);
            int versioncode = packageinfo.versionCode;
            return versioncode;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return -1;
    }


}
