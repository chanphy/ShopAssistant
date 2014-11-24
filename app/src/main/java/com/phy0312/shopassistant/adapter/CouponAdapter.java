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
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.phy0312.shopassistant.R;
import com.phy0312.shopassistant.db.Coupon;

import java.util.List;

/**
 * description: 优惠券列表适配器<br/>
 * author: dingdj<br/>
 * date: 2014/11/19<br/>
 */
public class CouponAdapter extends BaseAdapter {

    private Context context;

    private List<Coupon> list;

    DisplayImageOptions options;

    public CouponAdapter(Context context, List<Coupon> list) {
        this.context = context;
        this.list = list;

        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_stub)
                .showImageForEmptyUri(R.drawable.ic_empty)
                .showImageOnFail(R.drawable.ic_error)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .displayer(new RoundedBitmapDisplayer(20))
                .build();
    }

    @Override
    public int getCount() {
        return list==null?null:list.size();
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
            convertView = LayoutInflater.from(context).inflate(R.layout.listitem_coupon, null);
            viewHolder = new ViewHolder();
            viewHolder.iv_coupon_photo = (ImageView)convertView.findViewById(R.id.iv_coupon_photo);
            viewHolder.tv_coupon_name = (TextView)convertView.findViewById(R.id.tv_coupon_name);
            viewHolder.tv_coupon_summary = (TextView)convertView.findViewById(R.id.tv_coupon_summary);
            viewHolder.tv_coupon_price = (TextView)convertView.findViewById(R.id.tv_coupon_price);
            viewHolder.tv_coupon_type = (TextView)convertView.findViewById(R.id.tv_coupon_type);
            viewHolder.tv_coupon_count = (TextView)convertView.findViewById(R.id.tv_coupon_count);
            convertView.setTag(viewHolder);
        }
        Coupon coupon = list.get(position);
        viewHolder =  (ViewHolder)convertView.getTag();
        ImageLoader.getInstance().displayImage(coupon.getIconPath(), viewHolder.iv_coupon_photo, options);
        viewHolder.tv_coupon_name.setText(coupon.getName());
        viewHolder.tv_coupon_summary.setText(coupon.getDescription());
        viewHolder.tv_coupon_type.setText(coupon.getCategory()+"");
        viewHolder.tv_coupon_count.setText(
                String.format(context.getString(R.string.coupon_count), coupon.getCount()+"")
        );
        return convertView;
    }

    public List<Coupon> getList() {
        return list;
    }

    public void setList(List<Coupon> list) {
        this.list = list;
    }


    static class ViewHolder {
        ImageView iv_coupon_photo;
        TextView tv_coupon_name;
        TextView tv_coupon_summary;
        TextView tv_coupon_price;
        TextView tv_coupon_type;
        TextView tv_coupon_count;
    }


}
