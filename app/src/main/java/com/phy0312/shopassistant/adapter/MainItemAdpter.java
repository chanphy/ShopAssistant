package com.phy0312.shopassistant.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.phy0312.shopassistant.R;
import com.phy0312.shopassistant.model.MainColumnGroup;
import com.phy0312.shopassistant.model.MainColumnInfo;
import com.phy0312.shopassistant.tools.AndroidUtil;
import com.phy0312.shopassistant.tools.ImageLoaderUtil;
import com.phy0312.shopassistant.ui.MainActivity;
import com.phy0312.shopassistant.ui.MainFragment;
import com.phy0312.shopassistant.ui.activity.ActivityDetailActivty;
import com.phy0312.shopassistant.ui.coupon.CouponDetailActivity;
import com.phy0312.shopassistant.ui.product.ProductDetailActivity;

import java.util.List;

import static com.phy0312.shopassistant.tools.Constants.CATEGORY_COUPON;
import static com.phy0312.shopassistant.tools.Constants.CATEGORY_WEEKRECOMMEND;
import static com.phy0312.shopassistant.tools.Constants.CATEGOTY_DEAL;
import static com.phy0312.shopassistant.tools.Constants.CATEGOTY_ACTIVITY;

/**
 * description: 主界面Adapter<br/>
 * author: dingdj<br/>
 * date: 2014/11/7<br/>
 */
public class MainItemAdpter extends BaseAdapter {

    List<MainColumnGroup> list;
    Context context;
    DisplayImageOptions options;
    private MainFragment mainFragment;

    public MainItemAdpter(List<MainColumnGroup> list, Context context, MainFragment mainFragment) {
        this.list = list;
        this.context = context;
        this.mainFragment = mainFragment;
        options = ImageLoaderUtil.newDisplayImageOptionsInstance();
    }

    public List<MainColumnGroup> getList() {
        return list;
    }

    public void setList(List<MainColumnGroup> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return list == null ? null : list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressWarnings("all")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.listitem_main_common, null);
            holder = new ViewHolder();
            holder.rl_home_left = (RelativeLayout)convertView.findViewById(R.id.rl_home_left);
            holder.ll_home_right_top = (RelativeLayout)convertView.findViewById(R.id.ll_home_right_top);
            holder.ll_home_right_bottom = (RelativeLayout)convertView.findViewById(R.id.ll_home_right_bottom);

            holder.rv_header_container = convertView.findViewById(R.id.rv_header_container);
            holder.tv_header_title = (TextView) convertView.findViewById(R.id.tv_header_title);
            holder.tv_header_more = (TextView) convertView.findViewById(R.id.tv_header_more);

            holder.tv_home_left_summary = (TextView) convertView.findViewById(R.id.tv_home_left_summary);
            holder.tv_home_left_price = (TextView) convertView.findViewById(R.id.tv_home_left_price);
            holder.iv_home_left_photo_tag = (ImageView) convertView.findViewById(R.id.iv_home_left_photo);
            holder.iv_home_left_photo = (ImageView) convertView.findViewById(R.id.iv_home_left_photo);

            holder.iv_home_right_top_photo = (ImageView) convertView.findViewById(R.id.iv_home_right_top_photo);
            holder.iv_home_right_top_photo_tag = (ImageView) convertView.findViewById(R.id.iv_home_right_top_photo_tag);
            holder.tv_home_right_top_summary = (TextView) convertView.findViewById(R.id.tv_home_right_top_summary);
            holder.tv_home_right_top_price = (TextView) convertView.findViewById(R.id.tv_home_right_top_price);

            holder.iv_home_right_bottom_photo = (ImageView) convertView.findViewById(R.id.iv_home_right_bottom_photo);
            holder.iv_home_right_buttom_photo_tag = (ImageView) convertView.findViewById(R.id.iv_home_right_buttom_photo_tag);
            holder.tv_home_right_bottom_summary = (TextView) convertView.findViewById(R.id.tv_home_right_bottom_summary);
            holder.tv_home_right_bottom_price = (TextView) convertView.findViewById(R.id.tv_home_right_bottom_price);

            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        MainColumnGroup group = list.get(position);


        switch (group.getCategory()) {
            case CATEGORY_WEEKRECOMMEND:
                holder.rv_header_container.setBackground(context.getResources().getDrawable(R.drawable.main_listitem_header_bg_re));
                holder.tv_header_more.setVisibility(View.INVISIBLE);
                holder.tv_header_title.setText(R.string.main_item_week_recommend);
                break;
            case CATEGORY_COUPON:
                holder.rv_header_container.setBackground(context.getResources().getDrawable(R.drawable.main_listitem_header_co));
                holder.tv_header_more.setVisibility(View.VISIBLE);
                holder.tv_header_title.setText(R.string.main_item_coupon);
                holder.tv_header_more.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        ((MainActivity)mainFragment.getActivity()).gotoCoupon();
                        ((MainActivity)mainFragment.getActivity()).modifyTitle();
                        ((MainActivity)mainFragment.getActivity()).setSelectedNavDrawerItem(MainActivity.NAVDRAWER_ITEM_COUPON);
                    }
                });
                break;
            case CATEGOTY_ACTIVITY:
                holder.rv_header_container.setBackground(context.getResources().getDrawable(R.drawable.main_listitem_header_bg_hd));
                holder.tv_header_more.setVisibility(View.VISIBLE);
                holder.tv_header_title.setText(R.string.main_item_huodong);
                holder.tv_header_more.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        ((MainActivity)mainFragment.getActivity()).gotoActivity();
                        ((MainActivity)mainFragment.getActivity()).modifyTitle();
                        ((MainActivity)mainFragment.getActivity()).setSelectedNavDrawerItem(MainActivity.NAVDRAWER_ITEM_HUODONG);
                    }
                });
                break;
            case CATEGOTY_DEAL:
                holder.rv_header_container.setBackground(context.getResources().getDrawable(R.drawable.main_listitem_header_bg_re));
                holder.tv_header_more.setVisibility(View.VISIBLE);
                holder.tv_header_title.setText(R.string.main_item_flim);
                break;
            default:
                break;
        }
        MainColumnInfo[] infos = group.getMainColumnInfos();
        for(int i=0; i<infos.length; i++) {
            MainColumnInfo info = infos[i];
            if (i == 0) {
                holder.tv_home_left_summary.setText(info.getTitle());
                holder.tv_home_left_price.setText(info.getPrice());
                ImageLoader.getInstance().displayImage(info.getIcon(), holder.iv_home_left_photo, options);
                setOnClickListener(holder, info, holder.rl_home_left);
                continue;
            } else if (i == 1) {
                holder.tv_home_right_top_summary.setText(info.getTitle());
                holder.tv_home_right_top_price.setText(info.getPrice());
                ImageLoader.getInstance().displayImage(info.getIcon(), holder.iv_home_right_top_photo, options);
                setOnClickListener(holder, info, holder.ll_home_right_top);
                continue;
            } else if (i == 2) {
                holder.tv_home_right_bottom_summary.setText(info.getTitle());
                holder.tv_home_right_bottom_price.setText(info.getPrice());
                ImageLoader.getInstance().displayImage(info.getIcon(), holder.iv_home_right_bottom_photo, options);
                setOnClickListener(holder, info, holder.ll_home_right_bottom);
                continue;
            }
        }
        return convertView;
    }

    public void setOnClickListener(ViewHolder holder, MainColumnInfo info, RelativeLayout rl) {
        switch(info.getType()) {
            case MainColumnInfo.TYPE_COUPON:
                rl.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClassName(context, CouponDetailActivity.class.getName());
                        AndroidUtil.startActivity(context, intent);
                    }
                });
                break;
            case MainColumnInfo.TYPE_HUODONG:
                rl.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClassName(context, ActivityDetailActivty.class.getName());
                        AndroidUtil.startActivity(context, intent);
                    }
                });
                break;
            case MainColumnInfo.TYPE_PRODUCT:
                rl.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClassName(context, ProductDetailActivity.class.getName());
                        AndroidUtil.startActivity(context, intent);
                    }
                });
                break;
        }
    }

    static class ViewHolder {

        View rv_header_container;
        RelativeLayout rl_home_left;
        RelativeLayout ll_home_right_top;
        RelativeLayout ll_home_right_bottom;

        TextView tv_header_title;
        TextView tv_header_more;

        TextView tv_home_left_summary;
        TextView tv_home_left_price;
        ImageView iv_home_left_photo_tag;
        ImageView iv_home_left_photo;

        ImageView iv_home_right_top_photo;
        ImageView iv_home_right_top_photo_tag;
        TextView tv_home_right_top_summary;
        TextView tv_home_right_top_price;

        ImageView iv_home_right_bottom_photo;
        ImageView iv_home_right_buttom_photo_tag;
        TextView tv_home_right_bottom_summary;
        TextView tv_home_right_bottom_price;

    }

}
