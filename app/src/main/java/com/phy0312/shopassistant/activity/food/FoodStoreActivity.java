package com.phy0312.shopassistant.activity.food;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ListView;

import com.phy0312.shopassistant.R;
import com.phy0312.shopassistant.db.Store;
import com.phy0312.shopassistant.view.HorizontalListView;
import com.phy0312.shopassistant.view.PullToRefreshLayout;

public class FoodStoreActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.common_activity_fragment_container);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        private Store store;
        private PullToRefreshLayout ptl_container;
        private ListView lv_content;
        private HorizontalListView hlv_store_advertise_dishes;


        public PlaceholderFragment() {
        }



        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_food_store, container, false);
            lv_content = (ListView) rootView.findViewById(R.id.lv_content);
            ptl_container = (PullToRefreshLayout) rootView.findViewById(R.id.ptl_container);
            hlv_store_advertise_dishes = (HorizontalListView) rootView.findViewById(R.id.hlv_store_advertise_dishes);

            View headerView = inflater.inflate(R.layout.list_header_food_store, lv_content, false);
            lv_content.addHeaderView(headerView);


            return rootView;
        }


    }
}
