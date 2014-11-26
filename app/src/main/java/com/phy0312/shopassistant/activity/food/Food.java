package com.phy0312.shopassistant.activity.food;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.phy0312.shopassistant.R;

/**
 * description: 美食主activity<br/>
 * author: Administrator<br/>
 * date: 2014/11/25<br/>
 */
public class Food extends FragmentActivity {
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
