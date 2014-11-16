package com.phy0312.shopassistant.activity;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.phy0312.shopassistant.R;
import com.phy0312.shopassistant.adapter.HuoDongAdapter;
import com.phy0312.shopassistant.data.DataManager;
import com.phy0312.shopassistant.model.HuoDong;
import com.phy0312.shopassistant.tools.ThreadUtil;
import com.phy0312.shopassistant.view.PullToRefreshLayout;
import com.phy0312.shopassistant.view.smoothprogressbar.SmoothProgressBar;
import com.phy0312.shopassistant.view.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class CommonHuoDongFragment extends Fragment implements PullToRefreshLayout.PullRefreshListener{

    public static final int HOT = 0;
    public static final int NEW = 1;
    public static final int NEXTWEEK = 2;


    private PullToRefreshLayout ptl_container;
    private ListView lv_content;
    private ViewPager viewPager;
    private CirclePageIndicator indicator;
    List<View> viewList;
    private Handler handler;
    private HuoDongAdapter adpter;
    private int type;

    public CommonHuoDongFragment(int type) {
        this.type = type;
    }

    public CommonHuoDongFragment() {

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
        View view = inflater.inflate(R.layout.fragment_common_huo_dong, container, false);
        ptl_container = (PullToRefreshLayout)view.findViewById(R.id.ptl_container);
        lv_content = (ListView)view.findViewById(R.id.lv_content);
        ptl_container.setListView(lv_content);
        ptl_container.setUpProgressBar((SmoothProgressBar)view.findViewById(R.id.ptr_progress_up));
        ptl_container.setOnPullRefreshListener(this);

        LayoutInflater lif = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View headerView = lif.inflate(R.layout.ads_header, lv_content, false);
        lv_content.addHeaderView(headerView);
        lv_content.setAdapter(null);

        viewPager = (ViewPager)headerView.findViewById(R.id.pager);
        initAdsBanner();
        indicator = (CirclePageIndicator)headerView.findViewById(R.id.indicator);
        indicator.setViewPager(viewPager);
        startLoad();
        return view;
    }

    private void initAdsBanner() {
        LayoutInflater mInflater = getActivity().getLayoutInflater().from(getActivity());
        View v1 = mInflater.inflate(R.layout.banner_ads, null);
        View v2 = mInflater.inflate(R.layout.banner_ads, null);
        View v3 = mInflater.inflate(R.layout.banner_ads, null);

        //添加页面数据
        viewList = new ArrayList<View>();
        viewList.add(v1);
        viewList.add(v2);
        viewList.add(v3);


        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return viewList.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object o) {
                return view == o;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(viewList.get(position));
                return viewList.get(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(viewList.get(position));

            }
        });

        viewPager.setCurrentItem(0);
    }


    @Override
    public void onRefreshingUp() {

    }

    @Override
    public void onRefreshingBottom() {

    }


    private void startLoad() {
        ThreadUtil.executeMore(new Runnable(){

            @Override
            public void run() {
                final List<HuoDong> list = DataManager.getHuoDongs();
                //更新数据
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(adpter == null) {
                            adpter = new HuoDongAdapter(list, CommonHuoDongFragment.this.getActivity());
                        }else{
                            adpter.setList(list);
                        }
                        lv_content.setAdapter(adpter);
                        adpter.notifyDataSetChanged();
                    }
                });
            }
        });

    }
}
