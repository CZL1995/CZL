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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.caozhiliang.httpdata.FinalData;
import com.caozhiliang.httpdata.UserBean;
import com.caozhiliang.main.R;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.net.URLEncoder;
import java.util.Timer;
import java.util.TimerTask;

public class Denglu extends Activity{
	//账号和密码
	public EditText edphone;
	public EditText edkey;
	public Button btlogin;
	public TextView tvregister;
	public ImageView deng;
	public ImageButton fanhui;
	public UserBean use;
	String yname,yaddres,yphone,ymima,jieguo;
	String ce=null;
	String path= FinalData.FUWU_PATH;
	//	 Handler han=new Handler()
	//	  {
	//		@SuppressLint("HandlerLeak")
	//		public void handleMessage(android.os.Message msg)
	//		{
	//			//txt1.setText((String)msg.obj);
	//		if(ce.equals("登录成功"))
	//		{
	//			Intent in=new Intent();
	//			in.setClass(Denglu.this,Deng_cheng.class);
	//			startActivity(in);
	//			PersonServier2 ps=new PersonServier2(Denglu.this);
	//			ps.gai(use);
	//		}
	//		else{
	//			Toast.makeText(Denglu.this, (String)msg.obj, 0).show();
	//		}
	//		};
	//	  };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.denglu);
		edphone=(EditText) findViewById(R.id.accountEdittext);
		edkey=(EditText) findViewById(R.id.pwdEdittext);
		btlogin=(Button) findViewById(R.id.btn1);
		fanhui=(ImageButton) findViewById(R.id.denglu_back);
		tvregister=(TextView) findViewById(R.id.login_now);
		x.Ext.init(getApplication());
		//跳转到注册界面
		tvregister.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent=new Intent();
				intent.setClass(Denglu.this, Zhuce.class);
				startActivity(intent);
			}
		});
		btlogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getview(v);
			}
		});

		fanhui.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Intent in=new Intent();
//				in.setClass(Denglu.this, TabShow.class);
//				startActivity(in);
			}
		});

	}

	public void getview(View v)
	{

		String name=edphone.getText().toString();
		String key=edkey.getText().toString();
		try{
			name=URLEncoder.encode(name, "UTF-8");
			name=URLEncoder.encode(name, "UTF-8");
		}
		catch(Exception e)
		{}
		RequestParams params = new RequestParams(path+"/DengluServlet?name="+name+"&password="+key);
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {

				// Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
				Gson gs=new Gson();
				use = gs.fromJson(result, UserBean.class);
				if(use.getName().equals("0"))
				{
					Toast.makeText(getApplicationContext(), "输入密码错误", Toast.LENGTH_LONG).show();
				}
				else if(use.getName().equals("1"))
				{
					Toast.makeText(getApplicationContext(), "账号或手机号码不存在", Toast.LENGTH_LONG).show();
				}
				else{
					SharedPreferences sp=getApplication().getSharedPreferences("haha", MODE_PRIVATE);
					Editor edt=sp.edit();
					edt.putString("name",use.getName());
					edt.putString("phone",use.getPhone());
					edt.putString("address",use.getAddress());
					edt.putString("key",use.getKey());
					edt.putString("image",use.getImage());
					edt.commit();

					Toast.makeText(getApplicationContext(), "登录成功,3秒后自动跳转界面", Toast.LENGTH_LONG).show();
					new Timer().schedule(new TimerTask() {
						@Override
						public void run() {
							Intent intent = new Intent(Denglu.this , MineActivity.class);
							Denglu.this.startActivity(intent);
							Denglu.this.finish();
						}
					}, 5000);

				}
			}
			@Override
			public void onError(Throwable ex, boolean isOnCallback) {
			}
			@Override
			public void onCancelled(Callback.CancelledException cex) {
			}
			@Override
			public void onFinished() {
			}
		});

	}









	//创建数据库和用户表
	/*public void createDb() {
		db.execSQL("create table tb_user(name varchar(30) primary key,password varchar(30))");
	}*/

/*public void log(View view){
	Intent it=new Intent(LogActivity.this,TabhostViewpagerActivity.class);
	startActivity(it);
}*/
}


