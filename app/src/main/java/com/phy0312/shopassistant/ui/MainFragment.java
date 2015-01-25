package com.phy0312.shopassistant.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.phy0312.shopassistant.MainApplication;
import com.phy0312.shopassistant.R;
import com.phy0312.shopassistant.adapter.MainItemAdpter;
import com.phy0312.shopassistant.data.DataManager;
import com.phy0312.shopassistant.model.MainColumnGroup;
import com.phy0312.shopassistant.model.MainColumnInfo;
import com.phy0312.shopassistant.net.JsonCookieSupportRequest;
import com.phy0312.shopassistant.net.RequestResponseDataParseUtil;
import com.phy0312.shopassistant.net.URLManager;
import com.phy0312.shopassistant.tools.AndroidUtil;
import com.phy0312.shopassistant.tools.Constants;
import com.phy0312.shopassistant.tools.ImageLoaderUtil;
import com.phy0312.shopassistant.tools.LogUtil;
import com.phy0312.shopassistant.tools.ThreadUtil;
import com.phy0312.shopassistant.ui.base.BaseFragment;
import com.phy0312.shopassistant.ui.base.UIUtil;
import com.phy0312.shopassistant.ui.food.FoodActivity;
import com.phy0312.shopassistant.ui.product.ProductActivity;
import com.phy0312.shopassistant.ui.store.StoreActivity;
import com.phy0312.shopassistant.view.PullToRefreshLayout;
import com.phy0312.shopassistant.view.smoothprogressbar.SmoothProgressBar;
import com.phy0312.shopassistant.view.viewpagerindicator.CirclePageIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends BaseFragment {


    private PullToRefreshLayout ptl_container;
    private ListView lv_content;
    private ViewPager viewPager;
    private CirclePageIndicator indicator;
    private Handler handler;
    private MainItemAdpter adpter;
    DisplayImageOptions options;
    TextView tv_food;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        handler = new Handler();

        options = ImageLoaderUtil.newDisplayImageOptionsInstance();

        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ptl_container = (PullToRefreshLayout) view.findViewById(R.id.ptl_container);
        lv_content = (ListView) view.findViewById(R.id.lv_content);
        lv_content.setSelector(new ColorDrawable(Color.TRANSPARENT));
        ptl_container.setListView(lv_content);
        SmoothProgressBar ptr_progress_up = (SmoothProgressBar) view.findViewById(R.id.ptr_progress_up);
        ptl_container.setUpProgressBar(ptr_progress_up);
        ptl_container.setOnPullRefreshListener(this);

        LayoutInflater lif = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View headerView = lif.inflate(R.layout.main_header, lv_content, false);

        lv_content.addHeaderView(headerView);
        lv_content.setAdapter(adpter);

        tv_food = (TextView) headerView.findViewById(R.id.tv_food);
        tv_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClassName(MainFragment.this.getActivity(), FoodActivity.class.getName());
                AndroidUtil.startActivity(MainFragment.this.getActivity(), intent);

            }
        });

        initProduct(R.id.tv_product_dress, headerView);
        initProduct(R.id.tv_product_shoes, headerView);
        initProduct(R.id.tv_product_liren, headerView);
        initProduct(R.id.tv_product_baby, headerView);
        initProduct(R.id.tv_product_outside, headerView);
        initProduct(R.id.tv_product_other, headerView);

        TextView textView = (TextView) headerView.findViewById(R.id.tv_product_daily);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClassName(MainFragment.this.getActivity(), StoreActivity.class.getName());
                AndroidUtil.startActivity(MainFragment.this.getActivity(), intent);
            }
        });

        viewPager = (ViewPager) headerView.findViewById(R.id.pager);
        indicator = (CirclePageIndicator) headerView.findViewById(R.id.indicator);
        JsonCookieSupportRequest request = new JsonCookieSupportRequest(Request.Method.GET, URLManager.ADS_URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        new RequestResponseDataParseUtil.ResponseParse() {
                            @Override
                            public void parseResponseDataSection(JSONObject dataJsonObject) {//登录成功处理
                                try {
                                    JSONArray jsonArray = dataJsonObject.getJSONArray("list");
                                    final List<UIUtil.AdsItemType> datas = new ArrayList<>();
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                                        UIUtil.AdsItemType itemType = new UIUtil.AdsItemType();
                                        datas.add(itemType);
                                        itemType.id = jsonObject.optString("ID");
                                        itemType.iconUrl = jsonObject.optString("IconUrl");
                                        itemType.type = jsonObject.optInt("Category");
                                    }

                                    UIUtil.initAdsBanner(viewPager, datas);
                                    indicator.setViewPager(viewPager);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }.onResponse(jsonObject);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        LogUtil.e(volleyError.getMessage());
                    }
                });
        MainApplication.appContext.getRequestQueue().add(request);
        startLoad(false, false);
        return view;
    }

    private void initProduct(int resId, View headerView) {
        final TextView textView = (TextView) headerView.findViewById(resId);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(ProductActivity.PRODUCT_TITLE, textView.getText());
                intent.setClassName(MainFragment.this.getActivity(), ProductActivity.class.getName());
                AndroidUtil.startActivity(MainFragment.this.getActivity(), intent);

            }
        });
    }


    @Override
    public void onRefreshingUp() {
        startLoad(true, false);
    }

    @Override
    public void onRefreshingBottom() {
        startLoad(false, true);
    }


    /**
     * 开始加载数据
     */
    private void startLoad(final boolean isUp, final boolean isBottom) {
        JsonCookieSupportRequest request = new JsonCookieSupportRequest(Request.Method.GET, URLManager.MAIN_FRAME_URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        new RequestResponseDataParseUtil.ResponseParse() {
                            @Override
                            public void parseResponseDataSection(JSONObject dataJsonObject) {//登录成功处理
                                try {
                                    final List<MainColumnGroup> list = new ArrayList<MainColumnGroup>();

                                    JSONArray jsonArray = dataJsonObject.getJSONArray("RecommendList");
                                    MainColumnInfo[] recommendColumns = new MainColumnInfo[jsonArray.length()];
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                                        recommendColumns[i] = new MainColumnInfo(
                                                jsonObject.optInt("Category"),
                                                jsonObject.optInt("ID"),
                                                jsonObject.optString("Title"),
                                                jsonObject.optString("Desc"),
                                                jsonObject.optString("IconUrl"));
                                    }
                                    MainColumnGroup recommendGroup = new MainColumnGroup(recommendColumns, Constants.CATEGORY_WEEKRECOMMEND);
                                    list.add(recommendGroup);


                                    jsonArray = dataJsonObject.getJSONArray("CouponList");
                                    MainColumnInfo[] couponColumns = new MainColumnInfo[jsonArray.length()];
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                                        couponColumns[i] = new MainColumnInfo(
                                                jsonObject.optInt("Category"),
                                                jsonObject.optInt("ID"),
                                                jsonObject.optString("Title"),
                                                jsonObject.optString("Desc"),
                                                jsonObject.optString("IconUrl"));
                                    }
                                    MainColumnGroup couponGroup = new MainColumnGroup(couponColumns, Constants.CATEGORY_COUPON);
                                    list.add(couponGroup);

                                    jsonArray = dataJsonObject.getJSONArray("HuoDongList");
                                    MainColumnInfo[] huodongColumns = new MainColumnInfo[jsonArray.length()];
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                                        huodongColumns[i] = new MainColumnInfo(
                                                jsonObject.optInt("Category"),
                                                jsonObject.optInt("ID"),
                                                jsonObject.optString("Title"),
                                                jsonObject.optString("Desc"),
                                                jsonObject.optString("IconUrl"));
                                    }
                                    MainColumnGroup huodongGroup = new MainColumnGroup(huodongColumns, Constants.CATEGOTY_ACTIVITY);
                                    list.add(huodongGroup);

                                    //更新数据
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (adpter == null) {
                                                adpter = new MainItemAdpter(list, MainFragment.this.getActivity(), MainFragment.this);
                                            } else {
                                                adpter.setList(list);
                                            }
                                            lv_content.setAdapter(adpter);
                                            adpter.notifyDataSetChanged();

                                            if (isUp) {
                                                ptl_container.setRefreshUpEnd();
                                            }

                                            if (isBottom) {
                                                ptl_container.setRefreshingBottomEnd();
                                            }
                                        }
                                    });
                               } catch (JSONException e) {
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

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
