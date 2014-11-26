package com.phy0312.shopassistant.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.phy0312.shopassistant.R;


/**
 * description: 匣子菜单<br/>
 * author: dingdj<br/>
 * date: 2014/11/3<br/>
 */
public class DrawerMenuAdapter extends BaseAdapter {

    private LayoutInflater inflater;

    public DrawerMenuAdapter(LayoutInflater inflater) {
        this.inflater = inflater;
    }

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


    @Override
    public int getCount() {
        return NAVDRAWER_TITLE_RES_ID.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view  = inflater.inflate(R.layout.navdrawer_item, null);
        ImageView iv_icon = (ImageView)view.findViewById(R.id.icon);
        TextView tv_title = (TextView)view.findViewById(R.id.title);
        iv_icon.setImageResource(NAVDRAWER_ICON_RES_ID[position]);
        tv_title.setText(NAVDRAWER_TITLE_RES_ID[position]);
        view.setTag(position);
        return view;
    }



}
