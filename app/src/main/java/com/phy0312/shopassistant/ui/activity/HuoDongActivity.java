package com.phy0312.shopassistant.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.phy0312.shopassistant.R;
import com.phy0312.shopassistant.ui.base.BaseActivity;
import com.phy0312.shopassistant.ui.coupon.CouponFragment;

/**
 * description: <br/>
 * author: dingdongjin_91<br/>
 * date: 2014/12/19<br/>
 */
public class HuoDongActivity extends BaseActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        overridePendingTransition(0, 0);

        ActionBar ab = getSupportActionBar();

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setTitle(getString(R.string.app_name));

        ActivityFragment activityFragment = new ActivityFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.flv_main_content, activityFragment).commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateActionBarNavigation();
    }

    @Override
    protected int getSelfNavDrawerItem() {
        return super.NAVDRAWER_ITEM_HUODONG;
    }

    @Override
    protected void onNavDrawerStateChanged(boolean isOpen, boolean isAnimating) {
        super.onNavDrawerStateChanged(isOpen, isAnimating);
        updateActionBarNavigation();
    }

    private void updateActionBarNavigation() {
        boolean show = !isNavDrawerOpen();

        ActionBar ab = getSupportActionBar();
        if (show) {
            ab.setDisplayShowTitleEnabled(false);
        } else {
            ab.setDisplayShowTitleEnabled(true);
        }
    }
}
