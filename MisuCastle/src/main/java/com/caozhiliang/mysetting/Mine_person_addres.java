package com.caozhiliang.mysetting;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.caozhiliang.httpdata.AddressData;
import com.caozhiliang.httpdata.FinalData;
import com.caozhiliang.main.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;


public class Mine_person_addres extends Activity {
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
                intent.setClass(Mine_person_addres.this, Mine_person_addres_tianjia.class);
                startActivity(intent);
                Mine_person_addres.this.finish();
            }
        });
        addres.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent in = new Intent();
                in.setClass(Mine_person_addres.this, Mine_person.class);
                startActivity(in);
                Mine_person_addres.this.finish();
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            Intent intent = new Intent();
            intent.setClass(Mine_person_addres.this, Mine_person.class);
            startActivity(intent);
            Mine_person_addres.this.finish();

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
            holder.tv_address.setText(list.get(position).getXiangq());
            return convertView;

        }

        class ViewHolder {
            public TextView tv_address_name;
            public TextView tv_address;
        }
    }

}

