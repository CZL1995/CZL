package com.caozhiliang.fragment;

import android.graphics.Paint;
import android.os.Bundle;
import android.widget.TextView;

import com.caozhiliang.base.BaseActivity;
import com.caozhiliang.httpdata.TradeBean;
import com.caozhiliang.main.R;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * @author CZL
 * @time 2016-04-14 15:17
 */
public class TradeDetails extends BaseActivity {


    int id;
    private TradeBean tradedata;

    private TextView trade_name;
    private TextView trade_store_name;
    private TextView trade_location;
    private TextView trade_prices1;
    private TextView trade_prices2;
    private TextView tv_trade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trade_detail);
        initview();
        id = getIntent().getIntExtra("trade", 0);
        GetTradeData();

    }

    private void initview() {
        trade_name = (TextView) findViewById(R.id.trade_name);
        trade_store_name = (TextView) findViewById(R.id.trade_store_name);
        trade_location = (TextView) findViewById(R.id.trade_location);
        trade_prices1 = (TextView) findViewById(R.id.trade_prices1);
        trade_prices2 = (TextView) findViewById(R.id.trade_prices2);
        tv_trade = (TextView) findViewById(R.id.tv_trade);
    }

    private void GetTradeData() {
        RequestParams tradeParams = new RequestParams(URL + "/TradeServlet?number=" + id);
        x.http().get(tradeParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gs = new Gson();
                tradedata = gs.fromJson(result, TradeBean.class);
                System.out.println(tradedata);
                trade_name.setText(tradedata.getStorename());
                trade_store_name.setText(tradedata.getStorenamezheng());
                trade_location.setText(tradedata.getAddress());
                trade_prices1.setText(tradedata.getPrice1());
                trade_prices2.setText(tradedata.getPrice2());
                trade_prices2.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                tv_trade.setText(tradedata.getJianjie());
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


}
