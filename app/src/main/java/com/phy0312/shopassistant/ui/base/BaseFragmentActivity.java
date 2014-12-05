package com.phy0312.shopassistant.ui.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.phy0312.shopassistant.R;

/**
 * description: <br/>
 * author: dingdj<br/>
 * date: 2014/12/5<br/>
 */
public abstract class BaseFragmentActivity extends FragmentActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_activity_fragment_container);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, getPlaceholderFragment())
                    .commit();
        }
    }

    abstract public Fragment getPlaceholderFragment();
}
