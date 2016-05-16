package com.caozhiliang.fragment;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.caozhiliang.httpdata.AddressData;
import com.caozhiliang.httpdata.FinalData;
import com.caozhiliang.main.R;
import com.caozhiliang.mysetting.Mine_person_addres_tianjia;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class Order_person_addres extends Activity {
    private Button person_addres_add;
    private TextView addres;
    private ListView lv_address;
    private String bianh;
    private List<AddressData> list;
    private listAdapter mlistadapter;
    private String path = FinalData.FUWU_PATH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mine_person_addres);
        SharedPreferences sp = getApplication().getSharedPreferences("haha", MODE_PRIVATE);
        bianh = sp.getString("bianh", "");
        initData();
        initview();
        initlistenter();
    }

    private void initData() {
        RequestParams params = new RequestParams(path + "/AddressServlet?pan=cha&bianh=" + bianh);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gs = new Gson();
                list = gs.fromJson(result, new TypeToken<List<AddressData>>() {
                }.getType());
                System.out.println(list);
                mlistadapter = new listAdapter();
                lv_address.setAdapter(mlistadapter);
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

    private void initlistenter() {
        person_addres_add.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Order_person_addres.this, Mine_person_addres_tianjia.class);
                startActivity(intent);
                Order_person_addres.this.finish();
            }
        });
        addres.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent in = new Intent();
                in.setClass(Order_person_addres.this, Order.class);
                startActivity(in);
                Order_person_addres.this.finish();
            }
        });
        lv_address.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println(position);
                Toast.makeText(Order_person_addres.this, "长按设置默认地址", Toast.LENGTH_SHORT).show();
            }
        });
        lv_address.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long
                    id) {
                SharedPreferences sp = getApplication().getSharedPreferences("haha",
                        MODE_PRIVATE);
                SharedPreferences.Editor edt = sp.edit();
                edt.putString("addressname", list.get(position).getName());
                edt.putString("addressdetails", list.get(position).getShengf() +
                        list.get(position).getChengs() + list.get(position).getXianc()
                        + list.get(position).getXiangq());
                System.out.println(list.get(position).getAddnumber());
                edt.putString("addressnumber", list.get(position).getAddnumber());
                edt.commit();
                Toast.makeText(Order_person_addres.this, "设置完成", Toast.LENGTH_SHORT).show();
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Intent intent = new Intent();
                        intent.setClass(Order_person_addres.this, Order.class);
                        startActivity(intent);
                        Order_person_addres.this.finish();

                    }
                }, 1000);
                return true;
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            Intent intent = new Intent();
            intent.setClass(Order_person_addres.this, Order.class);
            startActivity(intent);
            Order_person_addres.this.finish();

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void initview() {
        person_addres_add = (Button) findViewById(R.id.person_addres_add);
        addres = (TextView) findViewById(R.id.addres);
        lv_address = (ListView) findViewById(R.id.lv_address);
    }

    class listAdapter extends BaseAdapter {
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
            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(getApplicationContext(), R.layout.address_list, null);
                holder = new ViewHolder();
                holder.tv_address_name = (TextView) convertView.findViewById(R.id.tv_address_name);
                holder.tv_address = (TextView) convertView.findViewById(R.id.tv_address);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();

            }
            holder.tv_address_name.setText(list.get(position).getName());
            holder.tv_address.setText(list.get(position).getShengf() +
                    list.get(position).getChengs() + list.get(position).getXianc()
                    + list.get(position).getXiangq());
            return convertView;

        }

        class ViewHolder {
            public TextView tv_address_name;
            public TextView tv_address;
        }
    }

}

