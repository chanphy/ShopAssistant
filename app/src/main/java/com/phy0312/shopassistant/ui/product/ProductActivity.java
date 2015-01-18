package com.phy0312.shopassistant.ui.product;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.phy0312.shopassistant.MainApplication;
import com.phy0312.shopassistant.R;
import com.phy0312.shopassistant.adapter.ProductAdapter;
import com.phy0312.shopassistant.data.DataManager;
import com.phy0312.shopassistant.model.Product;
import com.phy0312.shopassistant.net.JsonCookieSupportRequest;
import com.phy0312.shopassistant.net.RequestResponseDataParseUtil;
import com.phy0312.shopassistant.net.URLManager;
import com.phy0312.shopassistant.tools.AndroidUtil;
import com.phy0312.shopassistant.tools.Constants;
import com.phy0312.shopassistant.tools.StringUtils;
import com.phy0312.shopassistant.ui.base.BaseFragment;
import com.phy0312.shopassistant.ui.base.BaseFragmentActivity;
import com.phy0312.shopassistant.ui.base.UIUtil;
import com.phy0312.shopassistant.view.HeaderGridView;
import com.phy0312.shopassistant.view.PullToRefreshLayout;
import com.phy0312.shopassistant.view.smoothprogressbar.SmoothProgressBar;

import org.json.JSONObject;

import java.util.List;

public class ProductActivity extends BaseFragmentActivity {

    public static final String PRODUCT_TITLE = "product_title";


    @Override
    public Fragment getPlaceholderFragment() {
        return new PlaceholderFragment();
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends BaseFragment {

        public static final int PROMOTION = 0;
        public static final int NEW = 1;
        public static final int RECOMMEND = 2;

        private PullToRefreshLayout ptl_container;
        private HeaderGridView gv_content;
        private ProductAdapter productAdapter;
        private int type = PROMOTION;
        private LinearLayout ll_empty_view;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            String text = getActivity().getIntent().getStringExtra(PRODUCT_TITLE);
            View view = inflater.inflate(R.layout.fragment_product, container, false);
            ((TextView) view.findViewById(R.id.tv_title)).setText(StringUtils.isEmpty(text)?"":text);

            ptl_container = (PullToRefreshLayout) view.findViewById(R.id.ptl_container);
            gv_content = (HeaderGridView) view.findViewById(R.id.lv_content);
            gv_content.setNumColumns(2);
            gv_content.setOnItemClickListener(this);
            View emptyView = UIUtil.getNetWorkInfoView(getActivity(), null, UIUtil.LOADING_DATA_INFO_VIEW);
            emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            emptyView.setVisibility(View.GONE);
            ((ViewGroup) gv_content.getParent()).addView(emptyView);
            gv_content.setEmptyView(emptyView);

            ptl_container.setGridView(gv_content);
            ptl_container.setUpProgressBar((SmoothProgressBar) view.findViewById(R.id.ptr_progress_up));
            ptl_container.setOnPullRefreshListener(this);

            gv_content.setAdapter(null);

            View headerView = inflater.inflate(R.layout.list_header_product, container, false);
            gv_content.addHeaderView(headerView);

            ll_empty_view = (LinearLayout)headerView.findViewById(R.id.ll_empty_view);
            ll_empty_view.setVisibility(View.VISIBLE);

            initRadioGroup(headerView);
            //后退按钮
            view.findViewById(R.id.iv_go_back).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().finish();
                }
            });
            startLoad(false, false, false);
            return view;
        }

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.rb_tab_product_recommend:
                    if (type != RECOMMEND) {
                        type = RECOMMEND;
                        gv_content.setAdapter(null);
                        startLoad(false, false, false);
                    }
                    break;
                case R.id.rb_tab_product_promotion:
                    if (type != PROMOTION) {
                        type = PROMOTION;
                        gv_content.setAdapter(null);
                        startLoad(false, false, false);
                    }
                    break;
                case R.id.rb_tab_product_new:
                    if (type != NEW) {
                        type = NEW;
                        gv_content.setAdapter(null);
                        startLoad(false, false, false);
                    }
                    break;
                default:
                    break;

            }
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Product product = productAdapter.getList().get(position);
            Intent intent = new Intent();
            intent.putExtra(Constants.TRANSFER_BUNDLE_PRODUCT, product);
            intent.setClassName(getActivity(), ProductDetailActivity.class.getName());
            AndroidUtil.startActivity(getActivity(), intent);
        }

        private void initRadioGroup(View headerView) {
            RadioGroup rg_tab_bar = (RadioGroup) headerView.findViewById(R.id.rg_tab_bar);
            rg_tab_bar.setOnCheckedChangeListener(this);

            switch (type) {
                case RECOMMEND:
                    rg_tab_bar.check(R.id.rb_tab_product_recommend);
                    break;
                case PROMOTION:
                    rg_tab_bar.check(R.id.rb_tab_product_promotion);
                    break;
                case NEW:
                    rg_tab_bar.check(R.id.rb_tab_product_new);
                    break;
                default:
                    rg_tab_bar.check(R.id.rb_tab_product_promotion);
                    break;
            }
        }

        @Override
        protected void startLoad(final boolean isUp, final boolean isBottom, final boolean append) {
            JSONObject jsonObject = new JSONObject();
            JsonCookieSupportRequest request = new JsonCookieSupportRequest(Request.Method.POST, URLManager.PRODUCT_LIST, jsonObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            new RequestResponseDataParseUtil.ResponseParse() {
                                @Override
                                public void parseResponseDataSection(JSONObject dataJsonObject) {//成功处理
                                    updateProductView(isUp, isBottom, append);
                                }
                            }.onResponse(jsonObject);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            updateProductView(isUp, isBottom, append);
                        }
                    }) {
                @Override
                public String getCacheKey() {
                    return super.getCacheKey();
                }
            };
            MainApplication.appContext.getRequestQueue().add(request);
        }


        private void updateProductView(boolean isUp, boolean isBottom, boolean append) {
            final List<Product> list = DataManager.getProducts();
            if (productAdapter == null) {
                productAdapter = new ProductAdapter(PlaceholderFragment.this.getActivity(), list);
            } else {
                if (append && productAdapter.getList() != null) {
                    productAdapter.getList().addAll(list);
                } else {
                    productAdapter.setList(list);
                }
            }
            gv_content.setAdapter(productAdapter);
            productAdapter.notifyDataSetChanged();

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
