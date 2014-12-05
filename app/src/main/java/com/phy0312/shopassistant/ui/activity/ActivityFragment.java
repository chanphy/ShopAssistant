package com.phy0312.shopassistant.ui.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;

import com.phy0312.shopassistant.R;
import com.phy0312.shopassistant.adapter.ActivityAdapter;
import com.phy0312.shopassistant.tools.Constants;
import com.phy0312.shopassistant.ui.base.UIUtil;
import com.phy0312.shopassistant.data.DataManager;
import com.phy0312.shopassistant.db.HuoDong;
import com.phy0312.shopassistant.tools.ThreadUtil;
import com.phy0312.shopassistant.view.PullToRefreshLayout;
import com.phy0312.shopassistant.view.smoothprogressbar.SmoothProgressBar;
import com.phy0312.shopassistant.view.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ActivityFragment extends Fragment implements PullToRefreshLayout.PullRefreshListener,
        AdapterView.OnItemClickListener, RadioGroup.OnCheckedChangeListener{

    private PullToRefreshLayout ptl_container;
    private ListView lv_content;
    List<ImageView> viewList = new ArrayList<ImageView>();
    private Handler handler;
    private ActivityAdapter adpter;
    private int type = Constants.HOT;



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
        ptl_container = (PullToRefreshLayout)view.findViewById(R.id.ptl_container);
        lv_content = (ListView)view.findViewById(R.id.lv_content);
        ptl_container.setListView(lv_content);
        ptl_container.setUpProgressBar((SmoothProgressBar)view.findViewById(R.id.ptr_progress_up));
        ptl_container.setOnPullRefreshListener(this);

        LayoutInflater lif = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View headerView = lif.inflate(R.layout.list_header_activity, lv_content, false);

        RadioGroup rg_tab_bar = (RadioGroup)headerView.findViewById(R.id.rg_tab_bar);
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

        ViewPager viewPager = (ViewPager)headerView.findViewById(R.id.pager);
        UIUtil.initAdsBanner(getActivity(), viewList, viewPager);
        CirclePageIndicator indicator = (CirclePageIndicator)headerView.findViewById(R.id.indicator);
        indicator.setViewPager(viewPager);
        startLoad(false, false, false);
        return view;
    }




    @Override
    public void onRefreshingUp() {
        startLoad(true, false, true);
    }

    @Override
    public void onRefreshingBottom() {
        startLoad(false, true, true);
    }


    private void startLoad(final boolean isUp, final boolean isBottom, final boolean append) {
        ThreadUtil.executeMore(new Runnable() {

            @Override
            public void run() {
                final List<HuoDong> list = DataManager.getHuoDongs(type);
                //更新数据
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (adpter == null) {
                            adpter = new ActivityAdapter(ActivityFragment.this.getActivity(), list);
                        } else {
                            if(append && adpter.getList() != null){
                                adpter.getList().addAll(list);
                            }else{
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent();
        intent.setClassName(getActivity(), DetailActivity.class.getName());
        try{
            getActivity().startActivity(intent);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.rb_tab_activity_hot:
                if(type != Constants.HOT) {
                    type = Constants.HOT;
                    lv_content.setAdapter(null);
                    startLoad(false, false, false);
                }
                break;
            case R.id.rb_tab_activity_latest:
                if(type != Constants.LATEST) {
                    type = Constants.LATEST;
                    lv_content.setAdapter(null);
                    startLoad(false, false, false);
                }
                break;
            case R.id.rb_tab_activity_come_soon:
                if(type != Constants.NEXTWEEK) {
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
