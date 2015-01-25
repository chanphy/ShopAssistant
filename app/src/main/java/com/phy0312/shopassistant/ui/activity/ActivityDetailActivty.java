package com.phy0312.shopassistant.ui.activity;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.phy0312.shopassistant.MainApplication;
import com.phy0312.shopassistant.R;
import com.phy0312.shopassistant.model.HuoDong;
import com.phy0312.shopassistant.net.JsonCookieSupportRequest;
import com.phy0312.shopassistant.net.RequestResponseDataParseUtil;
import com.phy0312.shopassistant.net.URLManager;
import com.phy0312.shopassistant.tools.ImageLoaderUtil;
import com.phy0312.shopassistant.ui.share.ShareActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * description: 活动详情页面<br/>
 * author: dingdj<br/>
 * date: 2014/11/17<br/>
 */
public class ActivityDetailActivty extends Activity {

    public static final String EXTRA_KEY_ACTIVITY_ID = "key_activity_id";
    private ImageView iv_activity_photo;
    DisplayImageOptions options;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.detail_activity);
        ((TextView) findViewById(R.id.tv_title)).setText(getString(R.string.title_activity_huo_dong_detail));
        options = ImageLoaderUtil.newDisplayImageOptionsInstance();
        iv_activity_photo = (ImageView) findViewById(R.id.iv_activity_photo);
        int huodongId = getIntent().getIntExtra(EXTRA_KEY_ACTIVITY_ID, -1);
        if (huodongId != -1) {
            loadData(huodongId);
        }

        //后退按钮
        findViewById(R.id.iv_go_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityDetailActivty.this.finish();
            }
        });

        //分享按钮
        findViewById(R.id.iv_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(ShareActivity.buildIntent(ActivityDetailActivty.this, 4,
                        "",
                        "商品",
                        "商品描述",
                        "http://pic25.nipic.com/20121119/11328459_121121530346_2.jpg"));
            }
        });

    }

    private void loadData(int huodongId) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("HuoDongId", huodongId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonCookieSupportRequest request = new JsonCookieSupportRequest(Request.Method.GET, URLManager.ACTIVITY_DETAIL_LIST, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        new RequestResponseDataParseUtil.ResponseParse() {
                            @Override
                            public void parseResponseDataSection(JSONObject dataJsonObject) {
                                try {
                                    HuoDong huoDong = new HuoDong();
                                    huoDong.setName(dataJsonObject.optString("Name"));
                                    huoDong.setStartTime(dataJsonObject.optString("StartDate"));
                                    huoDong.setEndTime(dataJsonObject.optString("EndDate"));
                                    huoDong.setDescription(dataJsonObject.optString("Desc"));

                                    JSONArray jsonArray = dataJsonObject.getJSONArray("list");
                                    if(jsonArray.length() > 0) {
                                        JSONObject imgObject = jsonArray.getJSONObject(0);
                                        String iconUrl = imgObject.optString("IconUrl");
                                        ImageLoader.getInstance().displayImage(iconUrl, iv_activity_photo, options);
                                    }

                                    //更新数据
                                    ((TextView) findViewById(R.id.tv_activity_name)).setText(huoDong.getName());
                                    ((TextView) findViewById(R.id.tv_activity_description)).setText(huoDong.getName());
                                    ((TextView) findViewById(R.id.tv_activity_valid_time)).setText(huoDong.getStartTime() + "至" +
                                            huoDong.getEndTime());
                                    ((TextView) findViewById(R.id.tv_store_name)).setText(dataJsonObject.optString("ShopName"));
                                    ((TextView) findViewById(R.id.tv_store_number)).setText(dataJsonObject.optString("ShopAddress"));
                                    ((TextView) findViewById(R.id.tv_store_telephone)).setText(dataJsonObject.optString("ShopPhone"));
                                    ((TextView) findViewById(R.id.tv_activity_detail)).setText(huoDong.getDescription());
                                    dismissLoadingView();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }.onResponse(jsonObject);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                });
        MainApplication.appContext.getRequestQueue().add(request);
    }

    /**
     * 消除加载的view
     */
    private void dismissLoadingView() {
        findViewById(R.id.ll_wait_layout).setVisibility(View.GONE);
    }

}
