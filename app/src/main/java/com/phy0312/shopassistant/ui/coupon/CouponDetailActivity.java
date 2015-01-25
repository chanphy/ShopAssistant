package com.phy0312.shopassistant.ui.coupon;

import android.app.Activity;
import android.os.Bundle;
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
import com.phy0312.shopassistant.model.Coupon;
import com.phy0312.shopassistant.net.JsonCookieSupportRequest;
import com.phy0312.shopassistant.net.RequestResponseDataParseUtil;
import com.phy0312.shopassistant.net.URLManager;
import com.phy0312.shopassistant.tools.ImageLoaderUtil;
import com.phy0312.shopassistant.ui.share.ShareActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CouponDetailActivity extends Activity {

    public static String EXTRA_KEY_COUPON_ID = "coupon_id";
    DisplayImageOptions options;
    private ImageView iv_coupon_photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.fragment_coupon_detail);
        iv_coupon_photo = (ImageView) findViewById(R.id.iv_coupon_photo);
        options = ImageLoaderUtil.newDisplayImageOptionsInstance();
        ((TextView) findViewById(R.id.tv_title)).setText(getString(R.string.title_activity_coupon_detail));
        int couponId = getIntent().getIntExtra(EXTRA_KEY_COUPON_ID, -1);
        if (couponId != -1) {
            loadData(couponId);
        }

        //后退按钮
        findViewById(R.id.iv_go_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.iv_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(ShareActivity.buildIntent(CouponDetailActivity.this, 4, "", "商品", "商品描述", "http://pic25.nipic.com/20121119/11328459_121121530346_2.jpg"));
            }
        });
    }

    private void loadData(int couponId) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("CouponId", couponId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonCookieSupportRequest request = new JsonCookieSupportRequest(Request.Method.GET, URLManager.COUPON_DETAIL_LIST, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        new RequestResponseDataParseUtil.ResponseParse() {
                            @Override
                            public void parseResponseDataSection(JSONObject dataJsonObject) {
                                try {
                                    Coupon coupon = new Coupon();
                                    coupon.setName(dataJsonObject.optString("Name"));
                                    coupon.setStartTime(dataJsonObject.optString("StartDate"));
                                    coupon.setEndTime(dataJsonObject.optString("EndDate"));
                                    coupon.setDescription(dataJsonObject.optString("Desc"));

                                    JSONArray jsonArray = dataJsonObject.getJSONArray("list");
                                    if(jsonArray.length() > 0) {
                                        JSONObject imgObject = jsonArray.getJSONObject(0);
                                        String iconUrl = imgObject.optString("IconUrl");
                                        ImageLoader.getInstance().displayImage(iconUrl, iv_coupon_photo, options);
                                    }

                                    //更新数据
                                    ((TextView) findViewById(R.id.tv_coupon_name)).setText(coupon.getName());
                                    ((TextView) findViewById(R.id.tv_coupon_summary)).setText(coupon.getName());
                                    ((TextView) findViewById(R.id.tv_coupon_valid_time)).setText(coupon.getStartTime() + "至" +
                                            coupon.getEndTime());
                                    ((TextView) findViewById(R.id.tv_coupon_note)).setText(coupon.getDescription());

                                    ((TextView) findViewById(R.id.tv_store_name)).setText(dataJsonObject.optString("ShopName"));
                                    ((TextView) findViewById(R.id.tv_store_number)).setText(dataJsonObject.optString("ShopAddress"));
                                    ((TextView) findViewById(R.id.tv_store_telephone)).setText(dataJsonObject.optString("ShopPhone"));
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
