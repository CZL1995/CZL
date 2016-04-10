package com.caozhiliang.mysetting;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.caozhiliang.main.R;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

public class Mine_person_touxiang extends Activity {

	Button ben,chuan,pai;
	ImageView im;
	String path,a;
	
	Handler han=new Handler()
	  {
		@SuppressLint("HandlerLeak")
		public void handleMessage(Message msg)
		{
			Toast.makeText(Mine_person_touxiang.this, (String)msg.obj, 0).show();
		
		};  
	  };
	
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mine_person_touxiang);
		x.Ext.init(getApplication());
		ben=(Button) findViewById(R.id.button_persontouxiang_ben);
		pai=(Button) findViewById(R.id.button_persontouxiang_paizhao);
		chuan=(Button) findViewById(R.id.button_persontouxiang_chuan);
		im=(ImageView) findViewById(R.id.imageView1);
		SharedPreferences sp=getApplication().getSharedPreferences("haha", MODE_PRIVATE);
		a=sp.getString("phone", "");
		Toast.makeText(getApplicationContext(), a, Toast.LENGTH_LONG).show();
		ben.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(intent, 1);
			}
		});
		chuan.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				chuan(v);
			}
		});
		
		
		
	}
	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		// TODO Auto-generated method stub
		super.onActivityResult(arg0, arg1, arg2);
		if(arg0==1)
		{
			if(arg2==null)
			{
				Toast.makeText(this, "ûѡͼƬ", Toast.LENGTH_LONG).show();
			}
			else
			{
				Uri uri=arg2.getData();
				if(uri==null)
				{
					Toast.makeText(this, "ûѡͼƬuri����", Toast.LENGTH_LONG).show();
				}
				else
				{
					
					String[] po={MediaStore.Images.Media.DATA};
					Cursor cursor=getContentResolver().query(uri, po, null, null, null);
					if(cursor!=null)
					{
						int cut=cursor.getColumnIndexOrThrow(po[0]);
						cursor.moveToFirst();
						path=cursor.getString(cut);
						cursor.close();
					}
				
					if(path==null)
					{
						Toast.makeText(this, "ͼƬ����·������", Toast.LENGTH_LONG).show();	
					}
					else
					{
						Toast.makeText(this, "ͼƬ����·����"+path, Toast.LENGTH_LONG).show();	
					Bitmap bmpDefaultPic;
		            bmpDefaultPic = BitmapFactory.decodeFile(path,null);
		            im.setImageBitmap(bmpDefaultPic);
					
					}
				}
			}
		}
	}
	

	
	public void chuan(View v)
	{
		RequestParams params = new RequestParams("http://119.29.148.150:8080/Fuwu1/GetImage?phone="+a);
		params.addBodyParameter("haha","");   
		params.addBodyParameter("file", new File(path)); 
		x.http().post(params, new Callback.CommonCallback<String>() {
		    @Override
		    public void onSuccess(String result) {
		       
		        
      if(!result.equals("0"))
      {
  	      SharedPreferences sp=getApplication().getSharedPreferences("haha", MODE_PRIVATE);
   	      Editor edt=sp.edit();
		  edt.putString("image",result);
	      edt.commit();
	      Toast.makeText(getApplicationContext(), "�޸ĳɹ�", Toast.LENGTH_LONG).show();
	      new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					Intent intent = new Intent(Mine_person_touxiang.this , MineActivity.class);
					Mine_person_touxiang.this.startActivity(intent);
					System.exit(0);
					Mine_person_touxiang.this.finish();
				}
			}, 2000);
      }
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
	
	
	
	
	
	
	
	
	
	
	
	
//	public void chuan(View v)
//	{
//		Thread t=new Thread()
//		{
//
//			public void run() 
//			{
//		try	
//		{
//			InputStream in=new FileInputStream(new File(path));
//			HttpClient cilent=new DefaultHttpClient();
//			HttpPost post=new HttpPost("http://119.29.148.150:8080/Fuwu1/GetImage");
//			MultipartEntity entity=new MultipartEntity();
//			entity.addPart("file", new InputStreamBody(in, "multipart/form-data","ͼƬ����"));
//			entity.addPart("name", new StringBody(a, Charset.forName("UTF-8")));
//			//entity.addPart("param2", new StringBody("���а�", Charset.forName("UTF-8")));
//			post.setEntity(entity);
//			HttpResponse res=cilent.execute(post);
//			
//			HttpEntity resEntity = res.getEntity();  	
//			String ab=EntityUtils.toString(resEntity);
//			String abc=res.getHeaders("Content-Type")[0].getValue().toString();
//			Message mg=new Message();
//			mg.obj=ab;
//			han.sendMessage(mg);
//	        //System.out.println(IoStreamUtil.getStringFromInputStream(in));  
//			//Toast.makeText(Mine_person_touxiang.this, ab+abc, Toast.LENGTH_LONG).show();	
//		}
//
//		catch(Exception e)
//		{}
//			}
//		};
//		t.start();
//	}
	
	
	
	
	
	
}
