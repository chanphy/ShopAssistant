package com.phy0312.shopassistant.activity;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.phy0312.shopassistant.R;
import com.phy0312.shopassistant.activity.base.PagerItem;
import com.phy0312.shopassistant.view.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HuoDongFragment extends Fragment {

    private ViewPager vp_container;
    private SlidingTabLayout mSlidingTabLayout;
    List<PagerItem> mTabs = new ArrayList<PagerItem>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTabs.add(new PagerItem(
                getString(R.string.hot_huodong), // Title
                Color.BLUE, // Indicator color
                Color.GRAY // Divider color
        ) {
            @Override
            public Fragment createFragment() {
                return new CommonHuoDongFragment(CommonHuoDongFragment.HOT);
            }
        });

        mTabs.add(new PagerItem(
                getString(R.string.new_huodong), // Title
                Color.RED, // Indicator color
                Color.GRAY // Divider color
        ) {
            @Override
            public Fragment createFragment() {
                return new CommonHuoDongFragment(CommonHuoDongFragment.NEW);
            }
        });

        mTabs.add(new PagerItem(
                getString(R.string.nextweek_huodong), // Title
                Color.YELLOW, // Indicator color
                Color.GRAY // Divider color
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
        View view = inflater.inflate(R.layout.fragment_huo_dong, container, false);

        vp_container = (ViewPager) view.findViewById(R.id.vp_container);
        vp_container.setAdapter(new SampleFragmentPagerAdapter(getChildFragmentManager()));

        mSlidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.sliding_tabs);
        mSlidingTabLayout.setLayout_weight(true);
        mSlidingTabLayout.setViewPager(vp_container);

        mSlidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {

            @Override
            public int getIndicatorColor(int position) {
                return mTabs.get(position).getIndicatorColor();
            }

            @Override
            public int getDividerColor(int position) {
                return mTabs.get(position).getDividerColor();
            }



        });

        return view;
    }


    class SampleFragmentPagerAdapter extends FragmentPagerAdapter {

        SampleFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public Fragment getItem(int i) {
            return mTabs.get(i).createFragment();
        }

        @Override
        public int getCount() {
            return mTabs.size();
        }


        @Override
        public CharSequence getPageTitle(int position) {
            return mTabs.get(position).getTitle();
        }
    }


}
