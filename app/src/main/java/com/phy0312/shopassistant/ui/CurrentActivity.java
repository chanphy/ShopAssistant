package com.phy0312.shopassistant.ui;


import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Spinner;

import com.phy0312.shopassistant.R;
import com.phy0312.shopassistant.adapter.PlazaAdapter;
import com.phy0312.shopassistant.config.MainSp;
import com.phy0312.shopassistant.ui.base.BaseActivity;

import java.util.ArrayList;

/**
 * description: <br/>
 * author: dingdongjin_91<br/>
 * date: 2014/12/19<br/>
 */
public class CurrentActivity extends BaseActivity{

    private Toolbar toolbar;
    private Spinner sp_plazas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        overridePendingTransition(0, 0);

        ActionBar ab = getSupportActionBar();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        sp_plazas = (Spinner) findViewById(R.id.sp_plazas);

        ArrayList<String> list = new ArrayList<String>();
        list.add("东方百货");
        list.add("万象城");
        list.add("宝龙城市广场");
        sp_plazas.setAdapter(new PlazaAdapter(list, LayoutInflater.from(this)));

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setTitle(getString(R.string.app_name));

        MainFragment mainFragment = new MainFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.flv_main_content, mainFragment).commit();

        if (MainSp.getInstance(this).isFirstUse()) {
            mDrawerLayout.openDrawer(Gravity.START);
            MainSp.getInstance(this).setFirstUse(false);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateActionBarNavigation();
    }

    @Override
    protected int getSelfNavDrawerItem() {
        return super.NAVDRAWER_ITEM_MAIN;
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
            sp_plazas.setVisibility(View.VISIBLE);
            ab.setDisplayShowTitleEnabled(false);
        } else {
            sp_plazas.setVisibility(View.GONE);
            ab.setDisplayShowTitleEnabled(true);
        }
    }




}
