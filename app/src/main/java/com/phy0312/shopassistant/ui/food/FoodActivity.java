package com.phy0312.shopassistant.ui.food;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.HeaderViewListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.phy0312.shopassistant.MainApplication;
import com.phy0312.shopassistant.R;
import com.phy0312.shopassistant.adapter.ActivityAdapter;
import com.phy0312.shopassistant.adapter.CouponAdapter;
import com.phy0312.shopassistant.adapter.FoodStoreAdapter;
import com.phy0312.shopassistant.data.DataManager;
import com.phy0312.shopassistant.db.Coupon;
import com.phy0312.shopassistant.db.HuoDong;
import com.phy0312.shopassistant.db.Store;
import com.phy0312.shopassistant.net.JsonCookieSupportRequest;
import com.phy0312.shopassistant.net.RequestResponseDataParseUtil;
import com.phy0312.shopassistant.net.URLManager;
import com.phy0312.shopassistant.tools.AndroidUtil;
import com.phy0312.shopassistant.tools.Constants;
import com.phy0312.shopassistant.ui.activity.ActivityDetailActivty;
import com.phy0312.shopassistant.ui.base.BaseFragment;
import com.phy0312.shopassistant.ui.base.BaseFragmentActivity;
import com.phy0312.shopassistant.ui.base.UIUtil;
import com.phy0312.shopassistant.ui.coupon.CouponDetailActivity;
import com.phy0312.shopassistant.view.PullToRefreshLayout;
import com.phy0312.shopassistant.view.smoothprogressbar.SmoothProgressBar;
import com.phy0312.shopassistant.view.viewpagerindicator.CirclePageIndicator;

import org.json.JSONObject;
import java.util.List;

/**
 * description: 美食主activity<br/>
 * author: Administrator<br/>
 * date: 2014/11/25<br/>
 */
public class FoodActivity extends BaseFragmentActivity {

    @Override
    public Fragment getPlaceholderFragment() {
        return new PlaceholderFragment();
    }

    static public class PlaceholderFragment extends BaseFragment {

        public static final int STORE = 0;
        public static final int COUPON = 1;
        public static final int ACTIVITY = 2;
        public static final int DEAL = 3;

        private PullToRefreshLayout ptl_container;
        private ListView lv_content;
        private FoodStoreAdapter foodStoreAdapter;
        private CouponAdapter couponAdater;
        private ActivityAdapter activityAdater;
        private BaseAdapter currentAdater;
        private int type = STORE;
        private LinearLayout ll_empty_view;


        public PlaceholderFragment() {

        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_food, container, false);

            ImageView iv_go_back = (ImageView) view.findViewById(R.id.iv_go_back);
            iv_go_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().finish();
                }
            });

            ((TextView) view.findViewById(R.id.tv_title)).setText(getString(R.string.menu_title_food));

            ptl_container = (PullToRefreshLayout) view.findViewById(R.id.ptl_container);
            lv_content = (ListView) view.findViewById(R.id.lv_content);
            ptl_container.setListView(lv_content);
            ptl_container.setUpProgressBar((SmoothProgressBar) view.findViewById(R.id.ptr_progress_up));
            ptl_container.setOnPullRefreshListener(this);

            LayoutInflater lif = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View headerView = lif.inflate(R.layout.list_header_food, lv_content, false);

            ll_empty_view = (LinearLayout)headerView.findViewById(R.id.ll_empty_view);
            ll_empty_view.setVisibility(View.VISIBLE);

            initRadioGroup(headerView);


            lv_content.addHeaderView(headerView);
            lv_content.setAdapter(null);
            lv_content.setOnItemClickListener(this);

            ViewPager viewPager = (ViewPager) headerView.findViewById(R.id.pager);
            UIUtil.initAdsBanner(getActivity(), viewPager);
            CirclePageIndicator indicator = (CirclePageIndicator) headerView.findViewById(R.id.indicator);
            indicator.setViewPager(viewPager);
            startLoad(false, false, false);

            return view;
        }

        private void initRadioGroup(View headerView) {
            RadioGroup rg_tab_bar = (RadioGroup) headerView.findViewById(R.id.rg_tab_bar);
            rg_tab_bar.setOnCheckedChangeListener(this);
            switch (type) {
                case STORE:
                    rg_tab_bar.check(R.id.rb_tab_store);
                    currentAdater = foodStoreAdapter;
                    break;
                case COUPON:
                    rg_tab_bar.check(R.id.rb_tab_coupon);
                    currentAdater = couponAdater;
                    break;
                case ACTIVITY:
                    currentAdater = activityAdater;
                    rg_tab_bar.check(R.id.rb_tab_activity);
                    break;
                case DEAL:
                    //todo
                    currentAdater = foodStoreAdapter;
                    rg_tab_bar.check(R.id.rb_tab_deal);
                    break;
                default:
                    currentAdater = foodStoreAdapter;
                    rg_tab_bar.check(R.id.rb_tab_store);
                    break;
            }
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch (type) {
                case STORE:
                    FoodStoreAdapter adapter = (FoodStoreAdapter) ((HeaderViewListAdapter) parent.getAdapter()).getWrappedAdapter();
                    Store store = adapter.getList().get(position);
                    Intent intent = new Intent();
                    intent.putExtra(Constants.TRANSFER_BUNDLE_STORE, store);
                    intent.setClassName(getActivity(), FoodStoreDetailActivity.class.getName());
                    AndroidUtil.startActivity(getActivity(), intent);
                    break;
                case COUPON:
                    CouponAdapter couponAdapter = (CouponAdapter) ((HeaderViewListAdapter) parent.getAdapter()).getWrappedAdapter();
                    Coupon coupon = couponAdapter.getList().get(position);
                    intent = new Intent();
                    intent.putExtra(Constants.TRANSFER_BUNDLE_COUPON, coupon);
                    intent.setClassName(getActivity(), CouponDetailActivity.class.getName());
                    AndroidUtil.startActivity(getActivity(), intent);
                    break;
                case ACTIVITY:
                    ActivityAdapter activityAdapter = (ActivityAdapter) ((HeaderViewListAdapter) parent.getAdapter()).getWrappedAdapter();
                    HuoDong huoDong = activityAdapter.getList().get(position);
                    intent = new Intent();
                    intent.putExtra(Constants.TRANSFER_BUNDLE_ACTIVITY, huoDong);
                    intent.setClassName(getActivity(), ActivityDetailActivty.class.getName());
                    AndroidUtil.startActivity(getActivity(), intent);
                    break;
                case DEAL:

                    break;
                default:

                    break;
            }

        }

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.rb_tab_store:
                    if (type != STORE) {
                        type = STORE;
                        currentAdater = null;
                        lv_content.setAdapter(null);
                        startLoad(false, false, false);
                    }
                    break;
                case R.id.rb_tab_coupon:
                    if (type != COUPON) {
                        type = COUPON;
                        currentAdater = null;
                        lv_content.setAdapter(null);
                        startLoad(false, false, false);
                    }
                    break;
                case R.id.rb_tab_activity:
                    if (type != ACTIVITY) {
                        type = ACTIVITY;
                        currentAdater = null;
                        lv_content.setAdapter(null);
                        startLoad(false, false, false);
                    }
                    break;
                case R.id.rb_tab_deal:
                    if (type != DEAL) {
                        type = DEAL;
                        currentAdater = null;
                        lv_content.setAdapter(null);
                        startLoad(false, false, false);
                    }
                    break;
                default:
                    break;

            }
        }

        @Override
        protected void startLoad(final boolean isUp, final boolean isBottom, final boolean append) {
            switch (type) {
                case STORE:
                    JSONObject jsonObject = new JSONObject();
                    JsonCookieSupportRequest request = new JsonCookieSupportRequest(Request.Method.POST, URLManager.FOOD_STORE_LIST, jsonObject,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject jsonObject) {
                                    new RequestResponseDataParseUtil.ResponseParse() {
                                        @Override
                                        public void parseResponseDataSection(JSONObject dataJsonObject) {//成功处理
                                            updateFoodStoreView(isUp, isBottom, append);
                                        }
                                    }.onResponse(jsonObject);
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError volleyError) {
                                    updateFoodStoreView(isUp, isBottom, append);
                                }
                            }) {
                        @Override
                        public String getCacheKey() {
                            return super.getCacheKey();
                        }
                    };
                    MainApplication.appContext.getRequestQueue().add(request);
                    break;
                case COUPON:
                    jsonObject = new JSONObject();
                    request = new JsonCookieSupportRequest(Request.Method.POST, URLManager.COUPON_LIST, jsonObject,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject jsonObject) {
                                    new RequestResponseDataParseUtil.ResponseParse() {
                                        @Override
                                        public void parseResponseDataSection(JSONObject dataJsonObject) {//成功处理
                                            updateConponView(isUp, isBottom, append);
                                        }
                                    }.onResponse(jsonObject);
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError volleyError) {
                                    updateConponView(isUp, isBottom, append);
                                }
                            }) {
                        @Override
                        public String getCacheKey() {
                            return super.getCacheKey();
                        }
                    };
                    MainApplication.appContext.getRequestQueue().add(request);
                    break;
                case ACTIVITY:
                    jsonObject = new JSONObject();
                    request = new JsonCookieSupportRequest(Request.Method.POST, URLManager.ACTIVITY_LIST, jsonObject,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject jsonObject) {
                                    new RequestResponseDataParseUtil.ResponseParse() {
                                        @Override
                                        public void parseResponseDataSection(JSONObject dataJsonObject) {//成功处理
                                            updateActivityView(isUp, isBottom, append);
                                        }
                                    }.onResponse(jsonObject);
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError volleyError) {
                                    updateActivityView(isUp, isBottom, append);
                                }
                            }) {
                        @Override
                        public String getCacheKey() {
                            return super.getCacheKey();
                        }
                    };
                    MainApplication.appContext.getRequestQueue().add(request);
                    break;
                case DEAL:
                    //todo
                    currentAdater = foodStoreAdapter;

                    break;
                default:
                    currentAdater = foodStoreAdapter;

                    break;

            }

        }

        private void updateFoodStoreView(boolean isUp, boolean isBottom, boolean append) {
            final List<Store> list = DataManager.getFoodStores();
            if (foodStoreAdapter == null) {
               foodStoreAdapter = new FoodStoreAdapter(PlaceholderFragment.this.getActivity(), list);
            }

            currentAdater = foodStoreAdapter;
            if (append && foodStoreAdapter.getList() != null) {
                foodStoreAdapter.getList().addAll(list);
            } else {
                foodStoreAdapter.setList(list);
            }

            lv_content.setAdapter(currentAdater);
            currentAdater.notifyDataSetChanged();

            if (isUp) {
                ptl_container.setRefreshUpEnd();
            }

            if (isBottom) {
                ptl_container.setRefreshingBottomEnd();
            }
            ll_empty_view.setVisibility(View.GONE);
        }


        private void updateConponView(boolean isUp, boolean isBottom, boolean append) {
            final List<Coupon> list = DataManager.getCoupons();
            if(couponAdater == null) {
                couponAdater = new CouponAdapter(PlaceholderFragment.this.getActivity(), list);
            }

            currentAdater = couponAdater;
            if (append && couponAdater.getList() != null) {
                couponAdater.getList().addAll(list);
            } else {
                couponAdater.setList(list);
            }

            lv_content.setAdapter(currentAdater);
            currentAdater.notifyDataSetChanged();

            if (isUp) {
                ptl_container.setRefreshUpEnd();
            }

            if (isBottom) {
                ptl_container.setRefreshingBottomEnd();
            }
            ll_empty_view.setVisibility(View.GONE);
        }

        private void updateActivityView(boolean isUp, boolean isBottom, boolean append) {
            final List<HuoDong> list = DataManager.getHuoDongs(Constants.HOT);
            if(activityAdater == null) {
                activityAdater = new ActivityAdapter(PlaceholderFragment.this.getActivity(), list);
            }
            currentAdater = activityAdater;
            if (append && activityAdater.getList() != null) {
                activityAdater.getList().addAll(list);
            } else {
                activityAdater.setList(list);
            }

            lv_content.setAdapter(currentAdater);
            currentAdater.notifyDataSetChanged();

            if (isUp) {
                ptl_container.setRefreshUpEnd();
            }

            if (isBottom) {
                ptl_container.setRefreshingBottomEnd();
            }
            ll_empty_view.setVisibility(View.GONE);
        }
    }

}
