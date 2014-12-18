package com.phy0312.shopassistant.ui.store;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.phy0312.shopassistant.R;
import com.phy0312.shopassistant.adapter.CouponAdapter;
import com.phy0312.shopassistant.model.Store;
import com.phy0312.shopassistant.tools.Constants;
import com.phy0312.shopassistant.ui.base.BaseFragment;
import com.phy0312.shopassistant.view.PullToRefreshLayout;
import com.phy0312.shopassistant.view.expandtextview.ExpandCollapseTextView;
import com.phy0312.shopassistant.view.smoothprogressbar.SmoothProgressBar;

import java.util.Date;

public class StoreDetailActivity extends ActionBarActivity {

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
    public static class PlaceholderFragment extends BaseFragment {

        private PullToRefreshLayout ptl_container;
        private ListView lv_content;
        private Handler handler;

        ImageView iv_store_photo;
        TextView tv_store_name;
        TextView tv_store_number;
        TextView tv_store_phone;
        ExpandCollapseTextView tv_store_description;

        DisplayImageOptions options;
        private LinearLayout ll_empty_view;

        Store store = new Store(1L, "1", "1", 1, "大丰收","3F-306", 75, "http://pic25.nipic.com/20121119/11328459_121121530346_2.jpg", "0591-83956234", 3, 1, new Date(), "年年大丰收");

        private CouponAdapter adapter;

        private int type = Constants.CATEGORY_COUPON;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_store_detail, container, false);
            ((TextView) view.findViewById(R.id.tv_title)).setText(getActivity().getString(R.string.title_activity_store_detail));

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
            View headerView = inflater.inflate(R.layout.list_header_store_detail, lv_content, false);
            ll_empty_view = (LinearLayout) headerView.findViewById(R.id.ll_empty_view);
            ll_empty_view.setVisibility(View.VISIBLE);

            lv_content.addHeaderView(headerView);
            lv_content.setAdapter(null);

            initRadioGroup(headerView);

            iv_store_photo = (ImageView) headerView.findViewById(R.id.iv_store_photo);
            tv_store_name = (TextView) headerView.findViewById(R.id.tv_store_name);
            tv_store_number = (TextView) headerView.findViewById(R.id.tv_store_number);
            tv_store_number.setText(store.getAddress());
            tv_store_phone = (TextView) headerView.findViewById(R.id.tv_store_phone);
            tv_store_phone.setText(store.getTelephone());
            tv_store_description = (ExpandCollapseTextView) headerView.findViewById(R.id.tv_store_description);
            tv_store_description.setText(R.string.dummy_text2);

            ImageLoader.getInstance().displayImage(store.getIcon(), iv_store_photo, options);
            tv_store_name.setText(store.getName());
            startLoad(false, false, false);
            return view;
        }

        private void initRadioGroup(View headerView) {
            RadioGroup rg_tab_bar = (RadioGroup) headerView.findViewById(R.id.rg_tab_bar);
            rg_tab_bar.setOnCheckedChangeListener(this);

            switch (type) {
                case Constants.CATEGORY_COUPON:
                    rg_tab_bar.check(R.id.rb_coupon);
                    break;
                case Constants.CATEGOTY_ACTIVITY:
                    rg_tab_bar.check(R.id.rb_activity);
                    break;
                case Constants.CATEGOTY_PRODUCT:
                    rg_tab_bar.check(R.id.rb_product);
                    break;
                default:
                    rg_tab_bar.check(R.id.rb_coupon);
                    break;
            }
        }
    }
}
