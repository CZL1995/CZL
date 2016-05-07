package com.caozhiliang.mystore;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.caozhiliang.base.BaseFragment;
import com.caozhiliang.fragment.PayGaiMent;
import com.caozhiliang.fragment.TradeDetailStore;
import com.caozhiliang.httpdata.OrderData;
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
 * Created by ${CZL} on 2016/3/10.
 */
public class NewOrder extends BaseFragment {

    private Context context;
    private View neworderview;
    private View view = null;
    private List<OrderData> list;
    private ListView listview;
    private listviewadpter mlistadapter;
    private int i = 0;
    ImageOptions imageOptions1;
    private boolean open = true;
    private String bianh;
    private String username;
    private String ordernumber;
    private String xiangqing;
    private String orderzhunagtai;
    Bundle mBundle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        neworderview = View.inflate(getContext(),R.layout.orderlist, null);
        listview = (ListView) neworderview.findViewById(R.id.listview);
        SharedPreferences sp = getContext().getSharedPreferences("haha", Context.MODE_PRIVATE);
        bianh = sp.getString("storesnumberss", "");
        mBundle = getArguments();
        mBundle.getString("zt");
        getServerData();
        System.out.println("bianhao=" + bianh);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (list.get(position).getZhuangtai()) {
                    case "评价完成":
                        Intent intent = new Intent();
                        intent.setClass(getContext(), TradeDetailStore.class);
                        intent.putExtra("id", list.get(position).getNumber());
                        startActivity(intent);
                        break;
                    case "等待付款":
                        Intent intent1 = new Intent();
                        intent1.setClass(getContext(), PayGaiMent.class);
                        intent1.putExtra("pri", String.valueOf(list.get(position).getZongjia()));
                        System.out.println(list.get(position).getZongjia());
                        intent1.putExtra("i", String.valueOf(list.get(position).getCount()));
                        System.out.println(list.get(position).getCount());
                        intent1.putExtra("dan", String.valueOf(list.get
                                (position).getOrdernumber()));

                        startActivity(intent1);
                        break;
                    case "等待发货":
                        Toast.makeText(getContext(), "您的宝贝还在准备请稍等", Toast.LENGTH_SHORT).show();

                    case "等待收货":
                        Toast.makeText(getContext(), "请点击确认收货", Toast.LENGTH_SHORT).show();
                        break;
                    case "等待评价":
                        Toast.makeText(getContext(), "请评价后继续操作", Toast.LENGTH_SHORT).show();
                        break;

                }


            }
        });
        return neworderview;
    }


    public void getServerData() {
        RequestParams requestParams = new RequestParams(URL +
                "/OrderServlet?pan=cha&&storenumber=" + bianh + "&&zhuangtai=" + mBundle.getString
                ("zt"));
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println(result);

                Gson gs = new Gson();
                list = gs.fromJson(result, new TypeToken<List<OrderData>>() {
                }.getType());
                System.out.println(list);
                mlistadapter = new listviewadpter();
                listview.setAdapter(mlistadapter);

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                System.out.println(ex);
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

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
        public void unregisterDataSetObserver(DataSetObserver observer) {
            if (observer != null) {
                super.unregisterDataSetObserver(observer);
            }
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;

            imageOptions1 = new ImageOptions.Builder()
                    .setImageScaleType(ImageView.ScaleType.FIT_XY)
                    .setRadius(DensityUtil.dip2px(5))
                    .setLoadingDrawableId(R.mipmap.loge)
                    .setPlaceholderScaleType(ImageView.ScaleType.FIT_XY)
                    .setFailureDrawableId(R.mipmap.loge)
                    .build();
            if (convertView == null) {
                convertView = View.inflate(getContext(), R.layout.orderfragment, null);
                holder = new ViewHolder();
                holder.iv_image = (ImageView) convertView.findViewById(R.id.iv_image);
                holder.rl_evaluate = (RelativeLayout) convertView.findViewById(R.id.rl_evaluate);
                holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
                holder.ed_evaluate = (EditText) convertView.findViewById(R.id.ed_evaluate);
                holder.bt_pingjia = (Button) convertView.findViewById(R.id.bt_pingjia);
                holder.bt_evaluate = (Button) convertView.findViewById(R.id.bt_evaluate);
                holder.tv_zhuangtai = (TextView) convertView.findViewById(R.id.tv_zhuangtai);
                holder.iv_image.setScaleType(ImageView.ScaleType.FIT_XY);
                holder.tv_prices1 = (TextView) convertView.findViewById(R.id.tv_prices1);
                holder.tv_rank = (TextView) convertView.findViewById(R.id.tv_rank);
                holder.room_ratingbar = (RatingBar) convertView.findViewById(R.id.room_ratingbar);
                holder.tv_zongjia = (TextView) convertView.findViewById(R.id.tv_zongjia);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();

            }
            holder.tv_name.setText(list.get(position).getTradename());
            holder.tv_prices1.setText("¥" + list.get(position).getPrice1());
            x.image().bind(holder.iv_image, list.get(position).getImage(), imageOptions1);
            holder.tv_zhuangtai.setText(list.get(position).getZhuangtai());
            holder.tv_zongjia.setText("共" + list.get(position).getCount() + "件商品  合计：" + list
                    .get(position).getZongjia());
            ordernumber = String.valueOf(list.get(position).getOrdernumber());

            switch (list.get(position).getZhuangtai()) {
                case "评价完成":
                    holder.bt_pingjia.setText("已评价");
                    holder.bt_pingjia.setClickable(false);
                    break;
                case "等待付款":
                    holder.bt_pingjia.setText("待付款");
                    holder.bt_pingjia.setClickable(false);
                    break;
                case "等待收货":
                    holder.bt_pingjia.setText("待收货");
                    holder.bt_pingjia.setClickable(false);
                    break;
                case "等待发货":
                    holder.bt_pingjia.setText("待发货");
                    holder.bt_pingjia.setClickable(false);
                    break;
                case "等待评价":
                    holder.bt_pingjia.setText("待评价");
                    holder.bt_pingjia.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (open) {
                                holder.rl_evaluate.setVisibility(View.VISIBLE);

                                holder.room_ratingbar.setOnRatingBarChangeListener(new RatingBar
                                        .OnRatingBarChangeListener() {


                                    @Override
                                    public void onRatingChanged(RatingBar ratingBar, float rating,
                                                                boolean fromUser) {
                                        holder.tv_rank.setText(String.valueOf(rating));
                                    }
                                });
                                holder.bt_evaluate.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        System.out.println(list.get(position).getNumber());
                                        System.out.println(holder.tv_rank.getText().toString());
                                        System.out.println(holder.ed_evaluate.getText());
                                        xiangqing = holder.ed_evaluate.getText().toString();
                                        try {
                                            xiangqing = URLEncoder.encode(xiangqing, "UTF-8");
                                            xiangqing = URLEncoder.encode(xiangqing, "UTF-8");
                                            username = URLEncoder.encode(username, "UTF-8");
                                            username = URLEncoder.encode(username, "UTF-8");
                                        } catch (Exception e) {
                                        }
                                        RequestParams reparams = new RequestParams(URL +
                                                "/PingjiaServlet?pan=tian&&usernumber=" + bianh +
                                                "&&number=" + list.get(position).getNumber() +
                                                "&&username=" + username + "&&ordernumber=" +
                                                ordernumber +
                                                "&&xingji=" + holder.tv_rank.getText().toString() +
                                                "&&xiangqin=" +
                                                xiangqing);
                                        x.http().get(reparams, new Callback.CommonCallback<String>() {
                                            @Override
                                            public void onSuccess(String result) {
                                                if (result.equals("评论成功")) {
                                                    Toast.makeText(getContext(), "评价成功", Toast
                                                            .LENGTH_SHORT).show();
                                                    getServerData();
                                                    holder.rl_evaluate.setVisibility(View.GONE);
                                                    holder.bt_pingjia.setClickable(false);
                                                    mlistadapter.notifyDataSetChanged();


                                                }
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


                                open = false;
                            } else {
                                holder.rl_evaluate.setVisibility(View.GONE);
                                open = true;
                            }
                        }

                    });

                    break;

            }



            //            if (holder.tv_zhuangtai.getText().toString().equals("等待评价")) {
            //            } else {
            //
            //                holder.bt_pingjia.setClickable(false);
            //
            //            }


            return convertView;
        }

        class ViewHolder {

            public TextView tv_name;

            public TextView tv_zhuangtai;
            public TextView tv_prices1;
            public TextView tv_zongjia;
            public TextView tv_rank;
            public Button bt_pingjia;
            public Button bt_evaluate;
            public EditText ed_evaluate;
            public ImageView iv_image;
            public RatingBar room_ratingbar;

            public RelativeLayout rl_evaluate;


        }

    }


}
