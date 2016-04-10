package com.caozhiliang.mysetting;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.caozhiliang.httpdata.UserBean;
import com.caozhiliang.main.R;
import com.caozhiliang.sql.PersonServier2;


public class Mine_person_addres extends Activity{
private Button bc;
private ImageButton fanhui;
private PersonServier2 ps;
UserBean p;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mine_person_addres);
		bc=(Button) findViewById(R.id.person_addres_add);
		fanhui=(ImageButton) findViewById(R.id.person_addres_back);
		ps=new PersonServier2(Mine_person_addres.this);
		p=ps.cha();
		TextView text=(TextView) findViewById(R.id.Mine_person_addres);
		//text.setText(p.getAddres());
        bc.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent();
				intent.setClass(Mine_person_addres.this, Mine_person_addres_tianjia.class);
				startActivity(intent);
			}
		});
        

       fanhui.setOnClickListener(new OnClickListener() {
         			
         			@Override
         			public void onClick(View v) {
         				// TODO Auto-generated method stub
         				Intent in=new Intent();
         			    in.setClass(Mine_person_addres.this, Mine_person.class);
         				startActivity(in);
         			}
         		});
		
	}
}

