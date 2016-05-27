package com.caozhiliang.mystore;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.caozhiliang.httpdata.ImageData;
import com.caozhiliang.main.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zfdang.multiple_images_selector.ImagesSelectorActivity;
import com.zfdang.multiple_images_selector.SelectorSettings;

import org.xutils.common.Callback;
import org.xutils.common.util.DensityUtil;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author CZL
 * @time 2016-05-17 0:37
 */
public class ImageMore extends Activity {
    private GridView viewpicture;
    public final String URL = "http://119.29.148.150:8080/Fuwu1";
    private String bianh;
    private TextView tv_storepicture;
    private List<ImageData> imagedata;
    ImageOptions imageOptions1;
    ImageOptions imageOptions;
    private ViewPager iv_homepage_viewpager;
    private ImageView imageView;
    private ImageView iv_add;
    private boolean select;
    private Context mContext;
    private static final int REQUEST_CODE = 732;
    private ArrayList<String> mResults = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.imagemore);
        SharedPreferences sp = getSharedPreferences("haha", Context.MODE_PRIVATE);
        bianh = sp.getString("storesnumberss", "");

        initview();
        inintclicklistener();
        getStorePictureData();
    }

    private void initview() {
        viewpicture = (GridView) findViewById(R.id.viewpicture);
        tv_storepicture = (TextView) findViewById(R.id.tv_storepicture);
        iv_add = (ImageView) findViewById(R.id.iv_add);
        iv_homepage_viewpager = (ViewPager) findViewById(R.id.iv_homepage_viewpager);
    }

    private void inintclicklistener() {
        tv_storepicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(ImageMore.this, Store.class);
                startActivity(intent);
                ImageMore.this.finish();
            }
        });
        viewpicture.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            class toViewpager extends PagerAdapter {


                @Override
                public int getCount() {
                    return imagedata.size();
                }

                @Override
                public void destroyItem(ViewGroup container, int position, Object object) {
                    container.removeView((View) object);

                }

                @Override
                public Object instantiateItem(ViewGroup container, int position) {
                    imageView = new ImageView(mContext);
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    imageView.setImageResource(R.mipmap.loge);
                    imageOptions = new ImageOptions.Builder()
                            .setImageScaleType(ImageView.ScaleType.FIT_XY)
                            .setRadius(DensityUtil.dip2px(5))
                            .setLoadingDrawableId(R.mipmap.loge)
                            .setPlaceholderScaleType(ImageView.ScaleType.FIT_XY)
                            .setFailureDrawableId(R.mipmap.loge)
                            .build();

                    x.image().bind(imageView, imagedata.get(position % imagedata.size())
                                    .getImageaddress()
                    );
                    container.addView(imageView);
                    return imageView;
                }

                @Override
                public boolean isViewFromObject(View view, Object object) {
                    return view == object;
                }

            }

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                iv_homepage_viewpager.setVisibility(View.VISIBLE);
                select = true;
                iv_homepage_viewpager.setAdapter(new toViewpager());

                iv_homepage_viewpager.setCurrentItem(position);

            }
        });
        iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ImageMore.this, ImagesSelectorActivity.class);
                // max number of images to be selected
                intent.putExtra(SelectorSettings.SELECTOR_MAX_IMAGE_NUMBER, 5);
                // min size of image which will be shown; to filter tiny images (mainly icons)
                intent.putExtra(SelectorSettings.SELECTOR_MIN_IMAGE_SIZE, 100000);
                // show camera or not
                intent.putExtra(SelectorSettings.SELECTOR_SHOW_CAMERA, true);
                // pass current selected images as the initial value
                intent.putStringArrayListExtra(SelectorSettings.SELECTOR_INITIAL_SELECTED_LIST,
                        mResults);
                // start the selector
                startActivityForResult(intent, REQUEST_CODE);

            }
        });

    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {

            if (select) {
                iv_homepage_viewpager.setVisibility(View.GONE);
                select = false;
            } else {
                Intent intent = new Intent();
                intent.setClass(ImageMore.this, Store.class);
                startActivity(intent);
                ImageMore.this.finish();
            }

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void getStorePictureData() {
        RequestParams storeParams = new RequestParams(URL + "/ImageStoreServlet?Storenumber=" +
                bianh);

        x.http().get(storeParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                Gson gs = new Gson();
                imagedata = gs.fromJson(result, new TypeToken<List<ImageData>>() {
                }.getType());
                viewpicture.setAdapter(new HomeAdapter());
                System.out.println(imagedata.size());
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

    class HomeAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return imagedata.size();
        }

        @Override
        public Object getItem(int position) {
            return imagedata.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(ImageMore.this,
                    R.layout.imagemorelist, null);
            imageOptions1 = new ImageOptions.Builder()
                    .setImageScaleType(ImageView.ScaleType.FIT_XY)
                    .setRadius(DensityUtil.dip2px(5))
                    .setLoadingDrawableId(R.mipmap.loge)
                    .setPlaceholderScaleType(ImageView.ScaleType.FIT_XY)
                    .setFailureDrawableId(R.mipmap.loge)
                    .build();
            ImageView ivItem = (ImageView) view.findViewById(R.id.iv_item);
            x.image().bind(ivItem, imagedata.get(position).getImageaddress(), imageOptions1);
            return view;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                mResults = data.getStringArrayListExtra(SelectorSettings.SELECTOR_RESULTS);
                assert mResults != null;
                RequestParams params = new RequestParams("http://119.29.148" +
                        ".150:8080/Fuwu1/GetImage2?pan=Store&&number=" + bianh);
                for (String result : mResults) {
                    System.out.println(result);
                    params.addBodyParameter("filea" + result, "星星傻逼");
                    params.addBodyParameter("file" + result, new File(result));
                }
                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Toast.makeText(getApplicationContext(), "添加成功", Toast.LENGTH_LONG)
                                .show();
                        getStorePictureData();
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        Toast.makeText(getApplicationContext(), "添加失败", Toast.LENGTH_LONG)
                                .show();
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {
                    }

                    @Override
                    public void onFinished() {
                    }
                });

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
