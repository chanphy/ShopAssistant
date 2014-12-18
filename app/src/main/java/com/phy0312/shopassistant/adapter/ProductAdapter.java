package com.phy0312.shopassistant.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.phy0312.shopassistant.R;
import com.phy0312.shopassistant.model.Product;
import com.phy0312.shopassistant.tools.ImageLoaderUtil;

import java.util.List;

/**
 * description: 商品<br/>
 * author: dingdj<br/>
 * date: 2014/12/4<br/>
 */
public class ProductAdapter extends BaseAdapter {

    private Context context;

    private List<Product> list;

    DisplayImageOptions options;

    public ProductAdapter(Context context, List<Product> list) {
        this.context = context;
        this.list = list;

        options = ImageLoaderUtil.newDisplayImageOptionsInstance();
    }

    @Override
    public int getCount() {
        return list == null ? null : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list == null ? null : list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.listitem_product, null);
            viewHolder = new ViewHolder();
            viewHolder.rl_content = (RelativeLayout) convertView.findViewById(R.id.rl_content);
            viewHolder.iv_product_photo = (ImageView) convertView.findViewById(R.id.iv_product_photo);
            viewHolder.tv_product_name = (TextView) convertView.findViewById(R.id.tv_product_name);
            viewHolder.tv_product_brand = (TextView) convertView.findViewById(R.id.tv_product_brand);
            viewHolder.tv_product_price = (TextView) convertView.findViewById(R.id.tv_product_price);
            viewHolder.tv_product_discount_price = (TextView) convertView.findViewById(R.id.tv_product_discount_price);
            convertView.setTag(viewHolder);
        }
        Product product = list.get(position);
        viewHolder = (ViewHolder) convertView.getTag();
        ImageLoader.getInstance().displayImage(product.getIconUrl(), viewHolder.iv_product_photo, options);
        viewHolder.tv_product_name.setText(product.getProductName());
        viewHolder.tv_product_brand.setText(product.getProductBrand());
        viewHolder.tv_product_price.setText(product.getProductPrice() + "");
        viewHolder.tv_product_discount_price.setText(product.getProductDiscountPrice() + "");

        return convertView;
    }


    static class ViewHolder {
        ImageView iv_product_photo;
        TextView tv_product_name;
        TextView tv_product_brand;
        TextView tv_product_price;
        TextView tv_product_discount_price;
        RelativeLayout rl_content;
    }

    public List<Product> getList() {
        return list;
    }

    public void setList(List<Product> list) {
        this.list = list;
    }
}
