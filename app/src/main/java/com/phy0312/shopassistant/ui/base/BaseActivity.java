package com.phy0312.shopassistant.ui.base;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.phy0312.shopassistant.R;
import com.phy0312.shopassistant.config.MainSp;
import com.phy0312.shopassistant.ui.CurrentActivity;
import com.phy0312.shopassistant.ui.activity.HuoDongActivity;
import com.phy0312.shopassistant.ui.coupon.CouponActivity;
import com.phy0312.shopassistant.ui.deal.DealActivity;
import com.phy0312.shopassistant.ui.my.MyProfileActivity;

import java.util.ArrayList;

/**
 * description: <br/>
 * author: dingdongjin_91<br/>
 * date: 2014/12/19<br/>
 */
public class BaseActivity extends ActionBarActivity {
    public static final String TAG = BaseActivity.class.getSimpleName();

    // Navigation drawer:
    protected DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    private ViewGroup mDrawerItemsListContainer;
    private Handler mHandler;

    public static final int NAVDRAWER_ITEM_MAIN = 0;
    public static final int NAVDRAWER_ITEM_HUODONG = 1;
    public static final int NAVDRAWER_ITEM_COUPON = 2;
    public static final int NAVDRAWER_ITEM_TUANGOU = 3;
    public static final int NAVDRAWER_ITEM_MY_PROFILE = 4;
    protected static final int NAVDRAWER_ITEM_INVALID = -1;

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

    // delay to launch nav drawer item, to allow close animation to play
    private static final int NAVDRAWER_LAUNCH_DELAY = 250;

    // fade in and fade out durations for the main content when switching between
    // different Activities of the app through the Nav Drawer
    private static final int MAIN_CONTENT_FADEOUT_DURATION = 150;
    private static final int MAIN_CONTENT_FADEIN_DURATION = 250;

    // list of navdrawer items that were actually added to the navdrawer, in order
    private ArrayList<Integer> mNavDrawerItems = new ArrayList<Integer>();

    // views that correspond to each navdrawer item, null if not yet created
    private View[] mNavDrawerItemViews = null;

    private Runnable mDeferredOnDrawerClosedRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new Handler();

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * Returns the navigation drawer item that corresponds to this Activity. Subclasses
     * of BaseActivity override this to indicate what nav drawer item corresponds to them
     * Return NAVDRAWER_ITEM_INVALID to mean that this Activity should not have a Nav Drawer.
     */
    protected int getSelfNavDrawerItem() {
        return NAVDRAWER_ITEM_INVALID;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setupNavDrawer();
    }

    private void setupNavDrawer() {
        int selfItem = getSelfNavDrawerItem();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (mDrawerLayout == null) {
            return;
        }
        if (selfItem == NAVDRAWER_ITEM_INVALID) {//子类未复写
            // do not show a nav drawer
            View navDrawer = mDrawerLayout.findViewById(R.id.navdrawer);
            if (navDrawer != null) {
                ((ViewGroup) navDrawer.getParent()).removeView(navDrawer);
            }
            mDrawerLayout = null;
            return;
        }

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close) {

            public void onDrawerClosed(View view) {
                if (mDeferredOnDrawerClosedRunnable != null) {
                    mDeferredOnDrawerClosedRunnable.run();
                    mDeferredOnDrawerClosedRunnable = null;
                }
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
                onNavDrawerStateChanged(false, false);
                getSupportActionBar().setTitle(getDrawTitle());
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
                onNavDrawerStateChanged(true, false);
                getSupportActionBar().setTitle(getString(R.string.app_name));
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, Gravity.START);

        // populate the nav drawer with the correct items
        populateNavDrawer();
        mDrawerToggle.syncState();
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
        boolean selected = itemId == getSelfNavDrawerItem();
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
        titleView.setTypeface(selected? Typeface.DEFAULT_BOLD :Typeface.SANS_SERIF);

        iconView.setColorFilter(selected ?
                getResources().getColor(R.color.navdrawer_icon_tint_selected) :
                getResources().getColor(R.color.navdrawer_icon_tint));
    }

    public void onNavDrawerItemClicked(final int itemId) {
        if (itemId == getSelfNavDrawerItem()) {
            mDrawerLayout.closeDrawer(Gravity.START);
            return;
        }

        setSelectedNavDrawerItem(itemId);

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                goToNavDrawerItem(itemId);
            }
        }, NAVDRAWER_LAUNCH_DELAY);


        // fade out the main content
        View mainContent = findViewById(R.id.flv_main_content);
        if (mainContent != null) {
            mainContent.animate().alpha(0).setDuration(MAIN_CONTENT_FADEOUT_DURATION);
        }

        mDrawerLayout.closeDrawer(Gravity.START);
    }

    private void goToNavDrawerItem(int item) {
        if (getSelfNavDrawerItem() == item) {
            mDrawerLayout.closeDrawers();
            return;
        }
        Intent intent;
        switch (item) {
            case NAVDRAWER_ITEM_MAIN:
                intent = new Intent(this, CurrentActivity.class);
                startActivity(intent);
                finish();
                break;
            case NAVDRAWER_ITEM_COUPON:
                intent = new Intent(this, CouponActivity.class);
                startActivity(intent);
                finish();
                break;
            case NAVDRAWER_ITEM_HUODONG:
                intent = new Intent(this, HuoDongActivity.class);
                startActivity(intent);
                finish();
                break;
            case NAVDRAWER_ITEM_TUANGOU:
                intent = new Intent(this, DealActivity.class);
                startActivity(intent);
                finish();
                break;
            case NAVDRAWER_ITEM_MY_PROFILE:
                intent = new Intent(this, MyProfileActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }


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

    protected void onNavDrawerStateChanged(boolean isOpen, boolean isAnimating) {

    }

    protected boolean isNavDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(Gravity.START);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public String getDrawTitle(){
        return this.getString(R.string.app_name);
    }
}
