package com.caozhiliang.mystore;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.caozhiliang.httpdata.ImageData;
import com.caozhiliang.httpdata.TradeBean;
import com.caozhiliang.main.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.common.util.DensityUtil;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.net.URLEncoder;
import java.util.List;

/**
 * Author:CZL
 * time:2016/3/11  8:31
 */
public class Commodity extends Activity implements View.OnTouchListener {

    private TextView tv_trade;
    private TextView pia;
    private TextView tv_mytrade;
    private ImageView iv_trade;
    private EditText tv_prices1;
    private EditText tv_tradename;
    private EditText tv_prices2;
    private EditText tv_xinxi;
    private Button wanc;
    private List<TradeBean> tradedata;
    private ListView lv_store_evaluate;
    listadapte mlistadapte;
    public final String URL = "http://119.29.148.150:8080/Fuwu1";
    ImageOptions imageOptions1;
    private String bianh;
    private String mxinxi;
    private String picture;
    private String mprice1;
    private String mprice2;
    private String mtradename;
    private List<ImageData> imagedata;
    int length;
    private RelativeLayout rla;
    private RelativeLayout rexianshi;
    private LinearLayout llrexianshi;
    Context mContext;
    private int topTitleHeight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.commodity);
        mContext = this;
        SharedPreferences sp = getSharedPreferences("haha", Context.MODE_PRIVATE);
        bianh = sp.getString("storesnumberss", "");
        initView();
        rla.setOnTouchListener(this);
        getTradeData();
        GetTradeImageData();
        initOnclik();
    }

    private void GetTradeImageData() {


    }

    private void initView() {
        tv_trade = (TextView) findViewById(R.id.tv_trade);
        lv_store_evaluate = (ListView) findViewById(R.id.lv_store_evaluate);
        llrexianshi = (LinearLayout) findViewById(R.id.llrexianshi);
        tv_xinxi = (EditText) findViewById(R.id.tv_xinxi);
        tv_prices2 = (EditText) findViewById(R.id.tv_prices2);
        tv_prices1 = (EditText) findViewById(R.id.tv_prices1);
        rla = (RelativeLayout)findViewById(R.id.rla);
        tv_mytrade = (TextView) findViewById(R.id.tv_mytrade);
        wanc = (Button) findViewById(R.id.wanc);
        tv_tradename = (EditText) findViewById(R.id.tv_tradename);
        pia = (TextView) findViewById(R.id.pia);
        iv_trade = (ImageView) findViewById(R.id.iv_trade);
        tv_xinxi.setEnabled(false);
        tv_prices1.setEnabled(false);
        tv_prices2.setEnabled(false);
        tv_tradename.setEnabled(false);


    }

    private void initOnclik() {
        tv_trade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        lv_store_evaluate.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int pos, long id) {
                llrexianshi.setVisibility(View.VISIBLE);
//                BlurBehind.getInstance()
//                        .withAlpha(50)
//                        .withFilterColor(Color.parseColor("#cac5c5"))
//                        .setBackground(Commodity.this);
                llrexianshi.setBackgroundColor(Color.parseColor("#cac5c5"));
                x.image().bind(iv_trade, tradedata.get(pos).getImages(), imageOptions1);
                tv_mytrade.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tv_xinxi.setEnabled(true);
                        tv_prices1.setEnabled(true);
                        tv_prices2.setEnabled(true);
                        tv_tradename.setEnabled(true);
                        tv_tradename.setFocusable(true);
                        tv_xinxi.setFocusable(true);
                        tv_prices1.setFocusable(true);
                        tv_prices2.setFocusable(true);
                        tv_xinxi.setText(tradedata.get(pos).getJianjie());
                        tv_prices1.setText(tradedata.get(pos).getPrice1());
                        tv_prices2.setText(tradedata.get(pos).getPrice2());
                        tv_tradename.setText(tradedata.get(pos).getStorename());

                        wanc.setVisibility(View.VISIBLE);
                        wanc.setOnClickListener(new View
                                .OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                tv_tradename.setEnabled(false);
                                tv_xinxi.setEnabled(false);
                                tv_prices1.setEnabled(false);
                                tv_prices2.setEnabled(false);
                                mxinxi = tv_xinxi.getText().toString();
                                mtradename = tv_tradename.getText().toString();
                                mprice1 = tv_prices1.getText().toString();
                                mprice2 = tv_prices2.getText().toString();
                                try {
                                    mxinxi = URLEncoder.encode(mxinxi, "UTF-8");
                                    mxinxi = URLEncoder.encode(mxinxi, "UTF-8");
                                    mtradename = URLEncoder.encode(mtradename, "UTF-8");
                                    mtradename = URLEncoder.encode(mtradename, "UTF-8");
                                    mprice1 = URLEncoder.encode(mprice1, "UTF-8");
                                    mprice1 = URLEncoder.encode(mprice1, "UTF-8");
                                    mprice2 = URLEncoder.encode(mprice2, "UTF-8");
                                    mprice2 = URLEncoder.encode(mprice2, "UTF-8");
                                } catch (Exception e) {
                                }
                                RequestParams requestParams = new RequestParams(URL +
                                        "/TradeServlet?pan=gai&&number=" + String.valueOf
                                        (tradedata.get(pos).getNumber()) + "&&Storename="
                                        + mtradename + "&&price1=" + mprice1 + "&&price2=" +
                                        mprice2 + "&&jianjie=" + mxinxi
                                );
                                x.http().get(requestParams, new Callback.CommonCallback<String>() {
                                    @Override
                                    public void onSuccess(String result) {
                                        Toast.makeText(mContext,
                                                "修改成功", Toast.LENGTH_SHORT).show();
                                        wanc.setVisibility(View.GONE);
                                        llrexianshi.setVisibility(View.GONE);
                                        getTradeData();


                                    }

                                    @Override
                                    public void onError(Throwable ex,
                                                        boolean isOnCallback) {
                                        Toast.makeText(mContext,
                                                "修改失败", Toast.LENGTH_SHORT).show();

                                    }

                                    @Override
                                    public void onCancelled
                                            (CancelledException cex) {

                                    }

                                    @Override
                                    public void onFinished() {

                                    }
                                });
                            }
                        });


                    }


                });
                iv_trade.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClass(mContext, StoreTradeImage.class);
                        intent.putExtra("tradenu", String.valueOf(tradedata.get(pos).getNumber()));
                        startActivity(intent);
                        finish();
                    }
                });
                pia.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClass(mContext, TradeImageMore.class);
                        intent.putExtra("tradenu", String.valueOf(tradedata.get(pos).getNumber()));
                        startActivity(intent);
                        finish();
                    }
                });

                RequestParams tradeimageParams = new RequestParams(URL +
                        "/ImageTradeServlet?number=" + String.valueOf(tradedata.get(pos)
                        .getNumber()));

                x.http().get(tradeimageParams, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Gson gs = new Gson();
                        imagedata = gs.fromJson(result, new TypeToken<List<ImageData>>() {
                        }.getType());
                        length = imagedata.size();
                        pia.setText(length + "张");

                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {

                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });

            }
        });

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                int[] locations = new int[2];
                tv_trade.getLocationInWindow(locations);
                topTitleHeight = locations[1];
                break;
            case MotionEvent.ACTION_MOVE:
                moveViewByLayout(llrexianshi, (int) event.getRawX(),
                        (int) event.getRawY());
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }
    private void getTradeData() {
        RequestParams tradeParams = new RequestParams(URL + "/TradeServlet?Storenumber=" + bianh);
        x.http().get(tradeParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                gettradeData(result);

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                System.out.println("网址错误");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }

    private void gettradeData(String result) {
        Gson gs = new Gson();
        tradedata = gs.fromJson(result, new TypeToken<List<TradeBean>>() {
        }.getType());
        System.out.println(tradedata.get(0));
        mlistadapte = new listadapte();
        lv_store_evaluate.setAdapter(mlistadapte);
    }

    class listadapte extends BaseAdapter {
        @Override
        public int getCount() {
            return tradedata.size();
        }

        @Override
        public Object getItem(int position) {
            return tradedata.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            imageOptions1 = new ImageOptions.Builder()
                    .setImageScaleType(ImageView.ScaleType.FIT_XY)
                    .setRadius(DensityUtil.dip2px(5))
                    .setLoadingDrawableId(R.mipmap.loge)
                    .setPlaceholderScaleType(ImageView.ScaleType.FIT_XY)
                    .setFailureDrawableId(R.mipmap.loge)
                    .build();

            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(getApplication(), R.layout.trade_list_details, null);
                holder = new ViewHolder();
                holder.iv_image = (ImageView) convertView.findViewById(R.id.iv_image);
                holder.iv_detetails = (ImageView) convertView.findViewById(R.id.iv_detetails);
                holder.tv_prices2 = (TextView) convertView.findViewById(R.id.tv_prices2);
                holder.brief = (TextView) convertView.findViewById(R.id.brief);
                holder.tv_prices2.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                holder.tv_prices1 = (TextView) convertView.findViewById(R.id.tv_prices1);
                holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
                holder.iv_image.setScaleType(ImageView.ScaleType.FIT_XY);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();

            }
            holder.tv_prices2.setText(tradedata.get(position).getPrice2());
            holder.tv_prices1.setText(tradedata.get(position).getPrice1());
            holder.tv_name.setText(tradedata.get(position).getStorename());
            holder.brief.setText(tradedata.get(position).getJianjie());

            x.image().bind(holder.iv_image, tradedata.get(position).getImages(), imageOptions1);
            return convertView;
        }
    }

    static class ViewHolder {

        public TextView tv_prices2;
        public TextView tv_prices1;
        public TextView tv_name;
        public TextView brief;
        public ImageView iv_image;
        public ImageView iv_detetails;
    }


    private void moveViewByLayout(View view, int rawX, int rawY, int scale) {
        int left = rawX - llrexianshi.getWidth() / 2;
        int top = rawY - topTitleHeight - llrexianshi.getHeight() / 2;
        int width = left + (int) (view.getWidth() + scale);
        int height = top + (int) (view.getHeight() + scale);
        view.layout(left, top, width, height);
    }


    private void moveViewByLayout(View view, int rawX, int rawY) {
        int left = rawX - llrexianshi.getWidth() / 2;
        int top = rawY - topTitleHeight - llrexianshi.getHeight() / 2;
        int width = left + view.getWidth();
        int height = top + view.getHeight();
        view.layout(left, top, width, height);
    }
}
