package com.phy0312.shopassistant.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocationStatusCodes;
import com.baidu.location.GeofenceClient;
import com.phy0312.shopassistant.MainApplication;
import com.phy0312.shopassistant.R;
import com.phy0312.shopassistant.adapter.PlazaAdapter;
import com.phy0312.shopassistant.config.MainSp;
import com.phy0312.shopassistant.data.DataManager;
import com.phy0312.shopassistant.model.Plaza;
import com.phy0312.shopassistant.tools.LogUtil;
import com.phy0312.shopassistant.ui.activity.ActivityFragment;
import com.phy0312.shopassistant.ui.base.BaseFragment;
import com.phy0312.shopassistant.ui.coupon.CouponFragment;
import com.phy0312.shopassistant.ui.deal.DealFragment;
import com.phy0312.shopassistant.ui.my.MyProfileFragment;

import java.util.ArrayList;

public class HomeActivity extends FragmentActivity implements RadioGroup.OnCheckedChangeListener
        , MainApplication.LocationChange
        , GeofenceClient.OnGeofenceTriggerListener
        , GeofenceClient.OnAddBDGeofencesResultListener {

    public static final String TAG = HomeActivity.class.getSimpleName();

    @SuppressWarnings("all")
    private TextView tv_map;
    @SuppressWarnings("all")
    private TextView tv_title;
    private Spinner sp_plazas;

    private RadioGroup rg_tab_bar;

    public static final int NAVDRAWER_ITEM_MAIN = 0;
    public static final int NAVDRAWER_ITEM_HUODONG = 1;
    public static final int NAVDRAWER_ITEM_COUPON = 2;
    public static final int NAVDRAWER_ITEM_TUANGOU = 3;
    public static final int NAVDRAWER_ITEM_MY_PROFILE = 4;

    private int tab = NAVDRAWER_ITEM_MAIN;

    @SuppressWarnings("all")
    private final int waitTime = 2000;
    private long touchTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.home_frame);
        initView();
        MainApplication.appContext.registerLocationChanger(this);
        MainApplication.appContext.mLocationClient.start();
        //注册并开启围栏扫描服务
        MainApplication.appContext.mGeofenceClient.registerGeofenceTriggerListener(this);
    }

    private void initView() {
        tv_map = (TextView) findViewById(R.id.tv_map);
        tv_map.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        tv_title = (TextView) findViewById(R.id.tv_title);
        sp_plazas = (Spinner) findViewById(R.id.sp_plazas);
        ArrayList<String> list = new ArrayList<>();
        list.add("东方百货");
        list.add("万象城");
        list.add("宝龙城市广场");
        sp_plazas.setAdapter(new PlazaAdapter(list, LayoutInflater.from(this)));
        updateViewState("");

        rg_tab_bar = (RadioGroup) findViewById(R.id.rg_tab_bar);

        rg_tab_bar.setOnCheckedChangeListener(this);
        rg_tab_bar.check(R.id.rb_tab_index);
    }


    private void updateViewState(String title) {
        if (tab == NAVDRAWER_ITEM_MAIN) {
            sp_plazas.setVisibility(View.VISIBLE);
            tv_title.setVisibility(View.INVISIBLE);
        } else {
            sp_plazas.setVisibility(View.INVISIBLE);
            tv_title.setVisibility(View.VISIBLE);
            tv_title.setText(title);
        }
    }

    /**
     * 加载相关Fragment
     */
    private void loadFragment() {
        switch (tab) {
            case NAVDRAWER_ITEM_MAIN:
                MainFragment mainFragment = new MainFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.flv_main_content, mainFragment).commit();
                updateViewState("");
                break;
            case NAVDRAWER_ITEM_HUODONG:
                ActivityFragment activityFragment = new ActivityFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.flv_main_content, activityFragment).commit();
                updateViewState(getString(R.string.navdrawer_item_huodong));
                break;
            case NAVDRAWER_ITEM_COUPON:
                CouponFragment couponFragment = new CouponFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.flv_main_content, couponFragment).commit();
                updateViewState(getString(R.string.navdrawer_item_coupon));
                break;
            case NAVDRAWER_ITEM_TUANGOU:
                LogUtil.e("NAVDRAWER_ITEM_TUANGOU----------------------");
                DealFragment dealFragment = new DealFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.flv_main_content, dealFragment).commit();
                updateViewState(getString(R.string.navdrawer_item_tuangou));
                break;
            case NAVDRAWER_ITEM_MY_PROFILE:
                MyProfileFragment myProfileFragment = new MyProfileFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.flv_main_content, myProfileFragment).commit();
                updateViewState(getString(R.string.navdrawer_item_my_profile));
                break;
            default:
                mainFragment = new MainFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.flv_main_content, mainFragment).commit();
                updateViewState("");
                break;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_launcher, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        LogUtil.e("checkedId:" + checkedId);
        switch (checkedId) {
            case R.id.rb_tab_index:
                tab = NAVDRAWER_ITEM_MAIN;
                loadFragment();
                break;
            case R.id.rb_tab_activity:
                tab = NAVDRAWER_ITEM_HUODONG;
                loadFragment();
                break;
            case R.id.rb_tab_coupon:
                tab = NAVDRAWER_ITEM_COUPON;
                loadFragment();
                break;
            case R.id.rb_tab_food:
                tab = NAVDRAWER_ITEM_TUANGOU;
                loadFragment();
                break;
            case R.id.rb_tab_my_profile:
                tab = NAVDRAWER_ITEM_MY_PROFILE;
                loadFragment();
                break;
            default:
                tab = NAVDRAWER_ITEM_MAIN;
                loadFragment();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        BaseFragment fragment = (BaseFragment) getSupportFragmentManager().findFragmentById(R.id.flv_main_content);
        if (fragment != null && fragment.onBackPressed()) {
            return;
        }

        if (!(fragment instanceof MainFragment)) {
            rg_tab_bar.check(R.id.rb_tab_index);
            return;
        }
        long currentTime = System.currentTimeMillis();
        if ((currentTime - touchTime) >= waitTime) {
            Toast.makeText(this, getString(R.string.exit_confirm), Toast.LENGTH_SHORT).show();
            touchTime = currentTime;
        } else {
            finish();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        MainApplication.appContext.unRegisterLocationChanger();
    }


    @Override
    public void locationChange() {

    }

    /**
     * 进入地址围栏
     */
    @Override
    public void onGeofenceTrigger(final String geofenceRequestId) {
        Log.e(TAG, "geofenceRequestId");
        Plaza plaza = DataManager.getPlazaById(geofenceRequestId);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("你已进入" + plaza.getName() + ", 点确定切换吧。");
        builder.setTitle("切换商场");
        builder.setPositiveButton("切换", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                //切换商场
                MainSp.getInstance(HomeActivity.this).setPlazaId(geofenceRequestId);
                //刷新界面
                //todo
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        builder.show();
    }

    /**
     * 离开地址围栏
     */
    @Override
    public void onGeofenceExit(String geofenceRequestId) {

    }

    @Override
    public void onAddBDGeofencesResult(int statusCode, String geofenceRequestId) {
        if (statusCode == BDLocationStatusCodes.SUCCESS) {
            Log.e(TAG, "添加围栏成功" + geofenceRequestId);
            MainApplication.appContext.mGeofenceClient.start();
        } else {
            Log.e(TAG, "添加围栏失败" + geofenceRequestId);
        }
    }
}
