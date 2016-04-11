package com.caozhiliang.mysetting;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.caozhiliang.main.MainActivity;
import com.caozhiliang.main.R;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

public class Mine_person_touxiang extends Activity {
    //String path=FinalData.FUWU_PATH;

    Button ben, chuan, pai;
    ImageView im;
    private ImageButton fanhui;
    String path, a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mine_person_touxiang);
        x.Ext.init(getApplication());
        ben = (Button) findViewById(R.id.button_persontouxiang_ben);
        pai = (Button) findViewById(R.id.button_persontouxiang_paizhao);
        chuan = (Button) findViewById(R.id.button_persontouxiang_chuan);
        fanhui = (ImageButton) findViewById(R.id.fanhui);
        im = (ImageView) findViewById(R.id.imageView1);
        SharedPreferences sp = getApplication().getSharedPreferences("haha", MODE_PRIVATE);
        a = sp.getString("phone", "");
        //		Toast.makeText(getApplicationContext(), a, Toast.LENGTH_LONG).show();
        ben.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images
                        .Media.EXTERNAL_CONTENT_URI);
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

        fanhui.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Mine_person_touxiang.this, Mine_person.class);
                startActivity(intent);
                Mine_person_touxiang.this.finish();
            }
        });


    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            Intent intent=new Intent();
            intent.setClass(Mine_person_touxiang.this, Mine_person.class);
            startActivity(intent);
            Mine_person_touxiang.this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }





    @Override
    protected void onActivityResult(int arg0, int arg1, Intent arg2) {
        // TODO Auto-generated method stub
        super.onActivityResult(arg0, arg1, arg2);
        if (arg0 == 1) {
            if (arg2 == null) {
                Toast.makeText(this, "没选图片", Toast.LENGTH_LONG).show();
            } else {
                Uri uri = arg2.getData();
                if (uri == null) {
                    Toast.makeText(this, "没选图片uri不对", Toast.LENGTH_LONG).show();
                } else {

                    String[] po = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(uri, po, null, null, null);
                    if (cursor != null) {
                        int cut = cursor.getColumnIndexOrThrow(po[0]);
                        cursor.moveToFirst();
                        path = cursor.getString(cut);
                        cursor.close();
                    }

                    if (path == null) {
                        Toast.makeText(this, "图片物理路径不在", Toast.LENGTH_LONG).show();
                    } else {
                        //						Toast.makeText(this, "图片物理路径是"+path, Toast
                        // .LENGTH_LONG).show();
                        Bitmap bmpDefaultPic;
                        bmpDefaultPic = BitmapFactory.decodeFile(path, null);
                        im.setImageBitmap(bmpDefaultPic);

                    }
                }
            }
        }
    }


    public void chuan(View v) {
        RequestParams params = new RequestParams("http://119.29.148" +
				".150:8080/Fuwu1/GetImage?phone=" + a);
        params.addBodyParameter("haha", "");
        params.addBodyParameter("file", new File(path));
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {


                if (!result.equals("0")) {
                    SharedPreferences sp = getApplication().getSharedPreferences("haha", MODE_PRIVATE);
                    Editor edt = sp.edit();
                    edt.putString("image", result);
                    edt.commit();
                    Toast.makeText(getApplicationContext(), "修改成功", Toast.LENGTH_LONG).show();
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(Mine_person_touxiang.this, Mine_person.class);
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
            public void onCancelled(Callback.CancelledException cex) {
            }

            @Override
            public void onFinished() {
            }
        });

    }


}
