package com.caozhiliang.main;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
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

    @ViewInject(R.id.rb_diy)
    private RadioButton rb_diy;

    @ViewInject(R.id.rb_search)
    private RadioButton rb_search;
    int keyss;
    private Drawable drawable;
    private Drawable drawablea;
    private Drawable drawableb;
    private Drawable drawablec;
    private Drawable drawabled;
    private boolean isShow= true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        drawable = getResources().getDrawable(R.drawable.home_page);
        drawable.setBounds(0, 0, 2 * (drawable.getIntrinsicWidth()) / 3, 2 * (drawable
                .getIntrinsicHeight())
                / 3);
        drawablea = getResources().getDrawable(R.drawable.location);
        drawablea.setBounds(0, 0, 2 * (drawablea.getIntrinsicWidth()) / 3, 2 * (drawablea
                .getIntrinsicHeight())
                / 3);
        drawableb = getResources().getDrawable(R.drawable.scearch);
        drawableb.setBounds(0, 0, 2 * (drawableb.getIntrinsicWidth()) / 3, 2 * (drawableb
                .getIntrinsicHeight())
                / 3);
        drawablec = getResources().getDrawable(R.drawable.myhome);
        drawablec.setBounds(0, 0, 2 * (drawablec.getIntrinsicWidth()) / 3, 2 * (drawablec
                .getIntrinsicHeight())
                / 3);
        drawabled = getResources().getDrawable(R.mipmap.ddiy);
        drawabled.setBounds(0, 0, 2 * (drawablec.getIntrinsicWidth()) / 3, 2 * (drawablec
                .getIntrinsicHeight())
                / 3);
        rb_home.setCompoundDrawables(null, drawable, null, null);
        rb_location.setCompoundDrawables(null, drawablea, null, null);
        rb_my_home.setCompoundDrawables(null, drawablec, null, null);
        rb_search.setCompoundDrawables(null, drawableb, null, null);
        rb_diy.setCompoundDrawables(null, drawabled, null, null);
        int id = getIntent().getIntExtra("id", 0);

        switch (id) {
            case 1:
                init();
                rb_my_home.performClick();
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
                        if(!isShow) {
                            ScaleAnimation scalez = new ScaleAnimation((float) 2.5, 1, (float) 2.5,
                                    1, Animation
                                    .RELATIVE_TO_SELF, 0.5f,
                                    Animation.RELATIVE_TO_SELF, 0.5f);
                            scalez.setDuration(1000);
                            scalez.setFillAfter(true);
                            rb_diy.startAnimation(scalez);
                            isShow=true;
                        }

                        FragmentManager fragmentmanger = getSupportFragmentManager();
                        //                        拿到FragmentManger对象
                        //开始初始化Fragment
                        FragmentTransaction fragmentTS = fragmentmanger.beginTransaction();
                        HomePage homepage = new HomePage();
                        fragmentTS.replace(R.id.fl, homepage);
                        fragmentTS.commit();
                        break;
                    case R.id.rb_diy:
                        ScaleAnimation scale = new ScaleAnimation(1, (float) 2.5, 1,
                                (float) 2.5, Animation
                                .RELATIVE_TO_SELF, 0.5f,
                                Animation.RELATIVE_TO_SELF, 0.5f);
                        scale.setDuration(1000);
                        scale.setFillAfter(true);
                        rb_diy.startAnimation(scale);
                        isShow = false;
                        FragmentManager fragmentmangera = getSupportFragmentManager();
                        //                        拿到FragmentManger对象
                        //开始初始化Fragment
                        FragmentTransaction fragmentTSa = fragmentmangera.beginTransaction();
                        DIY diy = new DIY();
                        fragmentTSa.replace(R.id.fl, diy);
                        fragmentTSa.commit();

                        break;
                    case R.id.rb_location:
                        if(!isShow) {
                            ScaleAnimation scalec = new ScaleAnimation((float) 2.5, 1, (float) 2.5,
                                    1, Animation
                                    .RELATIVE_TO_SELF, 0.5f,
                                    Animation.RELATIVE_TO_SELF, 0.5f);
                            scalec.setDuration(1000);
                            scalec.setFillAfter(true);
                            rb_diy.startAnimation(scalec);
                            isShow=true;
                        }

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
                        if(!isShow) {
                            ScaleAnimation scalem = new ScaleAnimation((float) 2.5, 1, (float) 2.5,
                                    1, Animation
                                    .RELATIVE_TO_SELF, 0.5f,
                                    Animation.RELATIVE_TO_SELF, 0.5f);
                            scalem.setDuration(1000);
                            scalem.setFillAfter(true);
                            rb_diy.startAnimation(scalem);
                            isShow=true;
                        }


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
                        if(!isShow) {
                            ScaleAnimation scalea = new ScaleAnimation((float) 2.5, 1, (float) 2.5,
                                    1, Animation
                                    .RELATIVE_TO_SELF, 0.5f,
                                    Animation.RELATIVE_TO_SELF, 0.5f);
                            scalea.setDuration(1000);
                            scalea.setFillAfter(true);
                            rb_diy.startAnimation(scalea);
                            isShow=true;
                        }

                        FragmentManager fragmentsearch = getSupportFragmentManager();
                        //                        拿到FragmentManger对象
                        //开始初始化Fragment
                        FragmentTransaction fragmentTsearch = fragmentsearch.beginTransaction();

                        Search search = new Search();
                        Bundle bundle = new Bundle();
                        search.setArguments(bundle);
                        fragmentTsearch.replace(R.id.fl, search);
                        fragmentTsearch.commit();
                        break;


                }


            }
        });


    }


}
