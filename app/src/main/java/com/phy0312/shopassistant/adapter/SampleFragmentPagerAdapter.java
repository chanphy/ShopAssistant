package com.phy0312.shopassistant.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.phy0312.shopassistant.activity.base.PagerItem;
import java.util.List;

/**
 * description: <br/>
 * author: dingdj<br/>
 * date: 2014/11/25<br/>
 */
public class SampleFragmentPagerAdapter extends FragmentPagerAdapter {

    List<PagerItem> mTabs;

    public SampleFragmentPagerAdapter(FragmentManager fm, List<PagerItem> mTabs) {
        super(fm);
        this.mTabs = mTabs;
        if(mTabs == null) {
            throw new IllegalArgumentException("SampleFragmentPagerAdapter constuct is error: " +
                    "mTabs is null");
        }
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
