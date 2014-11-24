package com.phy0312.shopassistant.activity;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.phy0312.shopassistant.R;
import com.phy0312.shopassistant.adapter.DrawerMenuAdapter;
import com.phy0312.shopassistant.adapter.PlazaAdapter;
import com.phy0312.shopassistant.config.MainSp;

import java.util.ArrayList;

/**
 * 主activity
 */
public class MainActivity extends ActionBarActivity implements ListView.OnItemClickListener, ActionBar.TabListener {

    private final int waitTime = 2000;
    private long touchTime = 0;

    private DrawerLayout drawerLayout;
    private ListView lv_menu;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private int itemId = 0;
    private Toolbar toolbar;
    private Spinner sp_plazas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        sp_plazas = (Spinner) findViewById(R.id.sp_plazas);
        ArrayList<String> list = new ArrayList<String>();
        list.add("东方百货");
        list.add("万象城");
        list.add("宝龙城市广场");
        sp_plazas.setAdapter(new PlazaAdapter(list, LayoutInflater.from(this)));

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayUseLogoEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        lv_menu = (ListView) findViewById(R.id.lv_menu);
        lv_menu.setOnItemClickListener(this);
        initDrawerMenu();
        MainFragment mainFragment = new MainFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.flv_main_content, mainFragment).commit();

        MainSp sp = new MainSp(this);
        if(sp.isFirstUse()) {
            drawerLayout.openDrawer(Gravity.LEFT);
            sp.setFirstUse(false);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 初始化匣子菜单
     */
    private void initDrawerMenu() {
        mTitle = getTitle();
        mDrawerTitle = "";

        lv_menu.setAdapter(new DrawerMenuAdapter(this.getLayoutInflater()));

        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                if(mDrawerTitle.equals("")){
                    sp_plazas.setVisibility(View.VISIBLE);
                }
                getSupportActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setDisplayShowTitleEnabled(true);
                sp_plazas.setVisibility(View.GONE);
                getSupportActionBar().setTitle(getString(R.string.app_name));
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        // Set the drawer toggle as the DrawerListener
        drawerLayout.setDrawerListener(mDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, Gravity.START);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if (position == itemId) {
            drawerLayout.closeDrawers();
            return;
        }
        itemId = position;
        switch (position) {
            case DrawerMenuAdapter.NAVDRAWER_ITEM_MAIN:
                MainFragment mainFragment = new MainFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.flv_main_content, mainFragment).commit();
                mDrawerTitle = "";
                break;
            case DrawerMenuAdapter.NAVDRAWER_ITEM_HUODONG:
                HuoDongFragment huoDongFragment = new HuoDongFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.flv_main_content, huoDongFragment).commit();
                mDrawerTitle = getString(R.string.navdrawer_item_huodong);
                break;
            case DrawerMenuAdapter.NAVDRAWER_ITEM_COUPON:
                CouponFragment couponFragment = new CouponFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.flv_main_content, couponFragment).commit();
                mDrawerTitle = getString(R.string.navdrawer_item_coupon);
                break;
            case DrawerMenuAdapter.NAVDRAWER_ITEM_TUANGOU:
                FoodFragment foodFragment = new FoodFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.flv_main_content, foodFragment).commit();
                mDrawerTitle = getString(R.string.navdrawer_item_tuangou);
                break;
            case DrawerMenuAdapter.NAVDRAWER_ITEM_MY_PROFILE:
                MyProfileFragment myProfileFragment = new MyProfileFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.flv_main_content, myProfileFragment).commit();
                mDrawerTitle = getString(R.string.navdrawer_item_my_profile);
                break;
            default:
                break;
        }
        drawerLayout.closeDrawers();

    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onBackPressed() {

        if(drawerLayout.isDrawerOpen(Gravity.LEFT)){
            drawerLayout.closeDrawers();
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



}
