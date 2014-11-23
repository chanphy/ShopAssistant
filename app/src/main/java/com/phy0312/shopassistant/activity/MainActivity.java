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
        sp_plazas.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, new String[]{"东方百货", "宝龙", "万象城"}));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayUseLogoEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        lv_menu = (ListView) findViewById(R.id.lv_menu);
        lv_menu.setOnItemClickListener(this);
        initDrawerMenu();
        MainFragment mainFragment = new MainFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.flv_main_content, mainFragment).commit();
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
        mTitle = mDrawerTitle = getTitle();

        lv_menu.setAdapter(new DrawerMenuAdapter(this.getLayoutInflater()));

        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(mDrawerTitle);
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
                getSupportActionBar().setDisplayShowTitleEnabled(false);
                sp_plazas.setVisibility(View.VISIBLE);
                MainFragment mainFragment = new MainFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.flv_main_content, mainFragment).commit();
                mDrawerTitle = "";
                break;
            case DrawerMenuAdapter.NAVDRAWER_ITEM_HUODONG:
                getSupportActionBar().setDisplayShowTitleEnabled(true);
                sp_plazas.setVisibility(View.GONE);
                HuoDongFragment huoDongFragment = new HuoDongFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.flv_main_content, huoDongFragment).commit();
                mDrawerTitle = getString(R.string.navdrawer_item_huodong);
                break;
            case DrawerMenuAdapter.NAVDRAWER_ITEM_COUPON:
                getSupportActionBar().setDisplayShowTitleEnabled(true);
                sp_plazas.setVisibility(View.GONE);
                CouponFragment couponFragment = new CouponFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.flv_main_content, couponFragment).commit();
                mDrawerTitle = getString(R.string.navdrawer_item_coupon);
                break;
            case DrawerMenuAdapter.NAVDRAWER_ITEM_TUANGOU:
                getSupportActionBar().setDisplayShowTitleEnabled(true);
                sp_plazas.setVisibility(View.GONE);
                FoodFragment foodFragment = new FoodFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.flv_main_content, foodFragment).commit();
                mDrawerTitle = getString(R.string.navdrawer_item_tuangou);
                break;
            case DrawerMenuAdapter.NAVDRAWER_ITEM_MY_PROFILE:
                getSupportActionBar().setDisplayShowTitleEnabled(true);
                sp_plazas.setVisibility(View.GONE);
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
        long currentTime = System.currentTimeMillis();
        if ((currentTime - touchTime) >= waitTime) {
            Toast.makeText(this, getString(R.string.exit_confirm), Toast.LENGTH_SHORT).show();
            touchTime = currentTime;
        } else {
            finish();
        }
    }
}
