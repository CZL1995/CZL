package com.caozhiliang.mystore;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.caozhiliang.httpdata.TradeBean;
import com.caozhiliang.main.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.common.util.DensityUtil;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.List;

/**
 * Author:CZL
 * time:2016/3/11  8:31
 */
public class Commodity extends Activity {

    private TextView tv_trade;
    private List<TradeBean> tradedata;
    private ListView lv_store_evaluate;
    listadapte mlistadapte;
    public final String URL = "http://119.29.148.150:8080/Fuwu1";
    ImageOptions imageOptions1;
    private String bianh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.commodity);
        SharedPreferences sp = getSharedPreferences("haha", Context.MODE_PRIVATE);
        bianh = sp.getString("storesnumberss", "");
        initView();
        getTradeData();
        initOnclik();
    }

    private void initView() {
        tv_trade = (TextView) findViewById(R.id.tv_trade);
        lv_store_evaluate = (ListView) findViewById(R.id.lv_store_evaluate);

    }

    private void initOnclik() {
        tv_trade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

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


}
