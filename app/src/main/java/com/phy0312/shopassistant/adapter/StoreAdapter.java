package com.phy0312.shopassistant.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.phy0312.shopassistant.R;
import com.phy0312.shopassistant.tools.ImageLoaderUtil;

import java.util.List;

/**
 * description: <br/>
 * author: dingdongjin_91<br/>
 * date: 2014/12/12<br/>
 */
public class StoreAdapter extends BaseAdapter {

    DisplayImageOptions options;

    public static abstract class Row {
    }

    public static final class Section extends Row {
        public final String text;

        public Section(String text) {
            this.text = text;
        }
    }

    public static final class Item extends Row {
        public final String text;

        public Item(String text) {
            this.text = text;
        }
    }

    public StoreAdapter() {
        options = ImageLoaderUtil.newDisplayImageOptionsInstance();
    }

    private List<Row> rows;

    public void setRows(List<Row> rows) {
        this.rows = rows;
    }

    @Override
    public int getCount() {
        return rows.size();
    }

    @Override
    public Object getItem(int position) {
        return rows.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        if (getItem(position) instanceof Section) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (getItemViewType(position) == 0) { // Item
            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = (RelativeLayout) inflater.inflate(R.layout.listitem_store, parent, false);
            }

            Item item = (Item) getItem(position);
            TextView textView = (TextView) view.findViewById(R.id.tv_store_name);
            textView.setText(item.text);

            ImageLoader.getInstance().displayImage("http://pic25.nipic.com/20121119/11328459_121121530346_2.jpg",
                    (ImageView)view.findViewById(R.id.iv_store_photo), options);
        } else { // Section
            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = (LinearLayout) inflater.inflate(R.layout.listitem_store_section, parent, false);
            }

            Section section = (Section) getItem(position);
            TextView textView = (TextView) view.findViewById(R.id.textView1);
            textView.setText(section.text);
        }

        return view;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    static class ViewHolder {
        public ImageView iv_store_photo;
        public TextView tv_store_name;
        public ImageView iv_point_mark;
        public ImageView iv_deal;
        public ImageView iv_activity;
        public ImageView iv_coupon;
        public ImageView iv_product;
    }

}
