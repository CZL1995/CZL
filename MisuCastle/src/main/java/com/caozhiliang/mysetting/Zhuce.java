package com.caozhiliang.mysetting;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.caozhiliang.httpdata.FinalData;
import com.caozhiliang.main.R;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.Timer;
import java.util.TimerTask;


public class Zhuce extends Activity {
	public EditText edphone;
	public EditText edpwd1,edpwd2;
	public Button btregister1;
	public ImageButton fanhui;
	public String phone,pass,pass2;
	public CheckBox cx;
	public boolean box;
	public String ce;
	public String path= FinalData.FUWU_PATH;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zhuce);
		edphone=(EditText) findViewById(R.id.sign_text);
		edpwd1=(EditText) findViewById(R.id.pwd1);
		edpwd2=(EditText) findViewById(R.id.pwd2);

		btregister1=(Button) findViewById(R.id.btn_zhuce);
		fanhui=(ImageButton) findViewById(R.id.fanhui);
		box=false;
		cx=(CheckBox) findViewById(R.id.checkbox);

		cx.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked)
				{
					box=true;
					btregister1.setBackgroundResource(R.mipmap.zhuce);
				}
				else
				{
					box=false;
					btregister1.setBackgroundResource(R.mipmap.zhuce_fu);
				}
			}
		});
		btregister1.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				// TODO Auto-generated method stub
				if(box){
					if(event.getAction() == MotionEvent.ACTION_DOWN)
					{
						btregister1.setBackgroundResource(R.mipmap.zhuce_fu);
					}else
					{
						btregister1.setBackgroundResource(R.mipmap.zhuce);
					}
				}
				return false;
			}
		});
		btregister1.setBackgroundResource(R.mipmap.zhuce_fu);
		btregister1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				pass=edpwd1.getText().toString();
				pass2=edpwd2.getText().toString();
				// TODO Auto-generated method stub
				if(box){
					if(pass.equals(pass2)){
						get(v);
					}
					else
					{
						Toast.makeText(Zhuce.this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
					}
				}
			}
		});

		//返回按钮
		fanhui.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent in=new Intent();
				in.setClass(Zhuce.this,Denglu.class);
				startActivity(in);
			}
		});
	}



	public void get(View v)
	{

		String phone=edphone.getText().toString();
		String password=edpwd1.getText().toString();
		RequestParams params = new RequestParams(path+"/ZhuceServlet?phone="+phone+"&password="+password);
		x.http().get(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {

				Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
				if(result.equals("注册成功")){
					//Toast.makeText(getApplicationContext(), "注册成功,3秒后自动跳转界面", Toast.LENGTH_LONG).show();
					new Timer().schedule(new TimerTask() {
						@Override
						public void run() {
							Intent intent = new Intent(Zhuce.this , Denglu.class);
							Zhuce.this.startActivity(intent);
							Zhuce.this.finish();
						}
					}, 3000);
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
	
	
	
	/*public boolean addUser(String name,String password) {
		String str="insert into tb_user values(?,?)";
		LoginActivity mActivity=new LoginActivity();
		db=SQLiteDatabase.openOrCreateDatabase(this.getFilesDir().toString()
				+"/test.dbs", null);
		mActivity.db=db;
		try {
			db.execSQL(str,new String[]{name,password});
			return true;
		} catch (Exception e) {
			mActivity.createDb();
		}
		return false;
		
	}*/

}




/*if(!(name.equals("")&&password.equals(""))){
if(addUser(name,password)){
	DialogInterface.OnClickListener ss=new DialogInterface.OnClickListener() {
		
		@Override
		public void onClick(DialogInterface dialog, int which) {
			// 跳转到登录
			Intent in=new Intent();
			in.setClass(Zhuce.this,Denglu.class);
			startActivity(in);
			//销毁当前的activity
			Zhuce.this.onDestroy();
		}
	};
	new AlertDialog.Builder(Zhuce.this).setTitle("注册成功")
	.setMessage("注册成功").setPositiveButton("确定",ss)
	.show();
}else {
	new AlertDialog.Builder(Zhuce.this).setTitle("注册失败")
	.setMessage("注册失败").setPositiveButton("确定",null);
}
}else {
new AlertDialog.Builder(Zhuce.this).setTitle("账号密码不能为空")
.setMessage("账号密码不能为空").setPositiveButton("确定",null);
}*/

