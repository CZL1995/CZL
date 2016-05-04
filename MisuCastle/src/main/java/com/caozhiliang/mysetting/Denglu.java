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
import com.caozhiliang.httpdata.UserBean;
import com.caozhiliang.main.MainActivity;
import com.caozhiliang.main.R;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.net.URLEncoder;
import java.util.Timer;
import java.util.TimerTask;

public class Denglu extends Activity {
    //账号和密码
    public EditText edphone;
    public EditText edkey;
    public Button btlogin;
    public TextView tvregister;
    public TextView dengl;
    public UserBean use;
    String path = FinalData.FUWU_PATH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.denglu);
        edphone = (EditText) findViewById(R.id.accountEdittext);
        edkey = (EditText) findViewById(R.id.pwdEdittext);
        btlogin = (Button) findViewById(R.id.btn1);
        dengl = (TextView) findViewById(R.id.dengl);
        tvregister = (TextView) findViewById(R.id.login_now);
        x.Ext.init(getApplication());
        //跳转到注册界面
        tvregister.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Denglu.this, Zhuce.class);
                startActivity(intent);
                Denglu.this.finish();
            }
        });
        btlogin.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                getview(v);
            }
        });

        dengl.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Denglu.this, MainActivity.class);
                intent.putExtra("id", 1);
                Denglu.this.startActivity(intent);
                Denglu.this.finish();

            }
        });

    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            Intent intent = new Intent(Denglu.this, MainActivity.class);
            intent.putExtra("id", 1);
            Denglu.this.startActivity(intent);
            Denglu.this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    public void getview(View v) {

        String name = edphone.getText().toString();
        String key = edkey.getText().toString();
        try {
            name = URLEncoder.encode(name, "UTF-8");
            name = URLEncoder.encode(name, "UTF-8");
        } catch (Exception e) {
        }
        RequestParams params = new RequestParams(path + "/DengluServlet?name=" + name +
                "&password=" + key);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gs = new Gson();
                use = gs.fromJson(result, UserBean.class);
                if (use.getName().equals("0")) {
                    Toast.makeText(getApplicationContext(), "输入密码错误", Toast.LENGTH_LONG).show();
                } else if (use.getName().equals("1")) {
                    Toast.makeText(getApplicationContext(), "账号或手机号码不存在", Toast.LENGTH_LONG).show();
                } else {
                    SharedPreferences sp = getApplication().getSharedPreferences("haha",
                            MODE_PRIVATE);
                    Editor edt = sp.edit();
                    edt.putString("name", use.getName());
                    edt.putString("phone", use.getPhone());
                    edt.putString("address", use.getAddress());
                    edt.putString("key", use.getKey());
                    edt.putString("image", use.getImage());
                    edt.putString("bianh", use.getUsernumber());
                    edt.putString("storesnumber", use.getStorenumber());
                    edt.putString("sellersnumber", use.getSellernumber());
                    System.out.println(use.getSellernumber());
                    edt.commit();

                    Toast.makeText(getApplicationContext(), "登录成功,3秒后自动跳转界面", Toast.LENGTH_LONG)
                            .show();
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(Denglu.this, MainActivity.class);

                            intent.putExtra("id", 1);
                            Denglu.this.startActivity(intent);
                            Denglu.this.finish();
                        }
                    }, 2000);

                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(Denglu.this, "网络错误", Toast.LENGTH_SHORT).show();
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


