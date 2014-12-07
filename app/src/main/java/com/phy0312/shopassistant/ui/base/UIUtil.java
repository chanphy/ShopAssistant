package com.phy0312.shopassistant.ui.base;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.phy0312.shopassistant.R;
import com.phy0312.shopassistant.tools.ImageLoaderUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * description: <br/>
 * author: dingdj<br/>
 * date: 2014/11/26<br/>
 */
public class UIUtil {

    public static void initAdsBanner(Context context,  final List<ImageView> viewList, final ViewPager viewPager) {
        LayoutInflater mInflater = LayoutInflater.from(context);

        final List<String> datas = new ArrayList<String>();
        datas.add("http://jingdongquan.net/sites/default/files/imagecache/512/auto_img/2012/10/31/20121031124351.jpg");
        datas.add("http://www.yihaodianquan.com/sites/yihaodianquan.com/files/imagecache/512/2010/12/jizhongri-youhuiquan.jpg");
        datas.add("http://jingdongquan.net/sites/default/files/imagecache/512/2011/10/360buy-baihuo.jpg");

        viewPager.setTag(datas.size());
        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return Integer.MAX_VALUE;
            }

            @Override
            public boolean isViewFromObject(View view, Object o) {
                return view == o;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {

                int realPostion = getRealPostion(position, datas.size());
                ImageView imageView = (ImageView)LayoutInflater.from(container.getContext()).inflate(R.layout.banner_ads, null);
                container.addView(imageView);
                ImageLoader.getInstance().displayImage(datas.get(realPostion), imageView, ImageLoaderUtil.newDisplayImageOptionsInstance());
                return imageView;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                if(object instanceof View) {
                    container.removeView((View)object);
                }
            }
        });

        viewPager.setCurrentItem(Integer.MAX_VALUE/2);
        final Handler handler = new Handler();
        final Runnable r = new Runnable() {
            @Override
            public void run() {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
                handler.postDelayed(this, 5000);
            }
        };
        handler.postDelayed(r, 5000);
    }

    public static int getRealPostion(int position, int count) {
        int realPostion = position - Integer.MAX_VALUE/2;
        if(realPostion >= 0) {
            realPostion = realPostion%count;
        }else{
            realPostion = realPostion%count + count-1;
        }
        return realPostion;
    }

}
