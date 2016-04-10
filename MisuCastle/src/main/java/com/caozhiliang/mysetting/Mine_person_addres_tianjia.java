package com.caozhiliang.mysetting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

import com.caozhiliang.main.R;


public class Mine_person_addres_tianjia extends Activity{
	private Button bc;
	private ImageButton fanhui;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mine_person_ad_dizhi);
		bc=(Button) findViewById(R.id.btn12);
		fanhui=(ImageButton) findViewById(R.id.fanhui);
		bc.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent=new Intent();
				intent.setClass(Mine_person_addres_tianjia.this, Mine_person_addres.class);
				startActivity(intent);
			}
		});
		
	
	/*View fanhui;
			//返回按钮
			fanhui.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent in=new Intent();
					//in.setClass(TianJia.this,  .class);
					startActivity(in);
				}
			});*/
	}
}
