package com.caozhiliang.mysetting;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.caozhiliang.httpdata.FinalData;
import com.caozhiliang.httpdata.UserBean;
import com.caozhiliang.main.MyOrder;
import com.caozhiliang.main.R;
import com.caozhiliang.mystore.StoreMainActivity;

import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.x;

public class MineActivity extends Fragment {
    TextView txt;
    private ImageView deng;
    String a, image, password, imagepath, mpath, ipath;
    String path = FinalData.FUWU_PATH;
    public UserBean use;
    private ImageOptions imageOptions1;
    private TextView tv_myorder;
    private TextView tv_mydetails;
    private TextView tv_cake;
    private TextView tv_collect;
    private TextView tv_myversion;
    private TextView tv_mystore;
    private Drawable drawable;
    private Drawable drawablea;
    private View mineview;
    private RelativeLayout rl_order;
    private RelativeLayout rl_detail;
    private String storesnumber;
    private String sellersnumber;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {

        inintview();
        SharedPreferences sp = getContext().getSharedPreferences("haha", Context.MODE_PRIVATE);
        a = sp.getString("name", "");
        mpath = sp.getString("mimage", "");
        ipath = sp.getString("image", "");
        storesnumber = sp.getString("storesnumber", "");
        sellersnumber = sp.getString("sellersnumber", "");
        if (mpath.isEmpty()) {
            imagepath = ipath;
        } else {
            imagepath = mpath;

        }
        System.out.println(imagepath);

        if (a.equals("")) {
            txt.setText("点击登录");
        } else {
            txt.setText(a);
            imageOptions1 = new ImageOptions.Builder()
                    .setImageScaleType(ImageView.ScaleType.FIT_XY)
                    .setRadius(DensityUtil.dip2px(1000))
                    .setLoadingDrawableId(R.mipmap.tx)
                    .setPlaceholderScaleType(ImageView.ScaleType.FIT_XY)
                    .setFailureDrawableId(R.mipmap.tx)
                    .build();
            x.image().bind(deng, imagepath, imageOptions1);
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
                } else {
                    Intent intent = new Intent(getActivity(), Mine_person.class);
                    MineActivity.this.startActivity(intent);
                    getActivity().finish();
                }

            }
        });
        rl_detail.setOnClickListener(new View.OnClickListener() {
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
        rl_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!a.equals("")) {

                    Intent intent = new Intent(getContext(), MyOrder.class);
                    MineActivity.this.startActivity(intent);
                    getActivity().finish();

                } else {

                    Intent intent = new Intent(getContext(), Denglu.class);
                    MineActivity.this.startActivity(intent);
                    getActivity().finish();
                }
            }
        });

        tv_mystore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (a.equals("")) {

                    Intent intent = new Intent(getContext(), Denglu.class);
                    MineActivity.this.startActivity(intent);
                    getActivity().finish();

                } else {
                    if (sellersnumber.equals("0")) {
                        showDailgo();

                    } else {
                        Intent intent = new Intent(getContext(), StoreMainActivity.class);
                        MineActivity.this.startActivity(intent);
                        getActivity().finish();
                    }
                }


            }
        });
        tv_myversion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(), NewVersion.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

    }
    private void showDailgo() {


        new AlertDialog.Builder(getContext())
                .setTitle("是否成为商家？" )
                .setMessage("点击确定进入商家界面")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getContext(), StoreMainActivity.class);
                        MineActivity.this.startActivity(intent);
                        getActivity().finish();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();

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
        rl_detail = (RelativeLayout) mineview.findViewById(R.id.rl_detail);
        rl_order = (RelativeLayout) mineview.findViewById(R.id.rl_order);
        tv_mydetails = (TextView) mineview.findViewById(R.id.tv_mydetails);
        tv_myversion = (TextView) mineview.findViewById(R.id.tv_myversion);
        tv_mystore = (TextView) mineview.findViewById(R.id.tv_mystore);
    }


}
