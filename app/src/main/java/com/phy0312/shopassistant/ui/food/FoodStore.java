package com.phy0312.shopassistant.ui.food;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.phy0312.shopassistant.R;
import com.phy0312.shopassistant.adapter.CouponAdapter;
import com.phy0312.shopassistant.data.DataManager;
import com.phy0312.shopassistant.db.Coupon;
import com.phy0312.shopassistant.db.Store;
import com.phy0312.shopassistant.tools.Constants;
import com.phy0312.shopassistant.tools.ImageLoaderUtil;
import com.phy0312.shopassistant.tools.ThreadUtil;
import com.phy0312.shopassistant.view.HorizontalListView;
import com.phy0312.shopassistant.view.PullToRefreshLayout;
import com.phy0312.shopassistant.view.smoothprogressbar.SmoothProgressBar;

import java.util.List;

public class FoodStore extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_activity_fragment_container);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements PullToRefreshLayout.PullRefreshListener,
            AdapterView.OnItemClickListener, RadioGroup.OnCheckedChangeListener {

        private int type = Constants.CATEGORY_COUPON;
        private PullToRefreshLayout ptl_container;
        private ListView lv_content;
        private Handler handler;
        private HorizontalListView hlv_store_advertise_dishes;

        private ImageView iv_store_photo;
        private TextView tv_store_name;
        private TextView tv_store_short_name;
        private TextView tv_store_price;
        private TextView tv_store_phone;
        private TextView tv_store_number;

        DisplayImageOptions options;


        private Store store;

        private CouponAdapter adapter;

        public PlaceholderFragment() {
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            handler = new Handler();
            options = ImageLoaderUtil.newDisplayImageOptionsInstance();
            store = getActivity().getIntent().getParcelableExtra(Constants.TRANSFER_BUNDLE_STORE);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_food_store, container, false);
            ((TextView) view.findViewById(R.id.tv_title)).setText(getActivity().getString(R.string.food_store_detail));

            ptl_container = (PullToRefreshLayout) view.findViewById(R.id.ptl_container);
            lv_content = (ListView) view.findViewById(R.id.lv_content);
            ptl_container.setListView(lv_content);
            ptl_container.setUpProgressBar((SmoothProgressBar) view.findViewById(R.id.ptr_progress_up));
            ptl_container.setOnPullRefreshListener(this);

            //后退按钮
            view.findViewById(R.id.iv_go_back).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().finish();
                }
            });

            hlv_store_advertise_dishes = (HorizontalListView) view.findViewById(R.id.hlv_store_advertise_dishes);

            View headerView = inflater.inflate(R.layout.list_header_food_store, lv_content, false);
            lv_content.addHeaderView(headerView);
            lv_content.setAdapter(null);

            initRadioGroup(headerView);

            iv_store_photo = (ImageView) headerView.findViewById(R.id.iv_store_photo);
            tv_store_name = (TextView) headerView.findViewById(R.id.tv_store_name);
            tv_store_short_name = (TextView) headerView.findViewById(R.id.tv_store_short_name);
            tv_store_price = (TextView) headerView.findViewById(R.id.tv_store_price);
            tv_store_number = (TextView) headerView.findViewById(R.id.tv_store_number);
            tv_store_number.setText(store.getAddress());
            tv_store_phone = (TextView) headerView.findViewById(R.id.tv_store_phone);
            tv_store_phone.setText(store.getTelephone());

            ImageLoader.getInstance().displayImage(store.getIcon(), iv_store_photo, options);
            tv_store_name.setText(store.getName());
            tv_store_short_name.setText(store.getAddress());
            tv_store_price.setText("$" + store.getAverageCost());

            startLoad(false, false);
            return view;
        }

        private void initRadioGroup(View headerView) {
            RadioGroup rg_tab_bar = (RadioGroup) headerView.findViewById(R.id.rg_tab_bar);
            rg_tab_bar.setOnCheckedChangeListener(this);

            switch (type) {
                case Constants.CATEGORY_COUPON:
                    rg_tab_bar.check(R.id.rb_tab_coupon);
                    break;
                case Constants.CATEGOTY_ACTIVITY:
                    rg_tab_bar.check(R.id.rb_tab_activity);
                    break;
                case Constants.CATEGOTY_DEAL:
                    rg_tab_bar.check(R.id.rb_tab_deal);
                    break;
                default:
                    rg_tab_bar.check(R.id.rb_tab_coupon);
                    break;
            }
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        }

        @Override
        public void onRefreshingUp() {
            startLoad(true, false);
        }

        @Override
        public void onRefreshingBottom() {
            startLoad(false, true);
        }

        private void startLoad(final boolean isUp, final boolean isBottom) {
            ThreadUtil.executeMore(new Runnable() {

                @Override
                public void run() {
                    final List<Coupon> list = DataManager.getCoupons();
                    //更新数据
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (adapter == null) {
                                adapter = new CouponAdapter(PlaceholderFragment.this.getActivity(), list);
                            } else {
                                adapter.setList(list);
                            }
                            lv_content.setAdapter(adapter);
                            adapter.notifyDataSetChanged();

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

        }
    }
}
