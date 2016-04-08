package com.caozhiliang.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.caozhiliang.base.BaseFragment;
import com.caozhiliang.httpdata.TradeBean;
import com.caozhiliang.main.R;
import com.caozhiliang.view.RefreshListView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.common.util.DensityUtil;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.List;

/**
 * @author CZL
 * @time 2016-04-08 22:50
 */
public class MainFragmen extends BaseFragment {

    private View view = null;
    private List<TradeBean> list;
    private RefreshListView listview;
    private listviewadpter mlistadapter;
    ImageOptions imageOptions1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.tradelist, container, false);
        }
        listview = (RefreshListView) view.findViewById(R.id.trade_listview);

        Bundle mBundle = getArguments();
        String title = mBundle.getString("arg");
        getServerData();
        return view;
    }

    public void getServerData() {
        RequestParams requestParams = new RequestParams(URL +
                "/TabServlet?first=Trade&&second=jiage&&third=0");
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                getData(result);
                //                System.out.println(result);


            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                System.out.println("错误");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });


    }

    public void getData(String result) {


        Gson gs = new Gson();
        list = gs.fromJson(result, new TypeToken<List<TradeBean>>() {
        }.getType());
        System.out.println(list.get(1).toString());
        System.out.println(list.toString());
        System.out.println(list.size());


        mlistadapter = new listviewadpter();
        listview.setAdapter(mlistadapter);


    }

    class listviewadpter extends BaseAdapter {
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
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
                convertView = View.inflate(getContext(), R.layout.trade, null);
                holder = new ViewHolder();
                holder.iv_image = (ImageView) convertView.findViewById(R.id.iv_image);
                holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
                holder.tv_rank = (TextView) convertView.findViewById(R.id.tv_rank);
                holder.tv_brief = (TextView) convertView.findViewById(R.id.tv_brief);
                holder.tv_location = (TextView) convertView.findViewById(R.id.tv_location);
                holder.tv_per = (TextView) convertView.findViewById(R.id.tv_per);
                holder.tv_distance = (TextView) convertView.findViewById(R.id.tv_distance);
                holder.room_ratingbar = (RatingBar) convertView.findViewById(R.id.room_ratingbar);


                holder.iv_image.setScaleType(ImageView.ScaleType.FIT_XY);


                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();

            }
            holder.tv_name.setText(list.get(position).getStorename());
            holder.tv_rank.setText((int) list.get(position).getXingpj());
            holder.tv_brief.setText(list.get(position).getJianjie());
            holder.tv_distance.setText((int) list.get(position).getJuli());
            holder.tv_location.setText(list.get(position).getAddress());
            holder.tv_per.setText(list.get(position).getXfrenshu());


            x.image().bind(holder.iv_image, list.get(position).getImages(), imageOptions1);
            return convertView;
        }

        class ViewHolder {

            public TextView tv_name;
            public TextView tv_rank;
            public TextView tv_brief;
            public TextView tv_location;
            public TextView tv_per;
            public TextView tv_distance;
            public RatingBar room_ratingbar;
            public ImageView iv_image;


        }
    }


}
