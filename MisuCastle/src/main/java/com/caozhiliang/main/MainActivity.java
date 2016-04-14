package com.caozhiliang.main;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.caozhiliang.base.BaseActivity;
import com.caozhiliang.location.Location;
import com.caozhiliang.mysetting.MineActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    @ViewInject(R.id.rg)
    private RadioGroup rg;

    @ViewInject(R.id.rb_home)
    private RadioButton rb_home;

    @ViewInject(R.id.rb_location)
    private RadioButton rb_location;

    @ViewInject(R.id.rb_my_home)
    private RadioButton rb_my_home;

    @ViewInject(R.id.rb_search)
    private RadioButton rb_search;
    int keyss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int id = getIntent().getIntExtra("id", 0);

        switch (id) {
            case 1:
                init();
                rb_my_home.performClick();
                break;
            case 2:
                keyss = getIntent().getIntExtra("pos", 0);
                System.out.println(keyss);
                init();
                rb_search.performClick();

                break;
            default:
                init();
                rb_home.performClick();
                break;
        }
    }


    private void init() {

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_home:
                        FragmentManager fragmentmanger = getSupportFragmentManager();
                        //                        拿到FragmentManger对象
                        //开始初始化Fragment
                        FragmentTransaction fragmentTS = fragmentmanger.beginTransaction();
                        HomePage homepage = new HomePage();
                        fragmentTS.replace(R.id.fl, homepage);
                        fragmentTS.commit();
                        break;
                    case R.id.rb_location:
                        FragmentManager fragmentlocation = getSupportFragmentManager();
                        //                        拿到FragmentManger对象
                        //开始初始化Fragment
                        FragmentTransaction fragmentTlocation = fragmentlocation
                                .beginTransaction();

                        Location location = new Location();
                        fragmentTlocation.replace(R.id.fl, location);
                        fragmentTlocation.commit();
                        break;
                    case R.id.rb_my_home:
                        FragmentManager fragmentmohome = getSupportFragmentManager();
                        //                        拿到FragmentManger对象
                        //开始初始化Fragment
                        FragmentTransaction fragmentTmyhome = fragmentmohome
                                .beginTransaction();

                        MineActivity myhome = new MineActivity();
                        fragmentTmyhome.replace(R.id.fl, myhome);
                        fragmentTmyhome.commit();
                        break;
                    case R.id.rb_search:
                        FragmentManager fragmentsearch = getSupportFragmentManager();
                        //                        拿到FragmentManger对象
                        //开始初始化Fragment
                        FragmentTransaction fragmentTsearch = fragmentsearch.beginTransaction();

                        Search search = new Search();
                        Bundle bundle = new Bundle();
                        bundle.putInt("keyq", keyss);
                        search.setArguments(bundle);
                        fragmentTsearch.replace(R.id.fl, search);
                        fragmentTsearch.commit();
                        break;


                }


            }
        });


    }


}
