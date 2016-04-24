package com.caozhiliang.fragment;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.caozhiliang.base.BaseActivity;
import com.caozhiliang.httpdata.EvaluateData;
import com.caozhiliang.main.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

/**
 * @author CZL
 * @time 2016-04-23 8:31
 */
public class Evaluate extends BaseActivity {


    private TextView tv_back;
    private ListView lv_listview;
    private String evaluate;
    private listviewadapter mlistadapter;

    private List<EvaluateData> evaluatelist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.evaluate);
        evaluate=getIntent().getStringExtra("evaluatetradenumber");
        initview();
        initData();
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Evaluate.this.finish();
            }
        });
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            Evaluate.this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    private void initData() {
        RequestParams requestParams=new RequestParams(URL+"/PingjiaServlet?pan=cha&&number="+evaluate);

        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gs = new Gson();
                evaluatelist = gs.fromJson(result, new TypeToken<List<EvaluateData>>() {
                }.getType());
                System.out.println(evaluatelist);
                mlistadapter = new listviewadapter();
                lv_listview.setAdapter(mlistadapter);
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

    private void initview() {
        lv_listview = (ListView) findViewById(R.id.lv_listview);
        tv_back = (TextView) findViewById(R.id.tv_back);


    }

    class listviewadapter extends BaseAdapter {
        @Override
        public int getCount() {
            return evaluatelist.size();
        }

        @Override
        public Object getItem(int position) {
            return evaluatelist.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(getApplicationContext(), R.layout.evaluate_list, null);
                holder = new ViewHolder();
                holder.evaluate_ratingbar = (RatingBar) convertView.findViewById(R.id
                        .evaluate_ratingbar);
                holder.iv_image = (ImageView) convertView.findViewById(R.id.iv_image);
                holder.tv_evaluate = (TextView) convertView.findViewById(R.id.tv_evaluate);
                holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
                holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();

            }
            holder.tv_name.setText(evaluatelist.get(position).getUsername());
            x.image().bind(holder.iv_image, evaluatelist.get(position).getUserimage());
            System.out.println(evaluatelist.get(position).getUserimage());
            holder.tv_time.setText(evaluatelist.get(position).getTime());
            holder.tv_evaluate.setText(evaluatelist.get(position).getXiangqin());
            holder.evaluate_ratingbar.setRating((float)(evaluatelist.get(position).getXingji()));

            return convertView;
        }

        class ViewHolder {
            private TextView tv_name;
            private TextView tv_time;
            private TextView tv_evaluate;
            private ImageView iv_image;
            private RatingBar evaluate_ratingbar;
        }


    }

}
