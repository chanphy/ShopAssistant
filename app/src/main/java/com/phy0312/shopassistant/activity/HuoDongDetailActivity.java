package com.phy0312.shopassistant.activity;


import android.app.Activity;
import android.os.Bundle;

import com.phy0312.shopassistant.R;
import com.phy0312.shopassistant.db.HuoDong;
import com.phy0312.shopassistant.db.Store;

/**
 * description: 活动详情页面<br/>
 * author: dingdj<br/>
 * date: 2014/11/17<br/>
 */
public class HuoDongDetailActivity extends Activity {


    private HuoDong huoDong;
    private Store store;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);
    }
}
