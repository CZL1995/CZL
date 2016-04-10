package com.caozhiliang.mysetting;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.caozhiliang.httpdata.FinalData;
import com.caozhiliang.httpdata.UserBean;
import com.caozhiliang.main.R;
import com.caozhiliang.tools.StreamTool;
import com.caozhiliang.view.SortList;

import org.xutils.x;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;



public class Mine_person extends Activity {
	private static final int List1_ID = 1; // 最顶端的 图片显示
	private static final int List2_ID = 2 ;  // 中间分类
	private static final int List3_ID = 3;  // 下端显示
	String a,image,password,imagepath;
	String path= FinalData.FUWU_PATH;
	public UserBean use;
	SortList List1 ,List2,List3;
	LayoutInflater inflater;
	SharedPreferences shared;
	SortAdapter personAdapter1,personAdapter2,personAdapter3;
	HoldViewSwitch switchHold;
	AlertDialog.Builder Dialog;
	Touxiang tou1;
	SharedPreferences sp;
	ImageButton fanhui;
	Button tuichu;
	Bitmap btmap=null;
	ImageView imag;
	private int List1Data[][]=new int [][]{
			{R.mipmap.touxiang,R.string.person_ziliao1_imagai}};
	private int List2Data[][]=new int [][]{

			{R.string.person_ziliao1_name,R.string.person_ziliao1_gai},
			{R.string.person_ziliao1_phone,R.string.person_ziliao1_gai},
			{R.string.person_ziliao1_Password,R.string.person_ziliao1_gai}
	};
	private int List3Data[][]=new int [][]{
			{R.string.person_ziliao1_dizhi,R.string.person_ziliao1_gai}	};
	private String ziliao[]=new String[5];

	Handler han=new Handler()
	{
		@SuppressLint("HandlerLeak")
		public void handleMessage(android.os.Message msg)
		{
			imag=(ImageView) List1.getChildAt(0).findViewById(R.id.imageViewtou1);
			imag.setImageBitmap((Bitmap)msg.obj);
		};
	};


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		x.Ext.init(getApplication());
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mine_person);
		fanhui=(ImageButton) findViewById(R.id.person_back);
		tuichu=(Button) findViewById(R.id.tuichu_button);
		SharedPreferences sp=getApplication().getSharedPreferences("haha", MODE_PRIVATE);
		//chuan();
		tuichu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dilg();
			}
		});


		initResourceRefs();
		initSettings();
		ziliao[0]=sp.getString("name", "");
		ziliao[1]=sp.getString("phone", "");
		ziliao[2]=sp.getString("key", "");
		ziliao[3]=sp.getString("address", "");
		ziliao[4]=sp.getString("image", "");
		imagepath=ziliao[4];
		getimage();
		//ps.close();
		//PersonServier2 ps=new PersonServier2(Mine_person.this);

	}

	public void dilg()
	{
		Dialog = new AlertDialog.Builder(this);
		Dialog.setMessage("确定退出当前账号吗");
		Dialog.setTitle("提示");
		Dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				SharedPreferences sp=getApplication().getSharedPreferences("haha", MODE_PRIVATE);
				Editor edt=sp.edit();
				edt.clear();
				edt.commit();
				Intent in=new Intent();
				in.setClass(Mine_person.this,MineActivity.class);
				startActivity(in);
			}
		});
		Dialog.setNegativeButton("取消",null);
		Dialog.create().show();
	}
	public void getimage()
	{
		Thread t=new Thread()
		{
			public void run()
			{
				try {
					URL url=new URL(imagepath);
					HttpURLConnection con=(HttpURLConnection) url.openConnection();
					con.setConnectTimeout(5000);
					con.setRequestMethod("GET");
					if(con.getResponseCode()==200)
					{
						InputStream in=con.getInputStream();
						StreamTool st=new StreamTool();
						byte[] data=st.read(in);
						Bitmap bit=BitmapFactory.decodeByteArray(data, 0, data.length);
						Message mg=new Message();
						mg.obj=bit;
						han.sendMessage(mg);
					}

				} catch (Exception e) {

				}
			}
		};
		t.start();
	}


















	public void initResourceRefs()
	{
		List1=(SortList) findViewById(R.id.person_one);
		List2=(SortList) findViewById(R.id.person_two);
		List3=(SortList) findViewById(R.id.person_three);
		inflater = LayoutInflater.from(Mine_person.this);
		personAdapter1=new SortAdapter(List1_ID);
		personAdapter2=new SortAdapter(List2_ID);
		personAdapter3=new SortAdapter(List3_ID);
	}
	public void initSettings()
	{
		List1.setAdapter(personAdapter1);
		List2.setAdapter(personAdapter2);
		List3.setAdapter(personAdapter3);
		List1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Mine_person.this , Mine_person_touxiang.class);
				Mine_person.this.startActivity(intent);
			}
		});
		List2.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				// TODO Auto-generated method stub
				if(position==0){
					Intent intent = new Intent(Mine_person.this , Mine_person_name.class);
					Mine_person.this.startActivity(intent);
					Mine_person.this.finish();
				}

				if(position==1){
					Intent intent = new Intent(Mine_person.this , Mine_person_phone.class);
					Mine_person.this.startActivity(intent);
					Mine_person.this.finish();
				}
				if(position==2){
					Intent intent = new Intent(Mine_person.this , Mine_person_key.class);
					Mine_person.this.startActivity(intent);
					Mine_person.this.finish();
				}
			}
		});
		List3.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Mine_person.this , Mine_person_addres.class);
				Mine_person.this.startActivity(intent);
				Mine_person.this.finish();
			}
		});
		fanhui.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent=new Intent();
				intent.setClass(Mine_person.this, MineActivity.class);
				startActivity(intent);
				Mine_person.this.finish();
			}
		});


	}
	private class SortAdapter extends BaseAdapter{
		int SORT_ID;
		public SortAdapter(int id ){
			SORT_ID = id ;
		}
		@Override
		public int getCount() {
			if(SORT_ID == List1_ID){
				return List1Data.length;
			}
			else if(SORT_ID == List2_ID)
			{return List2Data.length;}
			else{
				return List3Data.length;
			}

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

			if(SORT_ID == List1_ID){
				return getList1View(convertView, position);}
			else if(SORT_ID == List2_ID)
			{return getList2View(convertView, position);}
			else
			{   return getList3View(convertView, position);}
		}

		private View getList1View( View convertView ,int position){


			tou1 = new Touxiang();
			if(convertView == null){
				convertView = inflater.inflate(R.layout.person_ziliao_itemtou, null);
				//tou1.img1 =(ImageView)convertView.findViewById(R.id.imageViewtou1);
				tou1.text =(TextView)convertView.findViewById(R.id.person_one_ziliao_tou);
				convertView.setTag(tou1);
			}else{
				tou1 = (Touxiang)convertView.getTag();
			}
			if(btmap!=null)
			{
				tou1.img1.setImageBitmap(btmap);
			}
			tou1.text.setText(List1Data[position][1]);
			return convertView;
		}




		private View getList2View( View convertView ,int position){

			switchHold = new HoldViewSwitch();
			if(convertView == null){
				convertView = inflater.inflate(R.layout.person_ziliao_item, null);
				switchHold.text2 =(TextView)convertView.findViewById(R.id.person_one_ziliao2);
				switchHold.text1 =(TextView)convertView.findViewById(R.id.person_one_ziliao1);
				switchHold.textxiao=(TextView)convertView.findViewById(R.id.person_ziliao_xiao);
				convertView.setTag(switchHold);
			}else{
				switchHold = (HoldViewSwitch)convertView.getTag();
			}
			switchHold.text1.setText(List2Data[position][0]);
			switchHold.text2.setText(List2Data[position][1]);
			switchHold.textxiao.setText(ziliao[position]);
			return convertView;

		}

		private View getList3View( View convertView ,int position){

			switchHold = new HoldViewSwitch();
			if(convertView == null){
				convertView = inflater.inflate(R.layout.person_ziliao_item, null);
				switchHold.text2 =(TextView)convertView.findViewById(R.id.person_one_ziliao2);
				switchHold.text1 =(TextView)convertView.findViewById(R.id.person_one_ziliao1);

				convertView.setTag(switchHold);
			}else{
				switchHold = (HoldViewSwitch)convertView.getTag();
			}
			switchHold.text1.setText(List3Data[position][0]);
			switchHold.text2.setText(List3Data[position][1]);

			return convertView;

		}



	}

}

class HoldViewSwitch{
	TextView text2;
	TextView text1;
	TextView textxiao;

}
class Touxiang{
	ImageView img1 ;

	TextView text;

}







