package com.caozhiliang.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.caozhiliang.base.BaseFragment;
import com.caozhiliang.httpdata.StoreBean;
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
public class MainStoreFragmen extends BaseFragment {

    private View view = null;
    private List<StoreBean> list;
    private List<StoreBean> list1;
    private RefreshListView listview;
    private listviewadpter mlistadapter;
    private int i = 0;
    private boolean mMoreUrl = true;//跟多页面地址
    ImageOptions imageOptions1;
    private String url;

    Bundle mBundle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.tradelist, container, false);
        }
        listview = (RefreshListView) view.findViewById(R.id.trade_listview);
        listview.setOnRefreshListener(new RefreshListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mMoreUrl = true;
                i = 1;
                getServerData();
                i = 0;
            }

            @Override
            public void onLoadMore() {
                i++;
                //                System.out.println(i);
                if (mMoreUrl) {
                    url = URL + "/TabServlet?first=Store&&second=" + mBundle.getString("arg") +
                            "&&third=" + i;
                    getMoreServiceData();

                } else {
                    Toast.makeText(getContext(), "最后一页了", Toast.LENGTH_SHORT).show();
                    listview.onRefreshComplete(false);

                }
            }
        });
        mBundle = getArguments();
        mBundle.getString("arg");
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent intent = new Intent();
                intent.setClass(getContext(), StoreDetails.class);
                intent.putExtra("id", list.get(position - 1).getStoreNumber());
                startActivity(intent);
            }
        });


        getServerData();

        return view;
    }

    private void getMoreServiceData() {
        RequestParams requestParams = new RequestParams(url);
        x.http().get(requestParams, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                getData(result, true);
                listview.onRefreshComplete(true);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                listview.onRefreshComplete(false);
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });


    }


    public void getServerData() {
        RequestParams requestParams = new RequestParams(URL +
                "/TabServlet?first=Store&&second=" + mBundle.getString("arg") + "&&third=0");
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println(result);
                getData(result, false);
                //                System.out.println(result);
                listview.onRefreshComplete(true);

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                System.out.println("错误");
                listview.onRefreshComplete(true);
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });


    }

    public void getData(String result, boolean isMore) {


        if (!isMore) {
            Gson gs = new Gson();
            list = gs.fromJson(result, new TypeToken<List<StoreBean>>() {
            }.getType());


            mlistadapter = new listviewadpter();
            listview.setAdapter(mlistadapter);

        } else {
            Gson gs = new Gson();
            list1 = gs.fromJson(result, new TypeToken<List<StoreBean>>() {
            }.getType());
            if (list1.isEmpty()) {
                Toast.makeText(getContext(), "最后一页了", Toast.LENGTH_SHORT).show();
                listview.onRefreshComplete(false);

            } else {
                list.addAll(list1);
                mlistadapter.notifyDataSetChanged();
            }

        }


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
                convertView = View.inflate(getContext(), R.layout.store, null);
                holder = new ViewHolder();
                holder.iv_image = (ImageView) convertView.findViewById(R.id.iv_image);
                holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
                holder.tv_rank = (TextView) convertView.findViewById(R.id.tv_rank);
                holder.tv_brief = (TextView) convertView.findViewById(R.id.tv_brief);
                holder.tv_location = (TextView) convertView.findViewById(R.id.tv_location);
                holder.tv_per = (TextView) convertView.findViewById(R.id.tv_per);
                holder.tv_distance = (TextView) convertView.findViewById(R.id.tv_distance);
                holder.room_ratingbar = (RatingBar) convertView.findViewById(R.id.room_ratingbar);
                holder.tv_dianzan = (TextView) convertView.findViewById(R.id.tv_dianzan);

                holder.iv_image.setScaleType(ImageView.ScaleType.FIT_XY);


                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();

            }
            holder.tv_name.setText(list.get(position).getName());
            holder.tv_rank.setText(list.get(position).getXingpj());
            holder.tv_brief.setText("¥" + list.get(position).getRenjun() + "/人");
            holder.tv_distance.setText("<" + list.get(position).getJuli() + "km");
            holder.tv_location.setText(list.get(position).getDiqu());
            holder.tv_dianzan.setText(list.get(position).getXfrenshu());


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
            public TextView tv_dianzan;
            public RatingBar room_ratingbar;
            public ImageView iv_image;


        }
    }


}
