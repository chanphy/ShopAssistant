package com.phy0312.shopassistant.ui.food;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.HeaderViewListAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.phy0312.shopassistant.R;
import com.phy0312.shopassistant.adapter.FoodStoreAdapter;
import com.phy0312.shopassistant.data.DataManager;
import com.phy0312.shopassistant.db.Store;
import com.phy0312.shopassistant.tools.AndroidUtil;
import com.phy0312.shopassistant.tools.Constants;
import com.phy0312.shopassistant.tools.ThreadUtil;
import com.phy0312.shopassistant.ui.base.UIUtil;
import com.phy0312.shopassistant.view.PullToRefreshLayout;
import com.phy0312.shopassistant.view.smoothprogressbar.SmoothProgressBar;
import com.phy0312.shopassistant.view.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * description: 美食主activity<br/>
 * author: Administrator<br/>
 * date: 2014/11/25<br/>
 */
public class Food extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_activity_fragment_container);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new FoodFragment())
                    .commit();
        }
    }


    static public class FoodFragment extends Fragment implements PullToRefreshLayout.PullRefreshListener,
            AdapterView.OnItemClickListener, RadioGroup.OnCheckedChangeListener {

        public static final int STORE = 0;
        public static final int COUPON = 1;
        public static final int ACTIVITY = 2;
        public static final int DEAL = 3;

        private PullToRefreshLayout ptl_container;
        private ListView lv_content;
        List<ImageView> viewList = new ArrayList<ImageView>();
        private Handler handler = new Handler();
        private FoodStoreAdapter adpter;
        private int type = STORE;


        public FoodFragment() {

        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_food, container, false);

            ImageView iv_go_back = (ImageView)view.findViewById(R.id.iv_go_back);
            iv_go_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().finish();
                }
            });

            ((TextView)view.findViewById(R.id.tv_title)).setText(getString(R.string.menu_title_food));

            ptl_container = (PullToRefreshLayout) view.findViewById(R.id.ptl_container);
            lv_content = (ListView) view.findViewById(R.id.lv_content);
            ptl_container.setListView(lv_content);
            ptl_container.setUpProgressBar((SmoothProgressBar) view.findViewById(R.id.ptr_progress_up));
            ptl_container.setOnPullRefreshListener(this);

            LayoutInflater lif = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View headerView = lif.inflate(R.layout.list_header_food, lv_content, false);

            RadioGroup rg_tab_bar = (RadioGroup) headerView.findViewById(R.id.rg_tab_bar);
            rg_tab_bar.setOnCheckedChangeListener(this);
            switch (type) {
                case STORE:
                    rg_tab_bar.check(R.id.rb_tab_store);
                    break;
                case COUPON:
                    rg_tab_bar.check(R.id.rb_tab_coupon);
                    break;
                case ACTIVITY:
                    rg_tab_bar.check(R.id.rb_tab_activity);
                    break;
                case DEAL:
                    rg_tab_bar.check(R.id.rb_tab_deal);
                    break;
                default:
                    rg_tab_bar.check(R.id.rb_tab_store);
                    break;
            }


            lv_content.addHeaderView(headerView);
            lv_content.setAdapter(null);
            lv_content.setOnItemClickListener(this);

            ViewPager viewPager = (ViewPager) headerView.findViewById(R.id.pager);
            UIUtil.initAdsBanner(getActivity(), viewList, viewPager);
            CirclePageIndicator indicator = (CirclePageIndicator) headerView.findViewById(R.id.indicator);
            indicator.setViewPager(viewPager);
            startLoad(false, false, false);

            return view;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch (type) {
                case STORE:
                    FoodStoreAdapter adapter = (FoodStoreAdapter)((HeaderViewListAdapter)parent.getAdapter()).getWrappedAdapter();
                    Store store = adapter.getList().get(position);
                    Intent intent = new Intent();
                    intent.putExtra(Constants.TRANSFER_BUNDLE_STORE, store);
                    intent.setClassName(getActivity(), FoodStore.class.getName());
                    AndroidUtil.startActivity(getActivity(), intent);
                    break;
                case COUPON:

                    break;
                case ACTIVITY:

                    break;
                case DEAL:

                    break;
                default:

                    break;
            }

        }

        @Override
        public void onRefreshingUp() {

        }

        @Override
        public void onRefreshingBottom() {

        }

        private void startLoad(final boolean isUp, final boolean isBottom, final boolean append) {
            ThreadUtil.executeMore(new Runnable() {

                @Override
                public void run() {
                    final List<Store> list = DataManager.getFoodStores();
                    //更新数据
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (adpter == null) {
                                adpter = new FoodStoreAdapter(FoodFragment.this.getActivity(), list);
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
                        }
                    });
                }
            });
        }

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.rb_tab_store:
                    if (type != STORE) {
                        type = STORE;
                        lv_content.setAdapter(null);
                        startLoad(false, false, false);
                    }
                    break;
                case R.id.rb_tab_coupon:
                    if (type != COUPON) {
                        type = COUPON;
                        lv_content.setAdapter(null);
                        startLoad(false, false, false);
                    }
                    break;
                case R.id.rb_tab_activity:
                    if (type != ACTIVITY) {
                        type = ACTIVITY;
                        lv_content.setAdapter(null);
                        startLoad(false, false, false);
                    }
                    break;
                case R.id.rb_tab_deal:
                    if (type != DEAL) {
                        type = DEAL;
                        lv_content.setAdapter(null);
                        startLoad(false, false, false);
                    }
                    break;
                default:
                    break;

            }
        }
    }



}
