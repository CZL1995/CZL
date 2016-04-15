package com.caozhiliang.mysetting;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.caozhiliang.main.R;


public class Mine_person_addres extends Activity {
    private Button person_addres_add;
    private TextView addres;
    private ListView lv_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mine_person_addres);
        person_addres_add = (Button) findViewById(R.id.person_addres_add);
        addres = (TextView) findViewById(R.id.addres);
        lv_address = (ListView) findViewById(R.id.lv_address);
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
}

