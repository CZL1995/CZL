package com.caozhiliang.mysetting;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.caozhiliang.httpdata.FinalData;
import com.caozhiliang.main.R;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.Timer;
import java.util.TimerTask;

public class Mine_person_key extends Activity{
	private Button bc;
	private TextView newphone;
	private EditText passworda,passwordb;
	String passworgai;
	private String a1;
	String password2;
	String a;
	private String path= FinalData.FUWU_PATH;
	private Editor edt;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mine_person_key);
		bc=(Button) findViewById(R.id.passgai_button);
		newphone= (TextView) findViewById(R.id.newphone);
		passworda=(EditText) findViewById(R.id.password1);
		passwordb=(EditText) findViewById(R.id.password2);
		SharedPreferences sp=getApplication().getSharedPreferences("haha", MODE_PRIVATE);
		a=sp.getString("phone", "");
		edt =sp.edit();
		bc.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(passworda.getText().toString().equals(passwordb.getText().toString()))
				{get(v);}
				else
				{
					Toast.makeText(Mine_person_key.this, "两次输入密码不一致",Toast.LENGTH_SHORT).show();
				}

			}
		});



		//返回按钮
		newphone.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent=new Intent();
				intent.setClass(Mine_person_key.this, Mine_person.class);
				startActivity(intent);
				Mine_person_key.this.finish();
			}
		});
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getRepeatCount() == 0) {
			Intent intent=new Intent();
			intent.setClass(Mine_person_key.this, Mine_person.class);
			startActivity(intent);
			Mine_person_key.this.finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}



	public void get(View v)
	{
		passworgai=passworda.getText().toString();
		RequestParams params = new RequestParams(path+"/GaiServlet?passwordgai="+passworgai+"&phone="+a);
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onCancelled(CancelledException arg0) {}
			@Override
			public void onError(Throwable arg0, boolean arg1) {}
			@Override
			public void onFinished() {}
			@Override
			public void onSuccess(String arg0) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), arg0, Toast.LENGTH_LONG).show();
				if(arg0.equals("修改成功"))
				{
					edt.putString("key",passworgai);
					edt.commit();
				}
				new Timer().schedule(new TimerTask() {
					@Override
					public void run() {
						Intent intent = new Intent(Mine_person_key.this , Mine_person.class);
						Mine_person_key.this .startActivity(intent);
						Mine_person_key.this .finish();
					}
				}, 2000);
			}});

	}



}
