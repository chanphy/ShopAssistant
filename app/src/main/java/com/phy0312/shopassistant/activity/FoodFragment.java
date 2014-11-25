package com.phy0312.shopassistant.activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.phy0312.shopassistant.R;
import com.phy0312.shopassistant.activity.base.PagerItem;
import com.phy0312.shopassistant.adapter.SampleFragmentPagerAdapter;
import com.phy0312.shopassistant.view.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FoodFragment extends Fragment {

    @SuppressWarnings("all")
    private ViewPager vp_container;
    @SuppressWarnings("all")
    private PagerSlidingTabStrip sliding_tabs;
    List<PagerItem> mTabs = new ArrayList<PagerItem>();


    public FoodFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTabs.add(new PagerItem(
                getActivity().getString(R.string.food)
        ) {
            @Override
            public Fragment createFragment() {
                return new CommonHuoDongFragment(CommonHuoDongFragment.HOT);
            }
        });

        mTabs.add(new PagerItem(
                getActivity().getString(R.string.food_coupon)
        ) {
            @Override
            public Fragment createFragment() {
                return new CommonHuoDongFragment(CommonHuoDongFragment.NEW);
            }
        });

        mTabs.add(new PagerItem(
                getActivity().getString(R.string.food_huodong)
        ) {
            @Override
            public Fragment createFragment() {
                return new CommonHuoDongFragment(CommonHuoDongFragment.NEXTWEEK);
            }
        });

        mTabs.add(new PagerItem(
                getActivity().getString(R.string.food_tuangou)
        ) {
            @Override
            public Fragment createFragment() {
                return new CommonHuoDongFragment(CommonHuoDongFragment.NEXTWEEK);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food, container, false);

        vp_container = (ViewPager) view.findViewById(R.id.vp_container);
        vp_container.setAdapter(new SampleFragmentPagerAdapter(getChildFragmentManager(), mTabs));

        sliding_tabs = (PagerSlidingTabStrip)view.findViewById(R.id.sliding_tabs);
        sliding_tabs.setViewPager(vp_container);

        ((TextView) view.findViewById(R.id.tv_title)).setText(getString(R.string.menu_title_food));
        view.findViewById(R.id.iv_go_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        return view;
    }


}
