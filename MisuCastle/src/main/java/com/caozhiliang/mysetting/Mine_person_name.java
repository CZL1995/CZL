package com.caozhiliang.mysetting;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.caozhiliang.httpdata.FinalData;
import com.caozhiliang.main.R;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.net.URLEncoder;
import java.util.Timer;
import java.util.TimerTask;

public class Mine_person_name extends Activity {
	private Button bc;
	private ImageButton fanhui;
	private EditText name;
	private String a1,namegai;
	String a,b;
	private String path= FinalData.FUWU_PATH;
	private Editor edt;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mine_person_nicheng);
		bc=(Button) findViewById(R.id.btnname);
		name=(EditText) findViewById(R.id.minenamegai);
		fanhui=(ImageButton) findViewById(R.id.person_name_back);
		SharedPreferences sp=getApplication().getSharedPreferences("haha", MODE_PRIVATE);
		a=sp.getString("phone", "");
		b=sp.getString("name", "");
		edt =sp.edit();
		name.setText(b);
		bc.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				/*Intent intent=new Intent();
				intent.setClass(Mine_person_nicheng.this, Mine_person.class);
				startActivity(intent);*/
				get(v);
			}
		});

		//返回按钮
		fanhui.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent=new Intent();
				intent.setClass(Mine_person_name.this, Mine_person.class);
				startActivity(intent);
			}
		});

	}

	public void get(View v)
	{
		namegai=name.getText().toString();
		try{
			namegai=URLEncoder.encode(namegai, "UTF-8");
			namegai=URLEncoder.encode(namegai, "UTF-8");
		}
		catch(Exception e){}
		RequestParams params = new RequestParams(path+"/GaiServlet?namegai="+namegai+"&phone="+a);
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
					edt.putString("name",name.getText().toString());
					edt.commit();
				}
				new Timer().schedule(new TimerTask() {
					@Override
					public void run() {
						Intent intent = new Intent(Mine_person_name.this , Mine_person.class);
						Mine_person_name.this .startActivity(intent);
						Mine_person_name.this .finish();
					}
				}, 2000);
			}});

	}





}
