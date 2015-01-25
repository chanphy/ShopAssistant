package com.phy0312.shopassistant.ui.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.phy0312.shopassistant.MainApplication;
import com.phy0312.shopassistant.R;
import com.phy0312.shopassistant.adapter.ActivityAdapter;
import com.phy0312.shopassistant.model.HuoDong;
import com.phy0312.shopassistant.net.JsonCookieSupportRequest;
import com.phy0312.shopassistant.net.RequestResponseDataParseUtil;
import com.phy0312.shopassistant.net.URLManager;
import com.phy0312.shopassistant.tools.Constants;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class ActivityFragment extends BaseFragment {

    private PullToRefreshLayout ptl_container;
    private ListView lv_content;
    private Handler handler;
    private ActivityAdapter adpter;
    private int type = Constants.HOT;
    private LinearLayout ll_empty_view;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_activity, container, false);
        ptl_container = (PullToRefreshLayout) view.findViewById(R.id.ptl_container);
        lv_content = (ListView) view.findViewById(R.id.lv_content);


        ptl_container.setListView(lv_content);
        ptl_container.setUpProgressBar((SmoothProgressBar) view.findViewById(R.id.ptr_progress_up));
        ptl_container.setOnPullRefreshListener(this);

        LayoutInflater lif = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View headerView = lif.inflate(R.layout.list_header_activity, lv_content, false);

        ll_empty_view = (LinearLayout) headerView.findViewById(R.id.ll_empty_view);
        ll_empty_view.setVisibility(View.VISIBLE);

        RadioGroup rg_tab_bar = (RadioGroup) headerView.findViewById(R.id.rg_tab_bar);
        rg_tab_bar.setOnCheckedChangeListener(this);

        switch (type) {
            case Constants.HOT:
                rg_tab_bar.check(R.id.rb_tab_activity_hot);
                break;
            case Constants.LATEST:
                rg_tab_bar.check(R.id.rb_tab_activity_latest);
                break;
            case Constants.NEXTWEEK:
                rg_tab_bar.check(R.id.rb_tab_activity_come_soon);
                break;
            default:
                rg_tab_bar.check(R.id.rb_tab_activity_hot);
                break;
        }


        lv_content.addHeaderView(headerView);
        lv_content.setAdapter(null);
        lv_content.setOnItemClickListener(this);

        final ViewPager viewPager = (ViewPager) headerView.findViewById(R.id.pager);
        final CirclePageIndicator indicator = (CirclePageIndicator) headerView.findViewById(R.id.indicator);
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
        startLoad(false, false, false);
        return view;
    }

    @Override
    protected void startLoad(final boolean isUp, final boolean isBottom, final boolean append) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("CatId", type);
            jsonObject.put("PageIndex", 1);
            jsonObject.put("PageSize", 10);
            jsonObject.put("Type", 1);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonCookieSupportRequest request = new JsonCookieSupportRequest(Request.Method.GET, URLManager.ACTIVITY_LIST, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        new RequestResponseDataParseUtil.ResponseParse() {
                            @Override
                            public void parseResponseDataSection(JSONObject dataJsonObject) {//登录成功处理
                                try {
                                    JSONArray jsonArray = dataJsonObject.getJSONArray("list");
                                    final List<HuoDong> list = new ArrayList<>();
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                                        HuoDong huoDong = new HuoDong();
                                        list.add(huoDong);
                                        huoDong.setCategory(type);
                                        huoDong.setHuoDongId(jsonObject.optString("ID"));
                                        huoDong.setName(jsonObject.optString("Name"));
                                        huoDong.setIconPath(jsonObject.optString("IconUrl"));
                                        huoDong.setStartTime(jsonObject.optString("StartDate"));
                                        huoDong.setEndTime(jsonObject.optString("EndDate"));
                                    }
                                    //更新数据
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (adpter == null) {
                                                adpter = new ActivityAdapter(ActivityFragment.this.getActivity(), list);
                                            } else {
                                                if (append && adpter.getList() != null) {
                                                    adpter.getList().addAll(list);
                                                } else {
                                                    adpter.setList(list);
                                                }

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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        HuoDong huodong = (HuoDong) parent.getItemAtPosition(position);
        Intent intent = new Intent();
        intent.putExtra(ActivityDetailActivty.EXTRA_KEY_ACTIVITY_ID, Integer.parseInt(huodong.getHuoDongId()));
        intent.setClassName(getActivity(), ActivityDetailActivty.class.getName());
        try {
            getActivity().startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_tab_activity_hot:
                if (type != Constants.HOT) {
                    type = Constants.HOT;
                    lv_content.setAdapter(null);
                    startLoad(false, false, false);
                }
                break;
            case R.id.rb_tab_activity_latest:
                if (type != Constants.LATEST) {
                    type = Constants.LATEST;
                    lv_content.setAdapter(null);
                    startLoad(false, false, false);
                }
                break;
            case R.id.rb_tab_activity_come_soon:
                if (type != Constants.NEXTWEEK) {
                    type = Constants.NEXTWEEK;
                    lv_content.setAdapter(null);
                    startLoad(false, false, false);
                }
                break;
            default:
                break;

        }

    }
}
