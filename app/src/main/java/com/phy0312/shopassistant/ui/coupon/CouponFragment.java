package com.phy0312.shopassistant.ui.coupon;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.phy0312.shopassistant.MainApplication;
import com.phy0312.shopassistant.R;
import com.phy0312.shopassistant.adapter.CouponAdapter;
import com.phy0312.shopassistant.model.Coupon;
import com.phy0312.shopassistant.net.JsonCookieSupportRequest;
import com.phy0312.shopassistant.net.RequestResponseDataParseUtil;
import com.phy0312.shopassistant.net.URLManager;
import com.phy0312.shopassistant.ui.base.BaseFragment;
import com.phy0312.shopassistant.ui.base.UIUtil;
import com.phy0312.shopassistant.view.PullToRefreshLayout;
import com.phy0312.shopassistant.view.smoothprogressbar.SmoothProgressBar;
import com.phy0312.shopassistant.view.viewpagerindicator.CirclePageIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class CouponFragment extends BaseFragment {

    private PullToRefreshLayout ptl_container;
    private ListView lv_content;
    private ViewPager viewPager;
    private CirclePageIndicator indicator;
    private Handler handler;
    private Spinner sp_category_name;
    private Spinner sp_category_sort;
    private CouponAdapter adpter;
    private LinearLayout ll_empty_view;


    public CouponFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_coupon, container, false);
        ptl_container = (PullToRefreshLayout) view.findViewById(R.id.ptl_container);
        lv_content = (ListView) view.findViewById(R.id.lv_content);
        ptl_container.setListView(lv_content);
        ptl_container.setUpProgressBar((SmoothProgressBar) view.findViewById(R.id.ptr_progress_up));
        ptl_container.setOnPullRefreshListener(this);

        LayoutInflater lif = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View headerView = lif.inflate(R.layout.list_header_coupon, lv_content, false);

        ll_empty_view = (LinearLayout) headerView.findViewById(R.id.ll_empty_view);
        ll_empty_view.setVisibility(View.VISIBLE);

        lv_content.addHeaderView(headerView);
        lv_content.setAdapter(null);
        lv_content.setOnItemClickListener(this);

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
                                        Log.e("dsdf", itemType.iconUrl);
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

                    }
                });
        MainApplication.appContext.getRequestQueue().add(request);


        initSpinners(view);
        startLoad(false, false, false);
        return view;
    }

    private void initSpinners(View view) {
        sp_category_name = (Spinner) view.findViewById(R.id.sp_category_name);
        sp_category_sort = (Spinner) view.findViewById(R.id.sp_category_sort);

        sp_category_name.setAdapter(new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_dropdown_item,
                this.getResources().getStringArray(R.array.coupon_categoty)));

        sp_category_sort.setAdapter(new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_dropdown_item,
                this.getResources().getStringArray(R.array.coupon_order)));

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent();
        Coupon coupon = (Coupon) parent.getAdapter().getItem(position);
        intent.putExtra(CouponDetailActivity.EXTRA_KEY_COUPON_ID, Integer.parseInt(coupon.getCouponId()));
        intent.setClassName(getActivity(), CouponDetailActivity.class.getName());
        try {
            getActivity().startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void startLoad(final boolean isUp, final boolean isBottom, final boolean append) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("CatId", sp_category_name.getSelectedItemPosition());
            jsonObject.put("PageIndex", 1);
            jsonObject.put("PageSize", 10);
            jsonObject.put("Type", sp_category_sort.getSelectedItemPosition());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonCookieSupportRequest request = new JsonCookieSupportRequest(Request.Method.GET, URLManager.COUPON_LIST, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        new RequestResponseDataParseUtil.ResponseParse() {
                            @Override
                            public void parseResponseDataSection(JSONObject dataJsonObject) {//登录成功处理
                                try {
                                    JSONArray jsonArray = dataJsonObject.getJSONArray("list");
                                    final List<Coupon> list = new ArrayList<>();
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                                        Coupon coupon = new Coupon();
                                        list.add(coupon);
                                        coupon.setCategory(sp_category_name.getSelectedItemPosition());
                                        coupon.setCouponId(jsonObject.optString("ID"));
                                        coupon.setName(jsonObject.optString("Name"));
                                        coupon.setIcon(jsonObject.optString("IconUrl"));
                                        coupon.setCount(jsonObject.optInt("Used"));
                                    }
                                    //更新数据
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (adpter == null) {
                                                adpter = new CouponAdapter(CouponFragment.this.getActivity(), list);
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
                                            ll_empty_view.setVisibility(View.GONE);
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
    public void onCheckedChanged(RadioGroup group, int checkedId) {

    }
}
