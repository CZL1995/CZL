package com.caozhiliang.mysetting;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.caozhiliang.base.BaseActivity;
import com.caozhiliang.main.MainActivity;
import com.caozhiliang.main.R;

/**
 * @author CZL
 * @time 2016-05-04 14:21
 */
public class NewVersion extends BaseActivity {
    private CheckBox sivUpdate;// 设置升级
    private SharedPreferences mPref;
    private TextView new_version;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newversion);

        mPref = getSharedPreferences("config", MODE_PRIVATE);
        new_version = (TextView) findViewById(R.id.new_version);
        sivUpdate = (CheckBox) findViewById(R.id.id_ck);

        boolean autoUpdate = mPref.getBoolean("update",true);

        if (autoUpdate) {
            sivUpdate.setChecked(true);
        } else {
            sivUpdate.setChecked(false);
        }



        new_version.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(NewVersion.this, MainActivity.class);
                intent.putExtra("id", 1);
                startActivity(intent);
                finish();
            }
        });
        sivUpdate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mPref.edit().putBoolean("update", true).commit();
                } else {
                    mPref.edit().putBoolean("update", false).commit();

                }

            }
        });
//
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            Intent intent = new Intent();
            intent.setClass(NewVersion.this, MainActivity.class);
            intent.putExtra("id", 1);
            startActivity(intent);
            finish();

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
