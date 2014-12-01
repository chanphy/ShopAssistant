package com.phy0312.shopassistant.ui.base;


import android.support.v4.app.Fragment;


public abstract class PagerItem {

    private  CharSequence mTitle;

    public PagerItem(CharSequence title) {
        mTitle = title;
    }

    abstract  public Fragment createFragment();

    /**
     * @return the title which represents this tab. In this sample this is used directly by
     * {@link android.support.v4.view.PagerAdapter#getPageTitle(int)}
     */
    public CharSequence getTitle() {
        return mTitle;
    }
}
