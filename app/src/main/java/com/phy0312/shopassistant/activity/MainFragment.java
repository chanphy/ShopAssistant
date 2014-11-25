package com.phy0312.shopassistant.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.phy0312.shopassistant.R;
import com.phy0312.shopassistant.adapter.MainItemAdpter;
import com.phy0312.shopassistant.data.DataManager;
import com.phy0312.shopassistant.model.MainColumnGroup;
import com.phy0312.shopassistant.tools.AndroidUtil;
import com.phy0312.shopassistant.tools.ThreadUtil;
import com.phy0312.shopassistant.view.PullToRefreshLayout;
import com.phy0312.shopassistant.view.smoothprogressbar.SmoothProgressBar;
import com.phy0312.shopassistant.view.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment implements PullToRefreshLayout.PullRefreshListener{


    private PullToRefreshLayout ptl_container;
    private ListView lv_content;
    private ViewPager viewPager;
    private CirclePageIndicator indicator;
    List<ImageView> viewList;
    private Handler handler;
    private MainItemAdpter adpter;
    DisplayImageOptions options;
    TextView tv_food;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        handler = new Handler();

        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_stub)
                .showImageForEmptyUri(R.drawable.ic_empty)
                .showImageOnFail(R.drawable.ic_error)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .build();

        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ptl_container = (PullToRefreshLayout)view.findViewById(R.id.ptl_container);
        lv_content = (ListView)view.findViewById(R.id.lv_content);
        ptl_container.setListView(lv_content);
        SmoothProgressBar ptr_progress_up = (SmoothProgressBar)view.findViewById(R.id.ptr_progress_up);
        ptl_container.setUpProgressBar(ptr_progress_up);
        ptl_container.setOnPullRefreshListener(this);

        LayoutInflater lif = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View headerView = lif.inflate(R.layout.main_header, lv_content, false);

        lv_content.addHeaderView(headerView);
        lv_content.setAdapter(adpter);

        tv_food = (TextView)headerView.findViewById(R.id.tv_food);
        tv_food.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClassName(MainFragment.this.getActivity(), FoodActivity.class.getName());
                AndroidUtil.startActivity(MainFragment.this.getActivity(), intent);
            }
        });

        viewPager = (ViewPager)headerView.findViewById(R.id.pager);
        initAdsBanner();
        indicator = (CirclePageIndicator)headerView.findViewById(R.id.indicator);
        indicator.setViewPager(viewPager);
        startLoad(false, false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onRefreshingUp() {
        startLoad(true, false);
    }

    @Override
    public void onRefreshingBottom() {
        startLoad(false, true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initAdsBanner() {
        LayoutInflater mInflater = LayoutInflater.from(getActivity());
        ImageView v1 = (ImageView)mInflater.inflate(R.layout.banner_ads, null);
        ImageView v2 = (ImageView)mInflater.inflate(R.layout.banner_ads, null);
        ImageView v3 = (ImageView)mInflater.inflate(R.layout.banner_ads, null);



        //添加页面数据
        viewList = new ArrayList<ImageView>();
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
                ImageLoader.getInstance().displayImage("http://d02.res.meilishuo.net/pic/d/87/8c/9ae13726e933e85711c2b3de7c8a_750_220.gif",
                        viewList.get(position), options);
                return viewList.get(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(viewList.get(position));

            }
        });

        viewPager.setCurrentItem(0);
    }

    /**
     * 开始加载数据
     */
    private void startLoad(final boolean isUp, final boolean isBottom) {
        ThreadUtil.executeMore(new Runnable(){
            @Override
            public void run() {
                final List<MainColumnGroup> list = DataManager.GetMainColumnInfos();
                //更新数据
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(adpter == null) {
                            adpter = new MainItemAdpter(list, MainFragment.this.getActivity());
                        }else{
                            adpter.setList(list);
                        }
                        lv_content.setAdapter(adpter);
                        adpter.notifyDataSetChanged();

                        if(isUp) {
                            ptl_container.setRefreshUpEnd();
                        }

                        if(isBottom) {
                            ptl_container.setRefreshingBottomEnd();
                        }


                    }
                });
            }
        });
    }

}
