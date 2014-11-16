package com.phy0312.shopassistant.activity.base;


import android.support.v4.app.Fragment;


public abstract class PagerItem {

    private  CharSequence mTitle;
    private  int mIndicatorColor;
    private  int mDividerColor;

    public PagerItem(CharSequence title, int indicatorColor, int dividerColor) {
        mTitle = title;
        mIndicatorColor = indicatorColor;
        mDividerColor = dividerColor;
    }

    abstract  public Fragment createFragment();

    /**
     * @return the title which represents this tab. In this sample this is used directly by
     * {@link android.support.v4.view.PagerAdapter#getPageTitle(int)}
     */
    public CharSequence getTitle() {
        return mTitle;
    }


    public int getIndicatorColor() {
        return mIndicatorColor;
    }


    public int getDividerColor() {
        return mDividerColor;
    }


}
