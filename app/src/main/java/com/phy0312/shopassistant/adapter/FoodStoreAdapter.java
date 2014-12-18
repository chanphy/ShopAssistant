package com.phy0312.shopassistant.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.phy0312.shopassistant.R;
import com.phy0312.shopassistant.model.Store;
import com.phy0312.shopassistant.tools.ImageLoaderUtil;

import java.util.List;

/**
 * description: 美食商家列表适配器<br/>
 * author: dingdj<br/>
 * date: 2014/11/26<br/>
 */
public class FoodStoreAdapter extends BaseAdapter {

    private Context context;

    private List<Store> list;

    DisplayImageOptions options;

    public FoodStoreAdapter(Context context, List<Store> list) {
        this.context = context;
        this.list = list;

        options = ImageLoaderUtil.newDisplayImageOptionsInstance();
    }

    @Override
    public int getCount() {
        return list == null?null:list.size();
    }

    @Override
    public Object getItem(int position) {
        return list==null?null:list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.listitem_food_store, null);
            viewHolder = new ViewHolder();
            viewHolder.iv_store_photo = (ImageView)convertView.findViewById(R.id.iv_store_photo);
            viewHolder.iv_point_mark = (ImageView)convertView.findViewById(R.id.iv_point_mark);
            viewHolder.iv_deal = (ImageView)convertView.findViewById(R.id.iv_deal);
            viewHolder.iv_activity = (ImageView)convertView.findViewById(R.id.iv_activity);
            viewHolder.iv_coupon = (ImageView)convertView.findViewById(R.id.iv_coupon);


            viewHolder.tv_store_name = (TextView)convertView.findViewById(R.id.tv_store_name);
            viewHolder.tv_store_category = (TextView)convertView.findViewById(R.id.tv_store_category);
            viewHolder.tv_store_number = (TextView)convertView.findViewById(R.id.tv_store_number);
            viewHolder.iv_reservation = (TextView)convertView.findViewById(R.id.iv_reservation);
            viewHolder.iv_takeout = (TextView)convertView.findViewById(R.id.iv_takeout);
            viewHolder.tv_average_cost = (TextView)convertView.findViewById(R.id.tv_average_cost);
            convertView.setTag(viewHolder);
        }
        Store store = list.get(position);
        viewHolder =  (ViewHolder)convertView.getTag();
        ImageLoader.getInstance().displayImage(store.getIcon(), viewHolder.iv_store_photo, options);
        viewHolder.tv_store_name.setText(store.getName());
        viewHolder.tv_average_cost.setText("¥"+store.getAverageCost());
        viewHolder.tv_store_category.setText("火锅");
        viewHolder.tv_store_number.setText("3F-37");
        return convertView;
    }

    public List<Store> getList() {
        return list;
    }

    public void setList(List<Store> list) {
        this.list = list;
    }

    static class ViewHolder {
        ImageView iv_store_photo;
        ImageView iv_point_mark;
        ImageView iv_deal;
        ImageView iv_activity;
        ImageView iv_coupon;

        TextView tv_store_name;
        TextView tv_store_category;
        TextView tv_store_number;
        TextView iv_reservation;
        TextView iv_takeout;
        TextView tv_average_cost;
    }
}
