package com.phy0312.shopassistant.activity.base;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.phy0312.shopassistant.R;
import com.phy0312.shopassistant.tools.ImageLoaderUtil;

import java.util.List;

/**
 * description: <br/>
 * author: dingdj<br/>
 * date: 2014/11/26<br/>
 */
public class UIUtil {

    public static void initAdsBanner(Context context,  final List<ImageView> viewList, ViewPager viewPager) {
        LayoutInflater mInflater = LayoutInflater.from(context);
        ImageView v1 = (ImageView)mInflater.inflate(R.layout.banner_ads, null);
        ImageView v2 = (ImageView)mInflater.inflate(R.layout.banner_ads, null);
        ImageView v3 = (ImageView)mInflater.inflate(R.layout.banner_ads, null);


        viewList.add(v1);
        viewList.add(v2);
        viewList.add(v3);


        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return viewList.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object o) {
                return view == o;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(viewList.get(position));
                ImageLoader.getInstance().displayImage("http://d02.res.meilishuo.net/pic/d/87/8c/9ae13726e933e85711c2b3de7c8a_750_220.gif",
                        viewList.get(position), ImageLoaderUtil.newDisplayImageOptionsInstance());
                return viewList.get(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(viewList.get(position));

            }
        });

        viewPager.setCurrentItem(0);
    }
}
