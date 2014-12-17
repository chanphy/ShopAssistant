package com.phy0312.shopassistant.ui;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.phy0312.shopassistant.MainApplication;
import com.phy0312.shopassistant.R;
import com.phy0312.shopassistant.adapter.PlazaAdapter;
import com.phy0312.shopassistant.config.MainSp;
import com.phy0312.shopassistant.ui.activity.ActivityFragment;
import com.phy0312.shopassistant.ui.base.BaseFragment;
import com.phy0312.shopassistant.ui.coupon.CouponFragment;
import com.phy0312.shopassistant.ui.deal.DealFragment;
import com.phy0312.shopassistant.ui.my.MyProfileFragment;

import java.util.ArrayList;

/**
 * 主activity
 */
public class MainActivity extends ActionBarActivity implements ActionBar.TabListener, MainApplication.LocationChange {

    private static final String TAG = "MainActivity";
    private final int waitTime = 2000;
    private long touchTime = 0;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private int itemId = 0;
    private Toolbar toolbar;
    private Spinner sp_plazas;
    private ViewGroup mDrawerItemsListContainer;


    private ArrayList<Integer> mNavDrawerItems = new ArrayList<Integer>();

    private View[] mNavDrawerItemViews = null;

    public static final int NAVDRAWER_ITEM_MAIN = 0;
    public static final int NAVDRAWER_ITEM_HUODONG = 1;
    public static final int NAVDRAWER_ITEM_COUPON = 2;
    public static final int NAVDRAWER_ITEM_TUANGOU = 3;
    public static final int NAVDRAWER_ITEM_MY_PROFILE = 4;


    private static final int[] NAVDRAWER_TITLE_RES_ID = new int[]{
            R.string.navdrawer_item_main,
            R.string.navdrawer_item_huodong,
            R.string.navdrawer_item_coupon,
            R.string.navdrawer_item_tuangou,
            R.string.navdrawer_item_my_profile
    };

    private static final int[] NAVDRAWER_ICON_RES_ID = new int[]{
            R.drawable.ic_drawer_main,
            R.drawable.ic_drawer_huodong,
            R.drawable.ic_drawer_coupon,
            R.drawable.ic_drawer_food,
            R.drawable.ic_drawer_my_profile
    };
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler();
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        sp_plazas = (Spinner) findViewById(R.id.sp_plazas);

        MainApplication.appContext.registerLocationChanger(this);
        MainApplication.appContext.mLocationClient.start();


        ArrayList<String> list = new ArrayList<String>();
        list.add("东方百货");
        list.add("万象城");
        list.add("宝龙城市广场");
        sp_plazas.setAdapter(new PlazaAdapter(list, LayoutInflater.from(this)));

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        initDrawerMenu();
        MainFragment mainFragment = new MainFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.flv_main_content, mainFragment).commit();

        if (MainSp.getInstance(this).isFirstUse()) {
            drawerLayout.openDrawer(Gravity.LEFT);
            MainSp.getInstance(this).setFirstUse(false);
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

        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                if (mDrawerTitle.equals("")) {
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
        populateNavDrawer();
    }

    public void modifyTitle() {
        getSupportActionBar().setTitle(mDrawerTitle);
        invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
        sp_plazas.setVisibility(View.GONE);
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

    public void onNavDrawerItemClicked(int position) {
        if (position == itemId) {
            drawerLayout.closeDrawers();
            return;
        }
        itemId = position;
        // launch the target Activity after a short delay, to allow the close animation to play
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                goToNavDrawerItem();
            }
        }, 250);

        // change the active item on the list so the user can see the item changed
        setSelectedNavDrawerItem(itemId);

        goToNavDrawerItem();
        drawerLayout.closeDrawers();
    }

    public void goToNavDrawerItem() {
        switch (itemId) {
            case NAVDRAWER_ITEM_MAIN:
                MainFragment mainFragment = new MainFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.flv_main_content, mainFragment).commit();
                mDrawerTitle = "";
                break;
            case NAVDRAWER_ITEM_HUODONG:
                gotoActivity();
                break;
            case NAVDRAWER_ITEM_COUPON:
                gotoCoupon();
                break;
            case NAVDRAWER_ITEM_TUANGOU:
                DealFragment dealFragment = new DealFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.flv_main_content, dealFragment).commit();
                mDrawerTitle = getString(R.string.navdrawer_item_tuangou);
                break;
            case NAVDRAWER_ITEM_MY_PROFILE:
                MyProfileFragment myProfileFragment = new MyProfileFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.flv_main_content, myProfileFragment).commit();
                mDrawerTitle = getString(R.string.navdrawer_item_my_profile);
                break;
            default:
                break;
        }
    }

    public void gotoActivity() {
        ActivityFragment activityFragment = new ActivityFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.flv_main_content, activityFragment).commit();
        mDrawerTitle = getString(R.string.navdrawer_item_huodong);
        itemId = NAVDRAWER_ITEM_HUODONG;
    }

    public void gotoCoupon() {
        CouponFragment couponFragment = new CouponFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.flv_main_content, couponFragment).commit();
        mDrawerTitle = getString(R.string.navdrawer_item_coupon);
        itemId = NAVDRAWER_ITEM_COUPON;
    }

    public void gotoMain() {
        MainFragment mainFragment = new MainFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.flv_main_content, mainFragment).commit();
        mDrawerTitle = "";
        itemId = NAVDRAWER_ITEM_MAIN;
        modifyTitle();
        sp_plazas.setVisibility(View.VISIBLE);
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
        if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            drawerLayout.closeDrawers();
            return;
        }

        BaseFragment fragment = (BaseFragment) getSupportFragmentManager().findFragmentById(R.id.flv_main_content);
        if (fragment != null && fragment.onBackPressed()) {
            return;
        }

        if (!(fragment instanceof MainFragment)) {
            gotoMain();
            setSelectedNavDrawerItem(NAVDRAWER_ITEM_MAIN);
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
    public void locationChange() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MainApplication.appContext.unRegisterLocationChanger();
    }


    private void populateNavDrawer() {
        mNavDrawerItems.clear();
        mNavDrawerItems.add(NAVDRAWER_ITEM_MAIN);
        mNavDrawerItems.add(NAVDRAWER_ITEM_HUODONG);
        mNavDrawerItems.add(NAVDRAWER_ITEM_COUPON);
        mNavDrawerItems.add(NAVDRAWER_ITEM_TUANGOU);
        mNavDrawerItems.add(NAVDRAWER_ITEM_MY_PROFILE);

        createNavDrawerItems();
    }

    private void createNavDrawerItems() {
        mDrawerItemsListContainer = (ViewGroup) findViewById(R.id.navdrawer_items_list);
        if (mDrawerItemsListContainer == null) {
            return;
        }

        mNavDrawerItemViews = new View[mNavDrawerItems.size()];
        mDrawerItemsListContainer.removeAllViews();
        int i = 0;
        for (int itemId : mNavDrawerItems) {
            mNavDrawerItemViews[i] = makeNavDrawerItem(itemId, mDrawerItemsListContainer);
            mDrawerItemsListContainer.addView(mNavDrawerItemViews[i]);
            ++i;
        }
    }

    private View makeNavDrawerItem(final int itemId, ViewGroup container) {
        boolean selected = itemId == NAVDRAWER_ITEM_MAIN;
        int layoutToInflate = 0;

        layoutToInflate = R.layout.navdrawer_item;
        View view = getLayoutInflater().inflate(layoutToInflate, container, false);


        ImageView iconView = (ImageView) view.findViewById(R.id.icon);
        TextView titleView = (TextView) view.findViewById(R.id.title);
        int iconId = itemId >= 0 && itemId < NAVDRAWER_ICON_RES_ID.length ?
                NAVDRAWER_ICON_RES_ID[itemId] : 0;
        int titleId = itemId >= 0 && itemId < NAVDRAWER_TITLE_RES_ID.length ?
                NAVDRAWER_TITLE_RES_ID[itemId] : 0;

        // set icon and text
        iconView.setVisibility(iconId > 0 ? View.VISIBLE : View.GONE);
        if (iconId > 0) {
            iconView.setImageResource(iconId);
        }
        titleView.setText(getString(titleId));

        formatNavDrawerItem(view, itemId, selected);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNavDrawerItemClicked(itemId);
            }
        });

        return view;
    }

    private void formatNavDrawerItem(View view, int itemId, boolean selected) {

        ImageView iconView = (ImageView) view.findViewById(R.id.icon);
        TextView titleView = (TextView) view.findViewById(R.id.title);

        // configure its appearance according to whether or not it's selected
        titleView.setTextColor(selected ?
                getResources().getColor(R.color.navdrawer_text_color_selected) :
                getResources().getColor(R.color.navdrawer_text_color));
        titleView.setTypeface(selected?Typeface.DEFAULT_BOLD :Typeface.SANS_SERIF);

        iconView.setColorFilter(selected ?
                getResources().getColor(R.color.navdrawer_icon_tint_selected) :
                getResources().getColor(R.color.navdrawer_icon_tint));
    }

    /**
     * Sets up the given navdrawer item's appearance to the selected state. Note: this could
     * also be accomplished (perhaps more cleanly) with state-based layouts.
     */
    public void setSelectedNavDrawerItem(int itemId) {
        if (mNavDrawerItemViews != null) {
            for (int i = 0; i < mNavDrawerItemViews.length; i++) {
                if (i < mNavDrawerItems.size()) {
                    int thisItemId = mNavDrawerItems.get(i);
                    formatNavDrawerItem(mNavDrawerItemViews[i], thisItemId, itemId == thisItemId);
                }
            }
        }
    }


}
