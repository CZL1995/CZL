package com.caozhiliang.mysetting;

import android.app.Activity;
import android.content.ContentValues;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.caozhiliang.main.R;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Mine_person_touxiang extends Activity {
    //String path=FinalData.FUWU_PATH;

    Button ben, chuan, pai;
    ImageView im;
    private TextView tv1;
    String path, a;
    Uri photoUri;
    Uri url2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mine_person_touxiang);
        x.Ext.init(getApplication());
        ben = (Button) findViewById(R.id.button_persontouxiang_ben);
        pai = (Button) findViewById(R.id.button_persontouxiang_paizhao);
        chuan = (Button) findViewById(R.id.button_persontouxiang_chuan);
        tv1 = (TextView) findViewById(R.id.tv1);
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
                startActivityForResult(intent, 0);
            }
        });
        pai.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                // 指定开启系统相机的Action
                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                SimpleDateFormat timeStampFormat = new SimpleDateFormat(
                        "yyyy_MM_dd_HH_mm_ss");
                String filename = timeStampFormat.format(new Date());
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.TITLE, filename);

                photoUri = getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);

                startActivityForResult(intent, 4);


            }
        });


        chuan.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                chuan(v);
            }
        });

        tv1.setOnClickListener(new OnClickListener() {
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
            Intent intent = new Intent();
            intent.setClass(Mine_person_touxiang.this, Mine_person.class);
            startActivity(intent);
            Mine_person_touxiang.this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onActivityResult(int arg0, int arg1, Intent arg2) {

        if (arg0 == 0) {

            Uri uri = arg2.getData();
            if (uri == null) {
                Toast.makeText(this, "没选图片,uri不对", Toast.LENGTH_LONG).show();
            } else {
                String[] po = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(uri, po, null, null, null);
                if (cursor != null) {
                    int cut = cursor.getColumnIndexOrThrow(po[0]);
                    cursor.moveToFirst();
                    path = cursor.getString(cut);
                    cursor.close();
                    Bitmap bmpDefaultPic;
                    bmpDefaultPic = BitmapFactory.decodeFile(path, null);
                    System.out.println(path);
                    im.setImageBitmap(bmpDefaultPic);
                }
            }
        } else if (arg0 == 4) {
            if (arg1 == Activity.RESULT_OK) {
                if (arg2 != null && arg2.getData() != null) {
                    url2 = arg2.getData();
                }

                // 一些机型无法从getData中获取uri，则需手动指定拍照后存储照片的Uri
                if (url2 == null) {
                    if (photoUri != null) {
                        url2 = photoUri;
                    }
                }
                String[] po = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(url2, po, null,
                        null, null);
                if (cursor != null) {
                    int cut = cursor.getColumnIndexOrThrow(po[0]);
                    cursor.moveToFirst();
                    path = cursor.getString(cut);
                    cursor.close();
                }
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                Bitmap bitmap = BitmapFactory.decodeFile(path, options); //此时返回 bm 为空
                options.inJustDecodeBounds = false; //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
                int be = (int)(options.outHeight / (float)320);
                if (be <= 0)
                    be = 1;
                options.inSampleSize = be; //重新读入图片，注意此时已经把 options.inJustDecodeBounds 设回 false 了
                bitmap=BitmapFactory.decodeFile(path,options);
                System.out.println(path);
                im.setImageBitmap(bitmap);

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
                    SharedPreferences sp = getApplication().getSharedPreferences("haha",
                            MODE_PRIVATE);
                    Editor edt = sp.edit();
                    edt.putString("image", result);
                    edt.commit();
                    Toast.makeText(getApplicationContext(), "修改成功", Toast.LENGTH_LONG).show();
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(Mine_person_touxiang.this, Mine_person
                                    .class);
                            Mine_person_touxiang.this.startActivity(intent);
                            //                            System.exit(0);
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
