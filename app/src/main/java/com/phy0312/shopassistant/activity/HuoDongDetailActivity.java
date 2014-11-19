package com.phy0312.shopassistant.activity;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.phy0312.shopassistant.R;
import com.phy0312.shopassistant.db.HuoDong;
import com.phy0312.shopassistant.db.Store;
import com.phy0312.shopassistant.tools.DateUtil;
import com.squareup.picasso.Picasso;

import java.util.Date;

/**
 * description: 活动详情页面<br/>
 * author: dingdj<br/>
 * date: 2014/11/17<br/>
 */
public class HuoDongDetailActivity extends Activity {


    private HuoDong huoDong;
    private Store store;

    private ImageView iv_activity_photo;
    private TextView tv_activity_name;
    private TextView tv_activity_status;
    private TextView tv_activity_description;
    private TextView tv_activity_valid_time;
    private TextView tv_store_name;
    private TextView tv_store_number;
    private TextView tv_store_telephone;
    private TextView tv_activity_detail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.detail_activity);
        ((TextView) findViewById(R.id.tv_title)).setText(getString(R.string.title_activity_huo_dong_detail));
        initData();
        iv_activity_photo = (ImageView) findViewById(R.id.iv_activity_photo);
        Picasso.with(this).load(huoDong.getIcon()).into(iv_activity_photo);
        ((TextView) findViewById(R.id.tv_activity_name)).setText(huoDong.getName());
        ((TextView) findViewById(R.id.tv_activity_description)).setText(huoDong.getName());
        ((TextView) findViewById(R.id.tv_activity_valid_time)).setText(DateUtil.parseLongToDate(huoDong.getStartTime()) + "至" +
                DateUtil.parseLongToDate(huoDong.getEndTime()));
        ((TextView) findViewById(R.id.tv_store_name)).setText(store.getName());
        ((TextView) findViewById(R.id.tv_store_number)).setText(store.getAddress());
        ((TextView) findViewById(R.id.tv_store_telephone)).setText(store.getTelephone());
        ((TextView) findViewById(R.id.tv_activity_detail)).setText(huoDong.getDescription());

        //后退按钮
        findViewById(R.id.iv_go_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HuoDongDetailActivity.this.finish();
            }
        });

        //分享按钮
        findViewById(R.id.iv_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HuoDongDetailActivity.this.finish();
            }
        });
    }


    private void initData() {
        huoDong = new HuoDong(1L, "1", "3", System.currentTimeMillis(), System.currentTimeMillis(),
                CommonHuoDongFragment.HOT, "活动详情", "闲逛记", "http://pic25.nipic.com/20121119/11328459_121121530346_2.jpg", 1);
        store = new Store(1L, "3", "3", 1, "闲逛记", "1F", 0, "http://pic25.nipic.com/20121119/11328459_121121530346_2.jpg",
                "0591-839236541", 1, 1, new Date(), "");
    }

}
