package com.caozhiliang.mysetting;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.caozhiliang.httpdata.FinalData;
import com.caozhiliang.httpdata.UserBean;
import com.caozhiliang.main.R;
import com.caozhiliang.tools.StreamTool;
import com.caozhiliang.view.SortList;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MineActivity extends Fragment {
    //*****1
    List<item> date2 = new ArrayList<item>();
    List<item> date1 = new ArrayList<item>();
    List<item> date3 = new ArrayList<item>();
    SortList moreList2, moreList1, moreList3;
    TextView txt;
    ImageView deng;
    MoreAdapter moreAda2, moreAda1, moreAda3;
    String a, image, password, imagepath;
    String path = FinalData.FUWU_PATH;
    public UserBean use;
    item it1 = new item("我的订单");
    item it2 = new item("个人设置");
    item it3 = new item("我的收藏");
    item it4 = new item("新版本检测");
    item it5 = new item("我的蛋糕");
    item it6 = new item("我是商家");
//    UserBean p;


    Handler han = new Handler() {
        @SuppressLint("HandlerLeak")
        public void handleMessage(android.os.Message msg) {
            deng.setImageBitmap((Bitmap) msg.obj);
        }
    };
    private View mineview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        mineview = inflater.inflate(R.layout.more, null);
        //*****2
        deng = (ImageView) mineview.findViewById(R.id.imageButton_denglu);
        txt = (TextView) mineview.findViewById(R.id.dianji);
        SharedPreferences sp = getContext().getSharedPreferences("haha", Context.MODE_PRIVATE);
        a = sp.getString("name", "");
        imagepath = sp.getString("image", "");
//        String phone = sp.getString("phone", "");
        if (a.equals("")) {
            txt.setText("点击登录");
        } else {
            txt.setText(a);
            getimage();
        }
        date2.add(it1);
        date2.add(it2);
        date2.add(it3);
        date2.add(it4);
        date1.add(it5);
        date3.add(it6);
        moreList2 = (SortList) mineview.findViewById(R.id.more_list);
        moreList1 = (SortList) mineview.findViewById(R.id.more_listTop);
        moreList3 = (SortList) mineview.findViewById(R.id.more_listBom);
        moreAda2 = new MoreAdapter(getContext(), date2, R.layout.more_list_item);
        moreList2.setAdapter(moreAda2);
        moreAda1 = new MoreAdapter(getContext(), date1, R.layout.more_list_item);
        moreList1.setAdapter(moreAda1);
        moreAda3 = new MoreAdapter(getContext(), date3, R.layout.more_list_item);
        moreList3.setAdapter(moreAda3);

        deng.setOnClickListener(new OnClickListener() {

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


        moreList2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub

                if (!a.equals("")) {
                    if (position == 0) {
                        Intent intent = new Intent(getContext(), Mine_order.class);
                        MineActivity.this.startActivity(intent);
                    }
                    if (position == 1) {

                        {
                            Intent intent = new Intent(getContext(), Mine_person.class);
                            MineActivity.this.startActivity(intent);
                            getActivity().finish();
                        }

                    }
                    if (position == 2) {
                        Intent intent = new Intent(getContext(), Mine_collection.class);
                        MineActivity.this.startActivity(intent);
                    }
                    if (position == 3) {
                        Intent intent = new Intent(getContext(), Mine_new_version.class);
                        MineActivity.this.startActivity(intent);
                    }

                } else {
                    Intent intent = new Intent(getContext(), Zhuce.class);
                    MineActivity.this.startActivity(intent);
                }
            }
        });

        moreList1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub


            }
        });

        moreList3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub

            }
        });
        //*****3给每个选项设置监听
        return mineview;
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
                        Bitmap bit = BitmapFactory.decodeByteArray(data, 0, data.length);
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


    public void setimage() {
    }


    private class MoreAdapter extends BaseAdapter {
        private List<item> date = new ArrayList<item>();
        private int resource;
        private LayoutInflater inflater;

        public MoreAdapter(Context context, List<item> date, int resource) {
            super();
            this.date = date;
            this.resource = resource;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return date.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = inflater.inflate(resource, null);
            } else {

            }
            TextView txt = (TextView) convertView.findViewById(R.id.more_item_Txt);
            item it = new item();
            it = date.get(position);
            txt.setText(it.GetA());
            return convertView;
        }

    }

    class item {
        String a;

        public item() {
            super();
        }

        public item(String a) {
            this.a = a;
        }

        public String GetA() {
            return a;
        }

        public void SetA(String a) {
            this.a = a;
        }
    }
}
