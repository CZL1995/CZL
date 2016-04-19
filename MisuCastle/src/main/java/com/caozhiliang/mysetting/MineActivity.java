package com.caozhiliang.mysetting;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.caozhiliang.httpdata.FinalData;
import com.caozhiliang.httpdata.UserBean;
import com.caozhiliang.main.R;
import com.caozhiliang.tools.StreamTool;
import com.caozhiliang.view.Round;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MineActivity extends Fragment {
    TextView txt;
    private ImageView deng;
    String a, image, password, imagepath;
    String path = FinalData.FUWU_PATH;
    public UserBean use;

    private TextView tv_myorder;
    private TextView tv_mydetails;
    private TextView tv_cake;
    private TextView tv_collect;
    private TextView tv_myversion;
    private TextView tv_mystore;
    private Drawable drawable;
    private Drawable drawablea;

    Handler han = new Handler() {
        @SuppressLint("HandlerLeak")
        public void handleMessage(android.os.Message msg) {
            Round round = new Round();
            deng.setImageBitmap(round.toRoundBitmap((Bitmap) msg.obj));
        }
    };
    private View mineview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {

        inintview();


        SharedPreferences sp = getContext().getSharedPreferences("haha", Context.MODE_PRIVATE);
        a = sp.getString("name", "");
        imagepath = sp.getString("image", "");
        if (a.equals("")) {
            txt.setText("点击登录");
        } else {
            txt.setText(a);
            getimage();
        }
        initlistener();
        return mineview;
    }

    private void initlistener() {
        deng.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (txt.getText().toString().equals("点击登录")) {
                    Intent intent = new Intent(getActivity(), Denglu.class);
                    MineActivity.this.startActivity(intent);
                    getActivity().finish();
                }

            }
        });
        tv_mydetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!a.equals("")) {

                    Intent intent = new Intent(getContext(), Mine_person.class);
                    MineActivity.this.startActivity(intent);
                    getActivity().finish();

                } else {
                    Intent intent = new Intent(getContext(), Denglu.class);
                    MineActivity.this.startActivity(intent);
                    getActivity().finish();
                }
            }
        });
    }

    private void inintview() {
        drawable = getResources().getDrawable(R.mipmap.cake);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth() / 2, drawable.getIntrinsicHeight()
                / 2);
        drawablea = getResources().getDrawable(R.mipmap.collect);
        drawablea.setBounds(0, 0, drawablea.getIntrinsicWidth() / 2, drawablea.getIntrinsicHeight()
                / 2);
        mineview = View.inflate(getContext(), R.layout.morea, null);
        tv_collect = (TextView) mineview.findViewById(R.id.tv_collect);
        tv_cake = (TextView) mineview.findViewById(R.id.tv_cake);
        tv_cake.setCompoundDrawables(drawable, null, null, null);
        tv_collect.setCompoundDrawables(drawablea, null, null, null);
        deng = (ImageView) mineview.findViewById(R.id.imageButton_denglu);
        txt = (TextView) mineview.findViewById(R.id.dianji);
        tv_myorder = (TextView) mineview.findViewById(R.id.tv_myorder);
        tv_mydetails = (TextView) mineview.findViewById(R.id.tv_mydetails);
        tv_myversion = (TextView) mineview.findViewById(R.id.tv_myversion);
        tv_mystore = (TextView) mineview.findViewById(R.id.tv_mystore);
    }


    public void getimage() {
        Thread t = new Thread() {
            public void run() {
                try {
                    URL url = new URL(imagepath);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setConnectTimeout(5000);
                    con.setRequestMethod("GET");
                    if (con.getResponseCode() == 200) {
                        InputStream in = con.getInputStream();
                        StreamTool st = new StreamTool();
                        byte[] data = st.read(in);
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inJustDecodeBounds = true;
                        Bitmap bit = BitmapFactory.decodeByteArray(data, 0, data.length, options);
                        options.inJustDecodeBounds = false; //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
                        int be = (int) (options.outHeight / (float) 320);
                        if (be <= 0)
                            be = 1;
                        options.inSampleSize = be; //重新读入图片，注意此时已经把 options.inJustDecodeBounds 设回
                        // false 了
                        bit = BitmapFactory.decodeByteArray(data, 0, data.length, options);
                        Message mg = new Message();
                        mg.obj = bit;
                        han.sendMessage(mg);
                    }

                } catch (Exception e) {

                }
            }
        };
        t.start();
    }


}
