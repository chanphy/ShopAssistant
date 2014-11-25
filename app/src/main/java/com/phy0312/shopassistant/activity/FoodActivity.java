package com.phy0312.shopassistant.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;

import com.phy0312.shopassistant.R;

/**
 * description: 美食主activity<br/>
 * author: Administrator<br/>
 * date: 2014/11/25<br/>
 */
public class FoodActivity extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_activity_fragment_container);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new FoodFragment())
                    .commit();
        }
    }


}
